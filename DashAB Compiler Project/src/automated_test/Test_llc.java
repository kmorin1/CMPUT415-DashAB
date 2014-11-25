package automated_test;

import java.io.*;

public class Test_llc {

	public static int run(String test){
		int error = 0;
		Test_compare.run(test);
		System.out.println("\n"+test + " with LLC");
		String line = null;
		try{
			//Initialize writer
			PrintWriter writer = new PrintWriter("test.llvm", "UTF-8");
			PrintWriter output = new PrintWriter("output.txt", "UTF-8");

			//Write llvm-ir to file
			if(Tester.debug == 1)	//Debug Code
				System.out.println("LLC COMMAND EXECUTED >>> " + Tester.our + " Tests/" + test);

			Process p = Runtime.getRuntime().exec(Tester.our + " Tests/" + test);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdInput.readLine()) != null) {
				if(line.indexOf("A problem has occured with the dash input file:") != -1){
					stdInput.readLine();
					output.println("//fail");
				}
				else
					writer.println(line);
				if(Tester.debug == 1)
					System.out.println(line);
			}
			
			//See if the llvm works or not...
			p = Runtime.getRuntime().exec(Tester.llc + " test.llvm -filetype=obj");
			//Now we link it into an executable
			p = Runtime.getRuntime().exec("clang test.llvm.o libruntime.a -lm");
			output.println("//pass");
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
			System.out.println("Invalid LLVM code");
			error = -1;
		}

		return error;
	}
}
