package example;

import javax.swing.*;


public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
        CarQueue carQueue = new CarQueue(null);
        ChargingStationGUI gui = new ChargingStationGUI(carQueue);
        carQueue.setGUI(gui); // Set GUI reference in CarQueue

        Weather weather = new Weather(gui);
        EnergyManagement energyManagement = new EnergyManagement(weather, gui);
        ChargingStation station = new ChargingStation(energyManagement, carQueue, gui);

        station.startChargingProcess();
    });
}
}