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
	JFileChooser fc;
	
	public U2TToolBar(MainFrame parent) {
		this.parent = parent;

		fc = new JFileChooser();

        ImageIcon iconNew = new ImageIcon("img/png/New document.png");
        ImageIcon iconOpen = new ImageIcon("img/png/Folder.png");
        ImageIcon iconSave = new ImageIcon("img/png/Save.png");
        ImageIcon iconGo = new ImageIcon("img/png/Go.png");

        JButton newb = new JButton(iconNew);
        JButton openb = new JButton(iconOpen);
        openb.addActionListener((ActionEvent event) -> {
            openFileChooser(event);
        });
        JButton saveb = new JButton(iconSave);
        JButton gob = new JButton(iconGo);

        add(newb);
        add(openb);
        add(saveb);
        addSeparator();
        add(gob);
        add(Box.createHorizontalGlue());
    }
	
	public void openFileChooser(ActionEvent e) {
        {
            int returnVal = fc.showOpenDialog(parent);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                parent.debugPane.debugLog("import requirements from file: " + file.getName());
                loadReq(file);
            } else {
                //log.append("Open command cancelled by user." + newline);
            }
            //log.setCaretPosition(log.getDocument().getLength());
 
        //Handle save button action.
        }
    }
	
	public void loadReq(File file){
		try (FileReader reader = new FileReader(file)) {
		    parent.requirements.getTextArea().read(reader, null);
		}catch(Exception e){};
	}
}
