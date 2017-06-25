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

        JButton newb = new JButton(iconNew);
        JButton openb = new JButton(iconOpen);
        openb.addActionListener((ActionEvent event) -> {
            parent.importUML(event);
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
	
	
}
