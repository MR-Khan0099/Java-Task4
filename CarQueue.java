// CarQueue class in CarQueue.java
package Task4;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class Example {
    public static void main(String[] args) {
        // Creating a logger
        Logger logger = Logger.getLogger(Example.class.getName());

        // Creating a FileHandler
        try {
            FileHandler fileHandler = new FileHandler("log.txt");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error creating FileHandler", e);
        }

        // Creating a concurrent linked queue
        Queue<String> queue = new ConcurrentLinkedQueue<>();

        // Logging a message
        logger.info("Logging an informational message.");

        // Adding an element to the queue
        queue.add("Element 1");
    }
}


public class CarQueue {
    private final Queue<Car> cars = new ConcurrentLinkedQueue<>();
    private ChargingStationGUI gui;
    private static final Logger logger = Logger.getLogger(CarQueue.class.getName());

    // Rest of the code...
    public void setGUI(ChargingStationGUI gui) {
        this.gui = gui;
        gui.updateQueue(getQueueSize()); // Update queue size on GUI
    }

    public CarQueue(ChargingStationGUI gui) {
        this.gui = gui;
        // Initialize with 10 cars
        for (int i = 0; i < 10; i++) {
            cars.offer(new Car("Car " + (i + 1)));
        }

        // Set up logging for CarQueue
        try {
            FileHandler fileHandler = new FileHandler("CarQueue.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Car getNextCar() {
        Car car = cars.poll();
        gui.updateQueue(cars.size());
        if (car != null) {
            logger.info("Car retrieved: " + car.getLicensePlate());
        }
        return car;
    }

    public void addCar(Car car) {
        cars.offer(car);
        gui.updateQueue(cars.size());
        logger.info("Car added to the queue: " + car.getLicensePlate());
    }

    public int getQueueSize() {
        return cars.size();
    }

    public void returnCar(Car car) {
        cars.offer(car);
        gui.updateQueue(cars.size());
        logger.info("Car returned to the queue: " + car.getLicensePlate());
    }
}
