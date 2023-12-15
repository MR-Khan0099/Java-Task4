package Task4;

//ChargingStation class in ChargingStation.java


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ChargingStation {
 private final int slots = 5;
 private final EnergyManagement energyManagement;
 private final CarQueue carQueue;
 private final ExecutorService chargingSlots;
 private final ChargingStationGUI gui;
 private static final Logger logger = Logger.getLogger(ChargingStation.class.getName());

 // Rest of the code...
 public ChargingStation(EnergyManagement energyManagement, CarQueue carQueue, ChargingStationGUI gui) {
     this.energyManagement = energyManagement;
     this.carQueue = carQueue;
     this.gui = gui;
     this.chargingSlots = Executors.newFixedThreadPool(slots);

     // Set up logging for ChargingStation
     try {
         FileHandler fileHandler = new FileHandler("ChargingStation.log", true);
         fileHandler.setFormatter(new SimpleFormatter());
         logger.addHandler(fileHandler);
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

 public void startChargingProcess() {
     for (int i = 0; i < slots; i++) {
         final int slot = i;
         chargingSlots.submit(() -> chargeCar(slot));
     }
 }

 private void chargeCar(int slot) {
     while (true) {
         Car car = carQueue.getNextCar();
         if (car != null) {
             String energySource = energyManagement.getCurrentEnergySource();
             gui.updateChargingSlot(slot, "Occupied by " + car.getLicensePlate() + " using " + energySource);
             try {
                 Thread.sleep(5000); // Charging time
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             gui.updateChargingSlot(slot, "Empty");
             carQueue.returnCar(car);
             logger.info("Slot " + (slot + 1) + ": Charging complete for " + car.getLicensePlate());
         }
     }
 }
}
