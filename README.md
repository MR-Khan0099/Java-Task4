# Task 4 Charging Station System
## Overview
This project implements a simple charging station system in Java, with components such as a Car class, CarQueue, ChargingStation, EnergyManagement, Weather, and a graphical user interface (GUI) to interact with the system. The system simulates the charging process for electric cars based on weather conditions, with the ability to log events and errors.

## Project Structure
1. Car Class (Car.java)
The Car class represents an electric car and is used in the system. It has a license plate, and instances of this class are managed within the CarQueue.

2. CarQueue Class (CarQueue.java)
The CarQueue class maintains a queue of electric cars waiting for their turn to charge. It has methods to add, retrieve, and return cars, and it utilizes a concurrent linked queue for thread safety. The class also handles logging and updates the GUI.

3. ChargingStation Class (ChargingStation.java)
The ChargingStation class represents the charging station itself. It uses a fixed thread pool to manage charging slots and continuously charges cars based on availability. The charging process is simulated with a delay. The class is responsible for logging charging events and updating the GUI.

4. EnergyManagement Class (EnergyManagement.java)
The EnergyManagement class is responsible for determining the current energy source (Solar or Grid) based on weather conditions. It runs in a separate thread, continuously updating the energy source and logging the changes. It communicates with the GUI and Weather class.

5. Weather Class (Weather.java)
The Weather class simulates changing weather conditions, alternating between sunny and rainy periods. It updates the GUI and influences the energy source determined by the EnergyManagement class.

6. ChargingExceptions Class (ChargingExceptions.java)
The ChargingExceptions class extends the standard Exception class, providing custom exceptions for charging-related errors.

7. Main Class (Main.java)
The Main class serves as the entry point to the program. It initializes the necessary components and starts the charging process in a Swing GUI thread.

## How to Run
Ensure you have Java installed on your machine.
Compile all Java files using a Java compiler.
Run the Main class.
Additional Notes
The system utilizes logging to record important events and errors. Log files are created for each major component, such as CarQueue.log, ChargingStation.log, EnergyManagement.log, and Weather.log.
The GUI (ChargingStationGUI) provides a visual representation of the charging station, car queue, weather conditions, and energy source.
The system assumes a simplified charging process and weather simulation for demonstration purposes.
