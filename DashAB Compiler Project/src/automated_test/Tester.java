package automated_test;

import java.io.*;

public class Tester {

	public static String dash;
	public static String our;
	public static String llc;
	public static int debug;
	public static int dash_test;
	public static int llc_test;
	public static int dash_compare_test;
	public static int llc_compare_test;

	public static void main(String[] args) {

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
			}
		} catch (FileNotFoundException e) {
			System.out.println("No config.txt found");
		} catch (IOException e) {
			System.out.println("Error reading from configuration file");
		}

		//Show info
		System.out.println("\nWe are now going to start testing with the following:\n");

		try{
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


			p = Runtime.getRuntime().exec(our);
			stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			System.out.println("### OUR COMPILER ###");
			while ((line = stdInput.readLine()) != null) {
				System.out.println(line);
			}

			stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((line = stdError.readLine()) != null) {
				//throw new Exception("Our compiler");
			}

			//Start loading all the test files
			System.out.println("Looks like they were valid commands, starting tests...");
			Test_loader.load();

		} catch (IOException e) {
			System.out.println("Error starting process");
		} catch (Exception e) {
			System.out.println(e +" command invalid");
		}

	}

}
