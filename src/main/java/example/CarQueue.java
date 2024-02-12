package example;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CarQueue {
    private final Queue<Car> cars = new ConcurrentLinkedQueue<>();
    private ChargingStationGUI gui;
    private static final Logger logger = Logger.getLogger(CarQueue.class.getName());

    
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //updated
    public Car getNextCar() {
        if(cars.isEmpty()){
            return null;
        }else {
            Car car = cars.poll();
            gui.updateQueue(cars.size());
            logger.info("Car retrieved: " + car.getLicensePlate());
            return car;
        }


//        Car car = cars.poll();
//        gui.updateQueue(cars.size());
//        if (car != null) {
//            logger.info("Car retrieved: " + car.getLicensePlate());
//        }
//        return car;
    }

    public void addCar(Car car) {
        cars.offer(car);
        gui.updateQueue(cars.size());
        logger.info("Car added to the queue: " + car.getLicensePlate());
        //New
        notifyAvailableCar();
    }

    public int getQueueSize() {
        return cars.size();
    }

   // This function is used to remove the car from the queue
    public void removeCar(Car car) {
    	cars.remove(car);
        gui.updateQueue(cars.size());
        logger.info("Car removed: "+ car.getLicensePlate());
       
    }


    //New
    public synchronized void notifyAvailableCar() {
        notifyAll(); // Notify all waiting threads (slots) that a new car is available
    }

}
