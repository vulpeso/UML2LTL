package gui;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class U2TMenuBar extends JMenuBar {
	JFrame parent;
	
	public U2TMenuBar(JFrame parent) {
		this.parent = parent;
        ImageIcon iconNew = new ImageIcon("img/png/New document.png");
        ImageIcon iconOpen = new ImageIcon("img/png/Folder.png");
        ImageIcon iconSave = new ImageIcon("img/png/Save.png");
        ImageIcon iconExit = new ImageIcon("img/png/Exit.png");

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu toolsMenu = new JMenu("Tools");
        JMenu preferencesMenu = new JMenu("Preferences");
        JMenu helpMenu = new JMenu("Help");

        JMenu impMenu = new JMenu("Import");

        JMenuItem newsfMi = new JMenuItem("Import newsfeed list...");
        JMenuItem bookmMi = new JMenuItem("Import bookmarks...");
        JMenuItem mailMi = new JMenuItem("Import mail...");

        impMenu.add(newsfMi);
        impMenu.add(bookmMi);
        impMenu.add(mailMi);

        JMenuItem newMi = new JMenuItem("New", iconNew);
        JMenuItem openMi = new JMenuItem("Open", iconOpen);
        JMenuItem saveMi = new JMenuItem("Save", iconSave);

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
        add(editMenu);
        add(toolsMenu);
        add(preferencesMenu);
        add(helpMenu);

    }
}
