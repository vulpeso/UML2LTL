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
	ScrollableTextArea tDebugging;
	ScrollableTextArea tActionLog;
	
	public U2TDebugPanel(JFrame parent){
		this.parent = parent;
		
		JPanel cReasoning;
		JPanel cDebugging;
		JPanel cActionLog;

		cReasoning = new JPanel(new BorderLayout());
		cDebugging = new JPanel(new BorderLayout());
		cActionLog = new JPanel(new BorderLayout());
		
		tReasoning = new ScrollableTextArea("");
		tDebugging = new ScrollableTextArea("");
		tActionLog = new ScrollableTextArea("");
		
		tReasoning.disable();
		tDebugging.disable();
		tActionLog.disable();
		
		cReasoning.add(tReasoning);
		cDebugging.add(tDebugging);
		cActionLog.add(tActionLog);
  
        addTab("Debugging", cDebugging);
        addTab("Action Log", cActionLog);
        addTab("Reasoning", cReasoning);
	}
	public void actionLog(String msg){
		tActionLog.getTextArea().append(msg);
	}
	public void debugClear(){
		tDebugging.getTextArea().setText("");
	}
	public void debugLog(String msg){
		tDebugging.getTextArea().append(msg);
	}
	public void reasoningLog(String str){
		tReasoning.getTextArea().setText(str);
	}
}
