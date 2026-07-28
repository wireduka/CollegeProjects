package logic;

import java.util.ArrayList;
import java.util.List;

public class Clock extends Thread implements Observable {
	
	public static final int DEFAULT_CLOCK_SPEED = 1000;
	public static final int MINUTES_PER_TICK = 10;
	
	private int clockSpeed = DEFAULT_CLOCK_SPEED;
	private int time = 0;
	
	private List<Observer> observers = new ArrayList<>();
	
	private volatile boolean paused = false;
	
	public Clock(int clockSpeed) {this.clockSpeed = clockSpeed;}
	public Clock() {this(DEFAULT_CLOCK_SPEED);}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				
				synchronized (this) {
					while(paused)
						wait();
				}
				
				Thread.sleep(clockSpeed);
				if(paused) continue;
				time += MINUTES_PER_TICK;
				notifyObservers();
				
			}
		}
		catch(InterruptedException e) {return;}
	}
	public int getClockSpeed() {return clockSpeed;}
	public int getSimulationTime() {return time;}
	
	public void setClockSpeed(int clockSpeed) {this.clockSpeed = clockSpeed;}
	
	public synchronized void pauseClock() {paused = true;}
	public synchronized void resumeClock() {paused = false; notifyAll();}
	public synchronized void resetClock() {time = 0; paused = true; notifyObservers();}
	
	@Override
	public void addObserver(Observer o) {
		observers.add(o);
		
	}
	@Override
	public void notifyObservers() {
		for(Observer o : observers) {o.onObserverSignal(EventType.CLOCK);}
		
	}
	
	public String getClockString() {
		
		int days = time / 1440;
	    int hours = (time % 1440) / 60;
	    int minutes = time % 60;

	    return String.format("%02d:%02d:%02d", days, hours, minutes);
	}
	
	
	
	

}

	
	
