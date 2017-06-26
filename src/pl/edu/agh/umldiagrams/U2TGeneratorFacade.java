package pl.edu.agh.umldiagrams;

import gui.MainFrame;
import pl.edu.agh.settings.Settings;
import pl.edu.agh.umldiagrams.config.Config;
import pl.edu.agh.umldiagrams.exceptions.ConfigException;
import pl.edu.agh.umldiagrams.exceptions.GenerateLogicException;
import pl.edu.agh.umldiagrams.exceptions.ParseException;
import pl.edu.agh.umldiagrams.model.ActivityDiagram;
import pl.edu.agh.umldiagrams.model.ModelDiagramNode;
import pl.edu.agh.umldiagrams.parser.Parser;
import pl.edu.agh.umldiagrams.patternmodel.PatternPart;
import pl.edu.agh.umldiagrams.patterns.PatternsFinder;
import pl.edu.agh.umldiagrams.temporallogic.TempLogicGenerator;

public class U2TGeneratorFacade {
	private MainFrame parent;
	
	public U2TGeneratorFacade(MainFrame parent){
		this.parent = parent;
	}
	
	public void generateLogic(String inFile, String ltlFile, String configFile) throws 

ConfigException{	

		parent.debugPane.actionLog("LTL generating process started\n");
		parent.debugPane.debugClear();
		parent.debugPane.debugLog("LTL generating process started...\n");
		Settings.getInstance().setPrintDetails(false);
		Config config = new Config(configFile);
		Parser parser = new Parser(config);
		try {
			parser.parseFile(inFile);
			parent.debugPane.debugLog("XML File parsed. Model validated.\n");
			for(ActivityDiagram diagram: parser.getParsedDiagramController().getDiagrams()) {
				PatternsFinder patternsFinder = new PatternsFinder();
				ModelDiagramNode node = patternsFinder.convertActivityDiagramToComposite(diagram, 

config);
				PatternPart patternPart = patternsFinder.getMainPattern();
				if(node != null && patternPart!=null) {
					parent.debugPane.debugLog("Pattern found.\n");
				}else{
					parent.debugPane.debugLog("Error: This diagram is not valid!\n");
					parent.debugPane.actionLog("Failure! LTL logic generation failed.\nCheck debug log for more information\n");
					return;
				}
				TempLogicGenerator tempLogicGen = new TempLogicGenerator(ltlFile, config);
				tempLogicGen.generateLogic(patternPart);
				//tempLogicGen.saveToFile(outFile);
				String result = tempLogicGen.getLogic();
				parent.ltlResult.getTextArea().setText(result);
				parent.debugPane.debugLog("Success!\n");
				parent.debugPane.actionLog("Success! LTL logic generated.\n");
			}
		} catch (ParseException e) {
			parent.debugPane.debugLog("Error: "+inFile+"! " + e.getMessage());
			parent.debugPane.actionLog("Failure! LTL logic generation failed.\nCheck debug log for more information\n");
		} catch (GenerateLogicException e){
			parent.debugPane.debugLog("Error: "+e.getMessage()); 
			parent.debugPane.actionLog("Failure! LTL logic generation failed.\nCheck debug log for more information\n");
		}
	}
	/*
	public static void main(String[] args){
		try{
			generateLogic("uml.xml", "ltl.txt", "out.lout", "res/conf.xml");
		}catch(Exception e){
			//System.out.println("Logic generation failed");
		}
	}*/
}