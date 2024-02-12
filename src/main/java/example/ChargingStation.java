package example;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ChargingStation {
 private final int slots = 3;
 private final EnergyManagement energyManagement;
 private final CarQueue carQueue;
 private final ExecutorService chargingSlots;
 private final ChargingStationGUI gui;
 private static final Logger logger = Logger.getLogger(ChargingStation.class.getName());

//for Unit Testing
public ChargingStation(EnergyManagement energyManagement, CarQueue carQueue, ChargingStationGUI gui, ExecutorService executorService) {
     this.energyManagement = energyManagement;
     this.carQueue = carQueue;
     this.gui = gui;
//     this.chargingSlots = Executors.newFixedThreadPool(slots);
    this.chargingSlots = executorService;

     // logging for ChargingStation
     try {
         FileHandler fileHandler = new FileHandler("ChargingStation.log", true);
         fileHandler.setFormatter(new SimpleFormatter());
         logger.addHandler(fileHandler);
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
    public ChargingStation(EnergyManagement energyManagement, CarQueue carQueue, ChargingStationGUI gui) {
        // Default thread pool initialization
        this.energyManagement = energyManagement;
        this.carQueue = carQueue;
        this.gui = gui;
        this.chargingSlots = Executors.newFixedThreadPool(slots);

        // logging for ChargingStation
        try {
            FileHandler fileHandler = new FileHandler("ChargingStation.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void startChargingProcess() {
     for (int i = 0; i < slots; i++) {
         final int slot = i;
         chargingSlots.submit(() -> chargeCar(slot));
     }
 }

//    private void chargeCar(int slot) {
//	    while (true) {
//	        Car car = carQueue.getNextCar();
//	        if (car != null) {
//	            String energySource = energyManagement.getCurrentEnergySource();
//	            gui.updateChargingSlot(slot, "Occupied by " + car.getLicensePlate() + " using " + energySource);
//	            try {
//	                Thread.sleep(5000); // Simulate charging time
//	            } catch (InterruptedException e) {
//	                Thread.currentThread().interrupt();
//	                return;
//	            }
//	            gui.updateChargingSlot(slot, "Empty");
//	            carQueue.removeCar(car);
//	            logger.info("Slot " + (slot + 1) + ": Charging complete for " + car.getLicensePlate());
//
//	        }
//	    }
//	}

    private void chargeCar(int slot) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Car car;
                synchronized (carQueue) {
                    while (carQueue.getQueueSize()==0) {
                        carQueue.wait(); // Wait until notified about a new car
                    }
                    car = carQueue.getNextCar();
                }
                if (car != null) {
                    // Simulate charging the car
                    String energySource = energyManagement.getCurrentEnergySource();
                    gui.updateChargingSlot(slot, "Occupied by " + car.getLicensePlate() + " using " + energySource);


                    Thread.sleep(5000);

                    // Update GUI and log completion
                    gui.updateChargingSlot(slot, "Empty");
                    carQueue.removeCar(car);
                    logger.info("Slot " + (slot + 1) + ": Charging complete for " + car.getLicensePlate());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
            }
        }
    }

}
