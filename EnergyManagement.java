package Task4;


//EnergyManagement class in EnergyManagement.java


import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class EnergyManagement {
 private final Weather weather;
 private ChargingStationGUI gui;
 private static final Logger logger = Logger.getLogger(EnergyManagement.class.getName());

 // Rest of the code...
 public EnergyManagement(Weather weather, ChargingStationGUI gui) {
     this.weather = weather;
     this.gui = gui;

     // Start a thread to continuously update the energy source based on the weather
     new Thread(() -> {
         while (true) {
             try {
                 updateEnergySource();
                 Thread.sleep(5000); // Update every second to stay in sync with weather changes
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }).start();

     // Set up logging for EnergyManagement
     try {
         FileHandler fileHandler = new FileHandler("EnergyManagement.log", true);
         fileHandler.setFormatter(new SimpleFormatter());
         logger.addHandler(fileHandler);
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

 private void updateEnergySource() {
     String source = weather.isSunny() ? "Solar" : "Grid";
     gui.updateEnergySource(source);
     logger.info("Current energy source: " + source);
 }

 public String getCurrentEnergySource() {
     return weather.isSunny() ? "Solar" : "Grid";
 }
}
