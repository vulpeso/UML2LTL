package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

import pl.edu.agh.umldiagrams.U2TGeneratorFacade;



public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -2707712944901661771L;

	JFileChooser fc;
	
	public JToolBar toolbar;
	//UML input
	public ScrollableTextArea modelUML;
	//LTL input
	public ScrollableTextArea requirements;
	//logic generator output
	public ScrollableTextArea ltlResult;
	public U2TDebugPanel debugPane;
	public U2TStatusBar statusBar;
	public U2TGeneratorFacade generator;
	
	public static boolean saveToFile(File file, String str){
		FileWriter out = null;
		try{
			out = new FileWriter(file);
			out.write(str);
			out.close();
		}catch(IOException e){
			try{
				if(out!=null){
					out.close();
				}
			}catch(Exception ex){}
				
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public MainFrame(){
		ImageIcon webIcon = new ImageIcon("img/icon.png");
        setIconImage(webIcon.getImage());

		fc = new JFileChooser();
        setJMenuBar(new U2TMenuBar(this));
		
		toolbar = new U2TToolBar(this);
		modelUML = new ScrollableTextArea(" input: UML model in XML format");		
		requirements = new ScrollableTextArea(" input: LTL rules definition");
		ltlResult = new ScrollableTextArea(" output: Logic specification generator");
		debugPane = new U2TDebugPanel(this);
		statusBar = new U2TStatusBar();
		generator = new U2TGeneratorFacade(this);
		
		createLayout(toolbar, modelUML, requirements, ltlResult, debugPane, statusBar);
		
		setTitle("UML2LTL");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
    private void createLayout(JComponent... arg) {
        JPanel pane3 = new JPanel();
        pane3.setLayout(new BorderLayout());
		pane3.add(arg[1], BorderLayout.NORTH);
		pane3.add(arg[2], BorderLayout.CENTER);
		
		arg[1].setPreferredSize(new Dimension(arg[2].getWidth(), 200));
		arg[3].setPreferredSize(new Dimension(500, arg[2].getHeight()));
		
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
    
    // wczytuje zbiór regu³ LTL dla wzorców
    public void importLTL(ActionEvent e) {
        {
            int returnVal = fc.showOpenDialog(this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                debugPane.actionLog("import requirements from file: " + file.getName()+"\n");
                loadLTL(file);
            } else {
            	debugPane.actionLog("Open command cancelled by user.\n");
            }
            //log.setCaretPosition(log.getDocument().getLength());
        }
    }
    // wczytuje model w formacie xml
    public void importUML(ActionEvent e) {
        {
            int returnVal = fc.showOpenDialog(this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                debugPane.actionLog("import UML model from file: " + file.getName()+"\n");
                loadUML(file);
            } else {
            	debugPane.actionLog("Open command cancelled by user.\n");
            }
            //log.setCaretPosition(log.getDocument().getLength());
        }
    }	
    
    // zapisuje wygenerowan¹ logikê LTL
	public void saveLTL(ActionEvent e) {
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String ltl = this.ltlResult.getTextArea().getText();
			MainFrame.saveToFile(file, ltl);
			debugPane.actionLog("save generated LTL logc to file: " + file.getName()+"\n");
        } else {
        	debugPane.actionLog("Save command cancelled by user.\n");
        }        
    }
	
    
	private void loadLTL(File file){
		try (FileReader reader = new FileReader(file)) {
		    requirements.getTextArea().read(reader, null);
		}catch(Exception e){};
	}
	private void loadUML(File file){
		try (FileReader reader = new FileReader(file)) {
		    modelUML.getTextArea().read(reader, null);
		}catch(Exception e){};
	}
	public static void main(String [ ] args){
		EventQueue.invokeLater(() -> {
			MainFrame app = new MainFrame();
            app.setVisible(true);
        });
	}
}
