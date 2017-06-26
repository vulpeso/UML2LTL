package pl.edu.agh.settings;



public class Settings {
	private static Settings instance = new Settings();
	private boolean printDetails = false;
	
	public static Settings getInstance() {
		return instance;
	}

	public boolean getPrintDetails() {
		return printDetails;
	}

	public void setPrintDetails(boolean printDetails) {
		this.printDetails = printDetails;
	}
}
