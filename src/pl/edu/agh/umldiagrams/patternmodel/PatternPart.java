package pl.edu.agh.umldiagrams.patternmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import pl.edu.agh.umldiagrams.exceptions.GenerateLogicException;
import pl.edu.agh.umldiagrams.temporallogic.TempLogicPattern;

public class PatternPart {
	public enum PatternType {
		SEQ, SEQSEQ, PAR, DEC, LOOP, ARG, MERG, EXC, SYNCH
	}

	private final PatternType patternType;
	private final String id;
	private String name;

	private Vector<PatternPart> arguments = new Vector<PatternPart>();

	public PatternPart(final PatternType patternType, String id) {
		this.patternType = patternType;
		this.id = id;
	}

	public PatternPart(final PatternType patternType, String id, String name) {
		this.patternType = patternType;
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public PatternType getPatternType() {
		return patternType;
	}

	public Vector<PatternPart> getArguments() {
		return arguments;
	}

	public String getPrintString() {
		switch (patternType) {
		case ARG:
			return name;
		case DEC:
			return "dec(" + arguments.get(0).getPrintString() + ","
					+ arguments.get(1).getPrintString() + ","
					+ arguments.get(2).getPrintString() + ")";
		case LOOP:
			return "loop(" + arguments.get(0).getPrintString() + ","
					+ arguments.get(1).getPrintString() + ","
					+ arguments.get(2).getPrintString() + ")";
		case PAR:
			return "par(" + arguments.get(0).getPrintString() + ","
					+ arguments.get(1).getPrintString() + ","
					+ arguments.get(2).getPrintString() + ")";
		case SEQ:
			return "seq(" + arguments.get(0).getPrintString() + ","
					+ arguments.get(1).getPrintString() + ")";
		case SEQSEQ:
			return "seqseq(" + arguments.get(0).getPrintString() + ","
					+ arguments.get(1).getPrintString() + ","
					+ arguments.get(2).getPrintString() + ")";
		case EXC:
			return "exc(" + arguments.get(0).getPrintString() + ","
					+ arguments.get(1).getPrintString() + ","
					+ arguments.get(2).getPrintString() + ")";
		case SYNCH:
			return "synch(" + arguments.get(0).getPrintString() + ","
					+ arguments.get(1).getPrintString() + ","
					+ arguments.get(2).getPrintString() + ")";
		case MERG:
			return "merg(" + arguments.get(0).getPrintString() + ","
					+ arguments.get(1).getPrintString() + ","
						+ arguments.get(2).getPrintString() + ")";
		default:
			return "";
		}
	}

	public String getFin(final List<TempLogicPattern> tempLogicPatterns)
			throws GenerateLogicException {

		if (patternType == PatternType.ARG) {
			return name;
		} else {
			TempLogicPattern tempLogicPattern = getMyTempLogicPattern(tempLogicPatterns);

			if (tempLogicPattern == null) {
				throw new GenerateLogicException("Cannot find fin for pattern "
						+ patternType);
			}
			String result = tempLogicPattern.getFin();
			if (tempLogicPattern.isArg1InFinPattern()) {
				result = result.replace(tempLogicPattern.getArg1(), arguments
						.get(0).getFin(tempLogicPatterns));
			}
			if (tempLogicPattern.isArg2InFinPattern()) {
				result = result.replace(tempLogicPattern.getArg2(), arguments
						.get(1).getFin(tempLogicPatterns));
			}
			if (tempLogicPattern.isArg3InFinPattern()) {
				result = result.replace(tempLogicPattern.getArg3(), arguments
						.get(2).getFin(tempLogicPatterns));
			}
			
			result = result.replace("|", " | ");
			result = result.replace("&", " & ");
			return result;
		}
	}

	public String getIni(final List<TempLogicPattern> tempLogicPatterns)
			throws GenerateLogicException {

		if (patternType == PatternType.ARG) {
			return name;
		} else {
			TempLogicPattern tempLogicPattern = getMyTempLogicPattern(tempLogicPatterns);

			if (tempLogicPattern == null) {
				throw new GenerateLogicException("Cannot find ini for pattern "
						+ patternType);
			}
			String result = tempLogicPattern.getIni();
			if (tempLogicPattern.isArg1InIniPattern()) {
				result = result.replace(tempLogicPattern.getArg1(), arguments
						.get(0).getIni(tempLogicPatterns));
			}
			if (tempLogicPattern.isArg2InIniPattern()) {
				result = result.replace(tempLogicPattern.getArg2(), arguments
						.get(1).getIni(tempLogicPatterns));
			}
			if (tempLogicPattern.isArg3InIniPattern()) {
				result = result.replace(tempLogicPattern.getArg3(), arguments
						.get(2).getIni(tempLogicPatterns));
			}

			result = result.replace("|", " | ");
			result = result.replace("&", " & ");
			return result;
		}
	}
	
	public String getLogic(final List<TempLogicPattern> tempLogicPatterns) throws GenerateLogicException {
		if (patternType == PatternType.ARG) {
			return "";
		} 
		String result = "";
		for(PatternPart patternPart: arguments) {
			result += patternPart.getLogic(tempLogicPatterns);
		}
		TempLogicPattern tempLogicPattern = getMyTempLogicPattern(tempLogicPatterns);
		if (tempLogicPattern == null) {
			throw new GenerateLogicException("Cannot find fin for pattern "
					+ patternType);
		}
		
		String logic = tempLogicPattern.getLogic();
		ArrayList<String> fs = new ArrayList<String>(3);
		for (int i = 0; i < arguments.size(); i++) {
			if(arguments.get(i).getPatternType() == PatternType.ARG) {
				fs.add(arguments.get(i).getName());
			}else{
				String ini = arguments.get(i).getIni(tempLogicPatterns);
				if(ini.contains("|")) {
					ini = "(" + ini + ")";
				}
				String fin = arguments.get(i).getFin(tempLogicPatterns);
				if(fin.contains("|")) {
					fin = "(" + fin + ")";
				}
				fs.add("(" + ini + " | " + fin + ")") ;

			}
			
		}
		logic = logic.replace(tempLogicPattern.getArg1(), fs.get(0));
		logic = logic.replace(tempLogicPattern.getArg2(), fs.get(1));
		if(arguments.size() > 2) {
			logic = logic.replace(tempLogicPattern.getArg3(), fs.get(2));
		}
		result = result + logic + "\n";
		return result;
	}

	private TempLogicPattern getMyTempLogicPattern(final List<TempLogicPattern> tempLogicPatterns) {

		for (TempLogicPattern t : tempLogicPatterns) {
			if (t.getType() == patternType) {
				return t;
			}
		}
		return null;
	}
}
