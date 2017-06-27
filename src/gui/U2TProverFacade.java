package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
 * Fasada uruchamia skrypt unixowego shella.
 *  
 */

public class U2TProverFacade {
	public static String invokeProver() throws IOException{
		Process process = new ProcessBuilder("prover/proveLTL","tmp/ltl.lout").start();
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		String buffer = "";
		String newline = "\n";
	
	
		while ((line = br.readLine()) != null) {
		  buffer += line + newline;
		}
		return buffer;
	}
}
