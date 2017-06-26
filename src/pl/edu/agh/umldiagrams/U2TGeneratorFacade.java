package pl.edu.agh.umldiagrams;

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
	
	private static void generateLogic(String inFile, String ltlFile, String outFile, String configFile) throws ConfigException{	
		
		Settings.getInstance().setPrintDetails(false);
		Config config = new Config(configFile);
		Parser parser = new Parser(config);
		try {
			parser.parseFile(inFile);
			//out.println(parseOK);
			for(ActivityDiagram diagram: parser.getParsedDiagramController().getDiagrams()) {
				PatternsFinder patternsFinder = new PatternsFinder();
				ModelDiagramNode node = patternsFinder.convertActivityDiagramToComposite(diagram, config);
				PatternPart patternPart = patternsFinder.getMainPattern();
				if(node != null && patternPart!=null) {
					//out.println(patternOK);
					//out.println("#PATTERN: "+patternPart.getPrintString());
				}else{
					//out.println("Error: This diagram is not valid!");
					return;
				}
				TempLogicGenerator tempLogicGen = new TempLogicGenerator(ltlFile, config);
				tempLogicGen.generateLogic(patternPart);
				tempLogicGen.saveToFile(outFile);
				//out.println(success);
				//out.println(endConnection);
			}
		} catch (ParseException e) {
			//out.println("Error: "+inFile+"! " + e.getMessage());
		} catch (GenerateLogicException e){
			//out.println("Error: "+e.getMessage()); 
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