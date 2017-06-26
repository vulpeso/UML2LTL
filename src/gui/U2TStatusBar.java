package gui;

import javax.swing.Box;
import javax.swing.JLabel;

public class U2TStatusBar extends JLabel {
	private static final long serialVersionUID = -716685039288790872L;
	
	public U2TStatusBar(){
		super("Status: ok");
		add(Box.createHorizontalGlue());
	}
	
	public void SetStatus(String stat)
	{
		this.setText(stat);
	}
}
