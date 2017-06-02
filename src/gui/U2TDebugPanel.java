package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class U2TDebugPanel extends JTabbedPane {
	private static final long serialVersionUID = 1440358589399800880L;
	private JFrame parent;
	
	ScrollableTextArea tReasoning;
	ScrollableTextArea tReporting;
	ScrollableTextArea tAnalysis;
	ScrollableTextArea tDebugging;
	
	public U2TDebugPanel(JFrame parent){
		this.parent = parent;
		
		JPanel cReasoning;
		JPanel cReporting;
		JPanel cAnalysis;
		JPanel cDebugging;

		cReasoning = new JPanel(new BorderLayout());
		cReporting = new JPanel(new BorderLayout());
		cAnalysis = new JPanel(new BorderLayout());
		cDebugging = new JPanel(new BorderLayout());
		
		tReasoning = new ScrollableTextArea();
		tReporting = new ScrollableTextArea();
		tAnalysis = new ScrollableTextArea();
		tDebugging = new ScrollableTextArea();
		
		cReasoning.add(tReasoning);
		cReporting.add(tReporting);
		cAnalysis.add(tAnalysis);
		cDebugging.add(tDebugging);
  
        addTab("Reasoning", cReasoning);
        addTab("Reporting", cReporting);
        addTab("Analysis", cAnalysis);
        addTab("Debugging", cDebugging);
	}
	public void debugLog(String msg){
		tDebugging.getTextArea().append(msg);
	}
}
