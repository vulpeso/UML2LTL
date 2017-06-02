package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;


public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -2707712944901661771L;
	
	public JToolBar toolbar;
	public mxGraphComponent graphPane;
	public ScrollableTextArea requirements;
	public ScrollableTextArea logicSpec;
	public U2TDebugPanel debugPane;
	public U2TStatusBar statusBar;
	
	public MainFrame(){
		ImageIcon webIcon = new ImageIcon("img/icon.png");
        setIconImage(webIcon.getImage());
        
        setJMenuBar(new U2TMenuBar(this));
		
		toolbar = new U2TToolBar(this);
		graphPane = new U2TModeler(this);		
		requirements = new ScrollableTextArea();
		logicSpec = new ScrollableTextArea();
		debugPane = new U2TDebugPanel(this);
		statusBar = new U2TStatusBar();
		
		createLayout(toolbar, graphPane, requirements, logicSpec, debugPane, statusBar);
		
		setTitle("UML2LTL");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
    private void createLayout(JComponent... arg) {
        JPanel pane3 = new JPanel();
        pane3.setLayout(new BorderLayout());
		pane3.add(arg[1], BorderLayout.CENTER);
		pane3.add(arg[2], BorderLayout.SOUTH);
		
		arg[2].setPreferredSize(new Dimension(arg[2].getWidth(), 100));
		arg[3].setPreferredSize(new Dimension(300, arg[2].getHeight()));
		
		JPanel pane4 = new JPanel();
        pane4.setLayout(new BorderLayout());
		pane4.add(pane3, BorderLayout.CENTER);
		pane4.add(arg[3], BorderLayout.EAST);
		
		JPanel pane2 = new JPanel();
		pane2.setLayout(new BorderLayout());
		pane2.add(arg[4], BorderLayout.CENTER);
		pane2.add(arg[5], BorderLayout.SOUTH);
		pane2.setMaximumSize(new Dimension(10000, 130));
		pane2.setPreferredSize(new Dimension(600, 130));
		pane2.setMinimumSize(new Dimension(1, 130));
		
		Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(arg[0], BorderLayout.NORTH);
        pane.add(pane4, BorderLayout.CENTER);
        pane.add(pane2, BorderLayout.SOUTH);
    }
	
	public static void main(String [ ] args){
		EventQueue.invokeLater(() -> {
			MainFrame app = new MainFrame();
            app.setVisible(true);
        });
	}
}
