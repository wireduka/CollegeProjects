package logic;

import java.awt.Canvas;

public class BlinkTimer extends Thread {
	
	private static final long BLINKING_TIME = 500;
	private volatile boolean blinkState = false;
	private Canvas canvas;
	
	public boolean getBlinkState() {return blinkState;}
	
	public BlinkTimer(Canvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				blinkState = !blinkState;
				canvas.repaint();
				Thread.sleep(BLINKING_TIME);
			}
		}
		catch(InterruptedException e) {return;}
	}

}
