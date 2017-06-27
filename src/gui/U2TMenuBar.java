package gui;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class U2TMenuBar extends JMenuBar {
	MainFrame parent;
	
	public U2TMenuBar(MainFrame parent) {
		this.parent = parent;
        ImageIcon iconNew = new ImageIcon("img/png/New document.png");
        ImageIcon iconOpen = new ImageIcon("img/png/Folder.png");
        ImageIcon iconSave = new ImageIcon("img/png/Save.png");
        ImageIcon iconExit = new ImageIcon("img/png/Exit.png");

        JMenu fileMenu = new JMenu("File");

        JMenu impMenu = new JMenu("Import");

        JMenuItem impUML = new JMenuItem("Import UML diagram (*.xml)...");
        impUML.addActionListener((ActionEvent event) -> {
            parent.importUML(event);
        });
        
        JMenuItem impLTL = new JMenuItem("Import LTL patterns...");
        impLTL.addActionListener((ActionEvent event) -> {
            parent.importLTL(event);
        });
        
        impMenu.add(impUML);
        impMenu.add(impLTL);

        /* unused */
        JMenuItem newMi = new JMenuItem("New", iconNew);
        /* unused */
        JMenuItem openMi = new JMenuItem("Open", iconOpen);

        JMenuItem saveMi = new JMenuItem("Save", iconSave);
        saveMi.setToolTipText("Save generated LTL logic to file");
        saveMi.addActionListener((ActionEvent event) -> {
            parent.saveLTL(event);
        });

        JMenuItem exitMi = new JMenuItem("Exit", iconExit);
        exitMi.setToolTipText("Exit application");

        exitMi.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        fileMenu.add(newMi);
        fileMenu.add(openMi);
        fileMenu.add(saveMi);
        fileMenu.addSeparator();
        fileMenu.add(impMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMi);

        add(fileMenu);

    }
}
