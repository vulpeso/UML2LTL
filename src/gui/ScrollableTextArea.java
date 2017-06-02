package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ScrollableTextArea extends JPanel {

	private static final long serialVersionUID = -8842283538662009639L;
	JTextArea text;
	
	public ScrollableTextArea(){
		super(new BorderLayout());
		text = initTextArea();
		JScrollPane scroll = new JScrollPane(text);
		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		add(scroll, BorderLayout.CENTER);
	}
	
	public JTextArea getTextArea(){
		return text;
	}
	
	private static JTextArea initTextArea(){
		JTextArea text = new JTextArea();
		//text.setPreferredSize(new Dimension(1000,200));  
		text.setLineWrap(true);
		return text;
	}
}
