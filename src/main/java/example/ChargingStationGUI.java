package example;

import javax.swing.*;
import java.awt.*;

public class ChargingStationGUI {
 private JFrame frame;
 private JLabel weatherLabel;
 private JLabel energySourceLabel;
 private JLabel queueLabel;
 private JLabel[] chargingSlots;
 private JTextField newCarTextField;
 private JButton addCarButton;
 private CarQueue carQueue; 

 

 public ChargingStationGUI(CarQueue carQueue) {
     this.carQueue = carQueue;
     initialize();
 }

 private void initialize() {
     frame = new JFrame("Electric Car Charging Station Simulation");
     frame.setBounds(100, 100, 500, 300);
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.setLayout(new GridLayout(4, 1));

     weatherLabel = new JLabel("Weather: ");
     frame.add(weatherLabel);

     energySourceLabel = new JLabel("Energy Source: ");
     frame.add(energySourceLabel);

     queueLabel = new JLabel("Queue: 10 Cars");
     frame.add(queueLabel);

     JPanel chargingPanel = new JPanel(new FlowLayout());
     chargingSlots = new JLabel[3];
     for (int i = 0; i < chargingSlots.length; i++) {
         chargingSlots[i] = new JLabel("Slot " + (i + 1) + ": Empty");
         chargingPanel.add(chargingSlots[i]);
     }
     frame.add(chargingPanel);

     frame.setVisible(true);

     // Add car section
     JPanel addCarPanel = new JPanel(new FlowLayout());
     newCarTextField = new JTextField(10);
     addCarButton = new JButton("Add Car");
     addCarPanel.add(newCarTextField);
     addCarPanel.add(addCarButton);

     frame.add(addCarPanel);

     // Action listener for the button
     addCarButton.addActionListener(e -> addCarToQueue());

     frame.setVisible(true);
 }

 private void addCarToQueue() {
     String licensePlate = newCarTextField.getText().trim();
     if (!licensePlate.isEmpty()) {
         carQueue.addCar(new Car(licensePlate));
         updateQueue(carQueue.getQueueSize());
         newCarTextField.setText(""); // Clear the text field
     }
 }
 
 
 public void updateWeather(String weather) {
     SwingUtilities.invokeLater(() -> weatherLabel.setText("Weather: " + weather));
 }

 public void updateEnergySource(String source) {
     SwingUtilities.invokeLater(() -> energySourceLabel.setText("Energy Source: " + source));
 }

 public void updateQueue(int size) {
//     final int x = size++;
     SwingUtilities.invokeLater(() -> queueLabel.setText("Queue: " + size + " Cars"));
	}


 public void updateChargingSlot(int slot, String status) {
     SwingUtilities.invokeLater(() -> chargingSlots[slot].setText("Slot " + (slot + 1) + ": " + status));
 }

}


