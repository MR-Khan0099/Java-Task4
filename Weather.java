package Task4;


//Weather class in Weather.java

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Weather {
 private final AtomicBoolean isSunny = new AtomicBoolean(true);
 private ChargingStationGUI gui;
 private static final Logger logger = Logger.getLogger(Weather.class.getName());

 // Rest of the code...
 public Weather(ChargingStationGUI gui) {
     this.gui = gui;
     new Thread(() -> {
         while (true) {
             boolean currentWeather = isSunny.get();
             gui.updateWeather(currentWeather ? "Rainy" : "Sunny");
             isSunny.set(!currentWeather);
             try {
                 Thread.sleep(currentWeather ? 10000 : 5000); // 10 seconds sunny, 5 seconds rainy
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }).start();

     // Set up logging for Weather
     try {
         FileHandler fileHandler = new FileHandler("Weather.log", true);
         fileHandler.setFormatter(new SimpleFormatter());
         logger.addHandler(fileHandler);
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

 public boolean isSunny() {
     return isSunny.get();
 }

}
