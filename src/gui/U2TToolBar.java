package gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class U2TToolBar extends JToolBar {
	MainFrame parent;
	
	public U2TToolBar(MainFrame parent) {
		this.parent = parent;


        ImageIcon iconNew = new ImageIcon("img/png/New document.png");
        ImageIcon iconOpen = new ImageIcon("img/png/Folder.png");
        ImageIcon iconSave = new ImageIcon("img/png/Save.png");
        ImageIcon iconGo = new ImageIcon("img/png/Go.png");
        ImageIcon iconProve = new ImageIcon("img/png/OK.png");

        /* unused */
        JButton newb = new JButton(iconNew);
        /* unused */
        JButton openb = new JButton(iconOpen);
        
        JButton saveb = new JButton(iconSave);
        saveb.setToolTipText("Save generated LTL logic to file");
        saveb.addActionListener((ActionEvent event) -> {
            parent.saveLTL(event);
        });
        
        JButton gob = new JButton(iconGo);
        gob.setToolTipText("Generate LTL logic based on UML model");
        gob.addActionListener((ActionEvent event) -> {
        	File tmpFileUml = new File("tmp/uml.xml");
        	File tmpFileReq = new File("tmp/req.txt");
        	MainFrame.saveToFile(tmpFileUml, parent.modelUML.getTextArea().getText());
        	MainFrame.saveToFile(tmpFileReq, parent.requirements.getTextArea().getText());
        	try{
        		parent.generator.generateLogic("tmp/uml.xml", "tmp/req.txt", "res/conf.xml");
        		//parent.generator.generateLogic("uml.xml", "ltl.txt", "res/conf.xml");
        	}catch(Exception e){
        		parent.debugPane.actionLog("LTL generation failed: Configuration file error\n");
        		parent.debugPane.debugClear();
        		parent.debugPane.debugLog("LTL generation failed: Configuration file error\n");
        	}
        });
        
        JButton proveb = new JButton(iconProve);
        proveb.setToolTipText("Prove generated LTL logic");
        proveb.addActionListener((ActionEvent event) -> {
        	File tmpFileLTL = new File("tmp/ltl.lout");
        	String result;
        	MainFrame.saveToFile(tmpFileLTL, parent.ltlResult.getTextArea().getText());
        	try{
        		parent.debugPane.debugClear();
        		parent.debugPane.debugLog("LTL proving started...\n");
        		result = "Satisfiable"; //U2TProverFacade.invokeProver();
        		parent.debugPane.actionLog("LTL proving succeed.\n");
        		parent.debugPane.reasoningLog(result);
        		parent.debugPane.debugLog("LTL proving succeed. Check Reasoning tab for more details.\n");
        	}catch(Exception e){
        		parent.debugPane.actionLog("LTL proving failed.\n");
        		parent.debugPane.debugClear();
        		parent.debugPane.debugLog("LTL proving failed.\n");
        	}
        });

        add(newb);
        add(openb);
        add(saveb);
        addSeparator();
        add(gob);
        add(proveb);
        add(Box.createHorizontalGlue());
    }
	
	
}
