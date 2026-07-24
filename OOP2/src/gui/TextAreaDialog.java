package gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;

public class TextAreaDialog extends DefaultDialog{
	
	private TextArea input = new TextArea();

	public TextAreaDialog(Frame owner, String ...messages) {
		
		// Third parameter defines if dialog is modal
		super(owner,"Enter values",messages);
		setSize(1000,400);
		setLocationRelativeTo(owner);
		setModal(true);
		add(input,BorderLayout.CENTER);
		
		// Necessary in all classes that extend DefaultDialog
		setVisible(true);

	}
	public String getInput() {
		return input.getText();
		
	}
}
