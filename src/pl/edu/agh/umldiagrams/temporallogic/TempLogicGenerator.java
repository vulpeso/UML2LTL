package pl.edu.agh.umldiagrams.temporallogic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.umldiagrams.config.Config;
import pl.edu.agh.umldiagrams.exceptions.GenerateLogicException;
import pl.edu.agh.umldiagrams.patternmodel.PatternPart;

public class TempLogicGenerator {
	
	private TempLogicFileParser fileParser;
	private String logic;
	private List<TempLogicPattern> tempLogicPatterns = new ArrayList<TempLogicPattern>();
	
	public TempLogicGenerator(final String fileName, Config config ){
		fileParser = new TempLogicFileParser(fileName,config);
	}
	
	public void generateLogic(PatternPart patternPart) throws GenerateLogicException {
		tempLogicPatterns = fileParser.parseFile();
		logic = patternPart.getLogic(tempLogicPatterns);
		
		//System.out.println(logic);
	}
	
	public String getLogic()
	{
		return logic;
	}
	
	public boolean saveToFile(String fileName){
		File file = new File(fileName);
		return saveToFile(file);
	}
	
	public boolean saveToFile(File file){
		FileWriter out = null;
		try{
			out = new FileWriter(file);
			out.write(logic);
			out.close();
		}catch(IOException e){
			try{
				if(out!=null){
					out.close();
				}
			}catch(Exception ex){}//Just closing in exception - whatever
				
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getLTL() {
		return logic;
	}

	
	
}
