package automated_test;

import java.io.*;

public class Test_lli {

	public static int run(String test){
		int error = 0;

		System.out.println(test + " with LLC");
		String line = null;
		try{
			//Initialize writer
			PrintWriter writer = new PrintWriter("test.llvm", "UTF-8");
			
			//Write llvm-ir to file
			Process p = Runtime.getRuntime().exec(Tester.our + " Tests/" + test);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdInput.readLine()) != null) {
				//System.out.println(line);
				writer.println(line);
			}

			//See if the llvm works or not...
			p = Runtime.getRuntime().exec(Tester.llc + " test.llvm -filetype=obj");
			p = Runtime.getRuntime().exec("clang test.llvm.o libruntime.a -lm");
			p = Runtime.getRuntime().exec("./a.out");
			//Detect for errors...
			
			// Do we need to? If it compiles into ./a.out we are good?
			// error = -1;
			
			//Close writer...
			writer.close();
			
			//and clean-up...
			p = Runtime.getRuntime().exec("rm test.llvm");
			p = Runtime.getRuntime().exec("rm test.llvm.o");
			p = Runtime.getRuntime().exec("rm a.out");
			
		}catch (IOException e) {
			System.out.println("IO Error");
			error = -1;
		}

		return error;
	}
}
