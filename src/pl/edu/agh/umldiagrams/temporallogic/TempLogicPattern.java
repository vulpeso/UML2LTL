package pl.edu.agh.umldiagrams.temporallogic;

import pl.edu.agh.umldiagrams.patternmodel.PatternPart.PatternType;

public class TempLogicPattern {
	private String name;
	private PatternType type;
	private String ini;
	private String fin;
	private String arg1;
	private String arg2;
	private String arg3;
	private String logic;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PatternType getType() {
		return type;
	}
	public void setType(PatternType type) {
		this.type = type;
	}
	public String getIni() {
		return ini;
	}
	public void setIni(String ini) {
		this.ini = ini;
	}
	public String getFin() {
		return fin;
	}
	public void setFin(String fin) {
		this.fin = fin;
	}
	public String getArg1() {
		return arg1;
	}
	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}
	public String getArg2() {
		return arg2;
	}
	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}
	public String getArg3() {
		return arg3;
	}
	public void setArg3(String arg3) {
		this.arg3 = arg3;
	}
	public String getLogic() {
		return logic;
	}
	public void setLogic(String logic) {
		this.logic = logic;
	}
	public boolean isArg1InFinPattern() {
		return arg1 == null ? false : fin.contains(arg1);
	}
	public boolean isArg2InFinPattern() {
		return arg2 == null ? false : fin.contains(arg2);
	}
	public boolean isArg3InFinPattern() {
		return arg3 == null ? false : fin.contains(arg3);
	}
	public boolean isArg1InIniPattern() {
		return arg1 == null ? false : ini.contains(arg1);
	}
	public boolean isArg2InIniPattern() {
		return arg2 == null ? false : ini.contains(arg2);
	}
	public boolean isArg3InIniPattern() {
		return arg3 == null ? false : ini.contains(arg3);
	}
}
