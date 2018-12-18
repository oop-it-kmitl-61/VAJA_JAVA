package com.vaja.voice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SpeechRecognize {
	public static void main(String[] args) {
	    String[] arg = new String[]{"-u root", "-h localhost"};

	    try {
	        String ss = null;
	        Runtime obj = null;
	        Process p = Runtime.getRuntime().exec("Terminal.exe /c start dir ");
	        BufferedWriter writeer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
	        writeer.write("dir");
	        writeer.flush();
	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	        System.out.println("Here is the standard output of the command:\n");
	        while ((ss = stdInput.readLine()) != null) {
	            System.out.println(ss);
	        }
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((ss = stdError.readLine()) != null) {
	            System.out.println(ss);
	        }

	    } catch (IOException e) {
	        System.out.println("FROM CATCH" + e.toString());
	    }

	}
}