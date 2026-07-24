package gui;

import java.awt.Frame;

public class TextDialog extends DefaultDialog{

	public TextDialog(Frame owner, String title, String... messages) {
		
		// Third parameter defines if dialog is modal
		super(owner,title,messages);
		setSize(500,150);
		setLocationRelativeTo(owner);
		setModal(true);

		// Necessary in all classes that extend DefaultDialog
		setVisible(true);

	}
}