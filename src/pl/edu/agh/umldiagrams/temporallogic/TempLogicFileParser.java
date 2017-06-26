package pl.edu.agh.umldiagrams.temporallogic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import pl.edu.agh.umldiagrams.config.Config;
import pl.edu.agh.umldiagrams.patternmodel.PatternPart.PatternType;

public class TempLogicFileParser {
	
	private final String fileName;
	private final String regEx;
	private ArrayList<TempLogicPattern> tempLogicPatterns = null;
	private Config config;
	public TempLogicFileParser(final String fileName, Config config) {
		this.fileName = fileName;
		this.config = config;
		regEx = "("	+ config.getDecConfig().getName()	+".*)|("+config.getParConfig().getName()+".*)|("
					+ config.getLoopConfig().getName()	+".*)|("+ config.getSeqConfig().getName()+".*)|("
					+ config.getSynchConfig().getName()	+".*)|("+ config.getExcConfig().getName()+".*)|("
					+ config.getMergConfig().getName()	+".*)|("+ config.getSeqSeqConfig().getName()+".*)";
	}
	
	public List<TempLogicPattern> parseFile(){
		tempLogicPatterns = new ArrayList<TempLogicPattern>();
		Scanner input;
		try{
			File file = new File(fileName);
			input = new Scanner(file);
		}catch(Exception e){
			System.out.println("Nie odnaleziono pliku!");
			return tempLogicPatterns;
		}
		if(!input.hasNextLine()) return tempLogicPatterns;
		String line = input.nextLine();
		while(input.hasNextLine()) {
			if(line.toLowerCase().matches(regEx)){
				TempLogicPattern tlp = new TempLogicPattern();
				if(line.toUpperCase().startsWith(config.getDecConfig().getName().toUpperCase())){
					tlp.setName(config.getDecConfig().getName());
					tlp.setType(PatternType.DEC);
				}else if(line.toUpperCase().startsWith(config.getSeqSeqConfig().getName().toUpperCase())){
					tlp.setName(config.getSeqSeqConfig().getName());
					tlp.setType(PatternType.SEQSEQ);
				}else if(line.toUpperCase().startsWith(config.getSeqConfig().getName().toUpperCase())){
					tlp.setName(config.getSeqConfig().getName());
					tlp.setType(PatternType.SEQ);
				}else if(line.toUpperCase().startsWith(config.getParConfig().getName().toUpperCase())){
					tlp.setName(config.getParConfig().getName());
					tlp.setType(PatternType.PAR);
				}else if(line.toUpperCase().startsWith(config.getLoopConfig().getName().toUpperCase())){
					tlp.setName(config.getLoopConfig().getName());
					tlp.setType(PatternType.LOOP);
				}else if(line.toUpperCase().startsWith(config.getExcConfig().getName().toUpperCase())){
					tlp.setName(config.getExcConfig().getName());
					tlp.setType(PatternType.EXC);
				}else if(line.toUpperCase().startsWith(config.getSynchConfig().getName().toUpperCase())){
					tlp.setName(config.getSynchConfig().getName());
					tlp.setType(PatternType.SYNCH);
				}else if(line.toUpperCase().startsWith(config.getMergConfig().getName().toUpperCase())){
					tlp.setName(config.getMergConfig().getName());
					tlp.setType(PatternType.MERG);
				}
				parseArgs(line,tlp);
				line = input.nextLine();
				parseIniFin(line,tlp);
				String logic = "";
				while(input.hasNextLine()){
					line = input.nextLine();
					if(line.toLowerCase().matches(regEx)){
						break;
					}
					logic = logic.concat(line+"\n");
				}
				tlp.setLogic(logic);
				tempLogicPatterns.add(tlp);
			}
		}

		input.close();
		return tempLogicPatterns;
	}
	
	private boolean parseArgs(String line, TempLogicPattern tlp){
		line = line.replaceAll(" ", "");
		line = line.replaceFirst("^.*"+Pattern.quote("("), "");
		line = line.replaceFirst(Pattern.quote(")")+".*$", "");
		String args[] = line.split(",");
		if(args.length >= 2 || args.length == 3){
			tlp.setArg1(args[0]);
			tlp.setArg2(args[1]);
			if(args.length == 3){
				tlp.setArg3(args[2]);
			}
			return true;
		}else{
			return false;
		}
	}
	private boolean parseIniFin(String line, TempLogicPattern tlp){
		line = line.replaceFirst("^ini=", "");
		String ini = line.replaceFirst("/.*fin=.*$", "");
		ini = ini.replace(" ", "");
		String fin = line.replaceFirst("^.*/.*fin=", "");
		fin = fin.replace(" ", "");
		tlp.setIni(ini);
		tlp.setFin(fin);
		return true;
	}
}
