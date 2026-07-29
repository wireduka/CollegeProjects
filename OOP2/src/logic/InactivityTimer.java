package logic;

import java.awt.Frame;

import gui.CountdownDialog;

public class InactivityTimer extends Thread{
	
	private static final int TIMEOUT = 60000;
	private volatile long lastAction = System.currentTimeMillis();
	private volatile boolean countdownActive = false;
	private volatile boolean paused = false;
	private Frame owner;
	
	public InactivityTimer(Frame owner) {
		this.owner = owner;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			try {
				Thread.sleep(500);
				
				long elapsedTime = System.currentTimeMillis();
				elapsedTime = elapsedTime - lastAction;
				if(elapsedTime >= TIMEOUT && !countdownActive && !paused) {
					
					countdownActive = true;
					CountdownDialog cd = new CountdownDialog(owner);
					
					for(int i = 5; i > 0; i --) {
						
						if(!cd.isVisible()) break;
						if(cd.isConfirmed()) break;
						
						cd.setCount(i);
						Thread.sleep(1000);
						
					}
					if(!cd.isVisible() || cd.isConfirmed()) {
						resetTimer();
					}
					else
						System.exit(0);
					countdownActive = false;

				}
			}
			catch(InterruptedException e) {
				return;
			}
		}
	}
	
	public void resetTimer() {lastAction = System.currentTimeMillis();}
	public void pauseTimer() {paused = true;}
	public void resumeTimer() {paused = false; resetTimer();}
}
