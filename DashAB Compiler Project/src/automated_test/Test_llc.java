package automated_test;

import java.io.*;

public class Test_llc {

	public static int run(String test, long pid){
		int error = 0;
		int valid_test = 1;
		System.out.println("\n"+test + " with LLC");
		String line = null;
		try{
			//Filenames
			String testllvm = "test"+ pid +".llvm";
			String outputtxt = "output"+ pid +".txt";
			String execname = pid+".out";
			
			//Initialize writer
			PrintWriter writer = new PrintWriter(testllvm, "UTF-8");
			PrintWriter output = new PrintWriter(outputtxt, "UTF-8");
			
			//Write llvm-ir to file
			if(Tester.debug == 1)
				System.out.println("***OUR COMPILER COMMAND EXECUTED >>> " + Tester.our + " Tests/" + test);

			Process p = Runtime.getRuntime().exec(Tester.our + " Tests/" + test);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			if (ProcessHadError(p)) {
				valid_test = 0;
				output.println("//fail");
				output.flush();
			}

			while ((line = stdInput.readLine()) != null && valid_test == 1) {
				if(line.indexOf("A problem has occured with the dash input file:") != -1){
					stdInput.readLine();
					valid_test = 0;
					output.println("//fail");
					output.flush();
				}
				else{
					writer.println(line);
					writer.flush();
				}
			}

			//Take a look at the files before they are used (Debug purposes only)
			if(Tester.debug == 1) {
				System.out.println("***Showing "+testllvm);
				BufferedReader reader = new BufferedReader(new FileReader(testllvm));
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			}

			if (valid_test == 1) {
				//See if the llvm works or not...
				p = Runtime.getRuntime().exec(Tester.llc + " "+testllvm+" -filetype=obj");			
				boolean hadError = ProcessHadError(p);
				if(Tester.debug == 1) {
					System.out.println("***LLC likes it");
				}
				//Now we link it into an executable
				p = Runtime.getRuntime().exec("clang "+ testllvm +".o libruntime.a -lm -o"+execname);
				hadError |= ProcessHadError(p);
				if(Tester.debug == 1) {
					System.out.println("***Linking OK");
				}

				if (!hadError) {
					output.println("//pass");
				}
				else {
					output.println("//fail");
				}
				output.flush();


				//Now run it

				if(Tester.debug == 1 && hadError) {
					System.out.println("***Had error creating executable");
				}


				if (!hadError) {
					p = Runtime.getRuntime().exec("./"+execname);

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
				System.out.println("***Showing "+outputtxt);
				BufferedReader reader = new BufferedReader(new FileReader(outputtxt));
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
				System.out.println("***About to diff");
			}

			if(Tester.llc_compare_test == 1)
				error = Test_compare.run(test,outputtxt);

			if (error == 0 && Tester.multithread == 1) {
				System.out.println("Ok");
			}

			//Close writer...
			writer.close();
			output.close();

			//and clean-up...
			p = Runtime.getRuntime().exec("rm "+testllvm);
			p = Runtime.getRuntime().exec("rm "+testllvm+".o");
			p = Runtime.getRuntime().exec("rm "+execname);
			p = Runtime.getRuntime().exec("rm "+outputtxt);

		}catch (IOException e) {
			System.out.println("Invalid LLVM code " + e);
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
