package automated_test;

import java.io.*;

public class Test_llc {

	public static int run(String test){
		int error = 0;
		System.out.println("\n"+test + " with LLC");
		String line = null;
		try{
			//Initialize writer
			PrintWriter writer = new PrintWriter("test.llvm", "UTF-8");
			PrintWriter output = new PrintWriter("output.txt", "UTF-8");

			//Write llvm-ir to file
			if(Tester.debug == 1)
				System.out.println("***LLC COMMAND EXECUTED >>> " + Tester.our + " Tests/" + test);

			Process p = Runtime.getRuntime().exec(Tester.our + " Tests/" + test);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdInput.readLine()) != null) {
				if(line.indexOf("A problem has occured with the dash input file:") != -1){
					stdInput.readLine();
					output.println("//fail");
					output.flush();
				}
				else{
					writer.println(line);
					writer.flush();
				}
			}

			//Take a look at the files before they are used (Debug purposes only)
			if(Tester.debug == 1){
				System.out.println("***Showing test.llvm");
				BufferedReader reader = new BufferedReader(new FileReader("test.llvm"));
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
				System.out.println("***Showing output.txt");
				reader = new BufferedReader(new FileReader("output.txt"));
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			}

			//See if the llvm works or not...
			p = Runtime.getRuntime().exec(Tester.llc + " test.llvm -filetype=obj");
			//Now we link it into an executable
			p = Runtime.getRuntime().exec("clang test.llvm.o libruntime.a -lm");
			output.println("//pass");
			output.flush();
			//Now run it
			p = Runtime.getRuntime().exec("./a.out");
			System.out.println("executable generated");
			//Output results into a file
			stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdInput.readLine()) != null) {
				if(Tester.debug == 1)
					System.out.println(line);
				output.println(line);
			}
			
			if(Tester.llc_compare_test == 1)
				error = Test_compare.run(test);

			//Close writer...
			writer.close();
			output.close();

			//and clean-up...
			p = Runtime.getRuntime().exec("rm test.llvm");
			p = Runtime.getRuntime().exec("rm test.llvm.o");
			p = Runtime.getRuntime().exec("rm a.out");
			p = Runtime.getRuntime().exec("rm output.txt");

		}catch (IOException e) {
			System.out.println("Invalid LLVM code" + e);
			error = -1;
		}

		return error;
	}
}
