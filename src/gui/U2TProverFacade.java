package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;



public class U2TProverFacade {
	String invokeProver(String[] args) throws IOException{
		Process process = new ProcessBuilder("prover/proveLTL","tmp/out.txt").start();
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		String buffer = "";
		String newline = "\n";
	
		System.out.printf("Output of running %s is:", Arrays.toString(args));
	
		while ((line = br.readLine()) != null) {
		  buffer += line + newline;
		}
		return buffer;
	}
}
