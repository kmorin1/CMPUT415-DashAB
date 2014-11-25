//Needs more testing to confirm
package automated_test;

import java.io.*;

public class Test_dash {

	public static int run (String test){
		int error = 0;

		//Do your stuff here
		System.out.println(test + " with DASH");
		String line = null;

		//See if the dash code works or not
		try{
			
			if(Tester.debug == 1)	//Debug code
				System.out.println("DASH COMMAND EXECUTED >>> " + Tester.dash + " Tests/" + test);
			
			Process p = Runtime.getRuntime().exec(Tester.dash + " Tests/" + test);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdInput.readLine()) != null) {
				System.out.println(line);
				error = -1;
			}
			//See if it runs or not
			p = Runtime.getRuntime().exec("./a.out");

			//Detect for errors...

			// Do we need to? If it compiles into ./a.out we are good?
			// error = -1;

			//and clean-up...
			p = Runtime.getRuntime().exec("rm a.out");

		}catch (IOException e) {
			System.out.println("IO Error");
			error = -1;
		}

		return error;
	}
}
