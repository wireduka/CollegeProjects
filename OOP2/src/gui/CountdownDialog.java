package gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Label;

public class CountdownDialog extends DefaultDialog implements WindowClosing{
	
	private Label countLabel = new Label("5",Label.CENTER);
	
	public CountdownDialog(Frame owner) {
		super(owner,"Inactivity!","Program will close in: ");
		add(countLabel,BorderLayout.CENTER);
		
		setSize(500,150);
		setLocationRelativeTo(owner);


		setVisible(true);
	}
	
	public void setCount(int seconds) {
		countLabel.setText(String.valueOf(seconds));
	}
	

}
