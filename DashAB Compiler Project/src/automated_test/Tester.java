//Tested to work
package automated_test;

import java.io.*;
import java.util.*;

public class Tester {

	public static String dash;
	public static String our;
	public static String llc;
	public static String specific;
	public static int debug;
	public static int dash_test;
	public static int llc_test;
	public static int dash_compare_test;
	public static int llc_compare_test;
	public static int specific_test;
	public static int windows = 0;
	public static int multithread;

	public static void main(String[] args) {

		long exec_time = System.currentTimeMillis();

		String line = null;

		//Read configuration file
		try{
			FileReader file = new FileReader("config.txt");
			BufferedReader reader = new BufferedReader(file);

			System.out.print("Parameters enabled: ");
			while ((line = reader.readLine()) != null) {
				if(line.indexOf("#1# DASH compiler command:") != -1){
					dash = reader.readLine();
				}
				if(line.indexOf("#2# Our compiler command:") != -1){
					our = reader.readLine();
				}
				if(line.indexOf("#3# LLC that we are using:") != -1){
					llc = reader.readLine();
				}
				if(line.indexOf("#4# DEBUG MODE: ON") != -1){
					debug = 1;
					System.out.print("[Debug] ");
				}
				if(line.indexOf("#5# DASH testing: ON") != -1){
					dash_test = 1;
					System.out.print("[Dash testing] ");
				}
				if(line.indexOf("#6# Compare output for DASH: ON") != -1){
					dash_compare_test = 1;
					System.out.print("[Expected output for DASH testing] ");
				}
				if(line.indexOf("#7# LLC testing: ON") != -1){
					llc_test = 1;
					System.out.print("[LLC testing] ");
				}
				if(line.indexOf("#8# Compare output for LLC: ON") != -1){
					llc_compare_test = 1;
					System.out.print("[Expected output for LLC testing] ");
				}
				if(line.indexOf("#9# Specific Test: ON") != -1){
					specific_test = 1;
					System.out.println("[WARNING RUNNING ONE TEST ONLY]");
				}
				if(line.indexOf("#10# Specific Test name:") != -1){
					specific = reader.readLine();
				}
				if(line.indexOf("#11# Threads:") != -1){
					multithread = Integer.parseInt(reader.readLine());
					System.out.println("Threads: "+multithread);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No config.txt found");
		} catch (IOException e) {
			System.out.println("Error reading from configuration file");
		}

		//Show info
		System.out.println("\nWe are now going to start testing with the following compilers:\n");

		try{
			if(Tester.dash_test == 1){
				Process p = Runtime.getRuntime().exec(dash + " --version");
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				System.out.println("### DASH VERSION ###");
				while ((line = stdInput.readLine()) != null) {
					System.out.println(line);
				}

				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				while ((line = stdError.readLine()) != null) {
					throw new Exception("DASH");
				}

			}

			if(Tester.llc_test == 1){
				Process p = Runtime.getRuntime().exec(our);
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				System.out.println("### OUR COMPILER ###");
				while ((line = stdInput.readLine()) != null) {
					System.out.println(line);
				}


				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				while ((line = stdError.readLine()) != null) {
					//throw new Exception("Our compiler");
				}
			}

			//Start loading all the test files
			System.out.println("Looks like they were valid commands, starting tests...");
			if(multithread != 0)
				Test_loader.load();

		} catch (IOException e) {
			System.out.println("Error starting process " + e);
		} catch (Exception e) {
			System.out.println(e +" command invalid");
		}
		exec_time = System.currentTimeMillis() - exec_time;
		System.out.println("Execution time: " + exec_time + "ms");

	}

}
