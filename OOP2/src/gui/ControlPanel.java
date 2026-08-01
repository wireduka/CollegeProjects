package gui;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;

import logic.Controller;
import logic.EventType;
import logic.Observer;
// Panel used for simulation handling (buttons)
public class ControlPanel extends Panel implements Observer {
	
	private Label timeLabel = new Label("SIMULATION TIME: 00:00:00");
	
	public ControlPanel() {
		Controller controller = Controller.getInstance();
		controller.getSimulationClock().addObserver(this);
		
		this.setLayout(new FlowLayout());
		
		Button startButton = new Button("START");
		Button pauseButton = new Button("PAUSE");
		Button resetButton = new Button("RESET");
		
		startButton.addActionListener(e -> {controller.startSimulation(); controller.resetTimer();});
		pauseButton.addActionListener(e -> {controller.pauseSimulation(); controller.resetTimer();});
		resetButton.addActionListener(e -> {controller.resetSimulation(); controller.resetTimer();});
		
		add(startButton);
		add(pauseButton);
		add(resetButton);
		add(timeLabel);
	}

	@Override
	public void onObserverSignal(EventType event) {
		if(event == EventType.CLOCK) {
			timeLabel.setText("SIMULATION TIME: " + Controller.getInstance().getSimulationTimeString());
		}
		
	}

}
