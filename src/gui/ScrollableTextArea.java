package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ScrollableTextArea extends JPanel {

	private static final long serialVersionUID = -8842283538662009639L;
	JTextArea text;
	
	public ScrollableTextArea(String name){
		super(new BorderLayout());
		text = initTextArea();
		JLabel label = new JLabel(name);
		JScrollPane scroll = new JScrollPane(text);
		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		add(label, BorderLayout.NORTH);
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
