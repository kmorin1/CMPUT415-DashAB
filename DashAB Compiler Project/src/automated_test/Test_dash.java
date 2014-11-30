//Needs more testing to confirm
package automated_test;

import java.io.*;

public class Test_dash {

	public static int run(String test){
		int error = 0;
		int valid_test = 1;
		System.out.println("\n"+test + " with dash");
		String line = null;
		try{
			//Initialize writer
			PrintWriter output = new PrintWriter("output.txt", "UTF-8");

			//Compile the dash code
			if(Tester.debug == 1)
				System.out.println("***OUR COMPILER COMMAND EXECUTED >>> " + Tester.dash + " Tests/" + test);

			Process p = Runtime.getRuntime().exec(Tester.dash + " Tests/" + test);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			if (ProcessHadError(p)) {
				valid_test = 0;
				output.println("//fail");
				if(Tester.debug == 1) {
					System.out.println("***Had error creating executable");
				}
			}else{
				valid_test = 1;
				output.println("//pass");
			}
			output.flush();

			//Now run it
			if (valid_test == 1) {
				boolean hadError = ProcessHadError(p);
				
				p = Runtime.getRuntime().exec("./a.out");

				BufferedWriter input = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()) );

				String inputFileName = test.replaceFirst(".ds", ".in");
				File input_file = new File("Tests/" + inputFileName);
				if (input_file.exists()) {
					BufferedReader inputReader = null;
					try {
						inputReader = new BufferedReader(new FileReader(input_file));
						String input_line = null;

						while ((input_line = inputReader.readLine()) != null) {
							input.write(input_line);
							input.flush();
						}
						input.close();
					}
					catch (Exception e) {
						System.out.println("Exception while sending input: " + e.getMessage());
					}
					inputReader.close();
				}
				else {
					try {
						input.write(0);
						input.flush();
						input.close();
					}
					catch (Exception e) {
						// Pipe might break if input is not needed?
						//System.out.println("Exception: " + e.getMessage());
					}
				}

				hadError |= ProcessHadError(p);
				if(Tester.debug == 1) {
					if (hadError) {
						System.out.println("***Executable had an error");
					}
					else {
						System.out.println("***Executable OK");
					}
				}


				//Output results into a file
				stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = stdInput.readLine()) != null) {
					output.print(line);
					output.println();
					output.flush();
				}
			}

			if(Tester.debug == 1) {
				System.out.println("***Showing output.txt");
				BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}

				System.out.println("***About to diff");
			}

			if(Tester.dash_compare_test == 1)
				error = Test_compare.run(test);

			if (error == 0) {
				System.out.println("Ok");
			}

			//Close writer...
			output.close();

			//and clean-up...
			p = Runtime.getRuntime().exec("rm a.out");
			p = Runtime.getRuntime().exec("rm output.txt");

		}catch (IOException e) {
			System.out.println("Program execution error " + e);
			error = -1;
		}

		return error;
	}

	public static boolean ProcessHadError(Process p) {
		try {
			BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line = null;
			// If there was std_err output, something probably went wrong

			if ((line = stdErr.readLine()) != null) {
				if(Tester.debug == 1) {
					System.out.println("Error: " + line);
					while ((line = stdErr.readLine()) != null) {
						System.out.println(line);
					}
				}
				return true;
			}
		}
		catch (IOException e) {
			System.out.println("IO Exception reading Process StdErr");
		}

		return false;
	}
}
