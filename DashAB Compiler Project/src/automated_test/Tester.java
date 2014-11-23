package automated_test;

import java.io.*;

public class Tester {

	public static String dash;
	public static String our;

	public static void main(String[] args) {

		String line = null;

		//Read configuration file
		try{
			FileReader file = new FileReader("config.txt");
			BufferedReader reader = new BufferedReader(file);

			while ((line = reader.readLine()) != null) {
				if(line.indexOf("DASH COMPILER COMMAND:") != -1){
					dash = reader.readLine();
				}
				if(line.indexOf("OUR COMPILER COMMAND:") != -1){
					our = reader.readLine();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No config.txt found");
		} catch (IOException e) {
			System.out.println("Error reading from configuration file");
		}

		//Show info
		System.out.println("We are now going to start testing with the following:\n");

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
				throw new Exception("Our compiler");
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
