//Tested to work
package automated_test;

import java.io.*;

public class Test_compare {

	public static int run(String test, String outputtxt){

		//Read filename
		String[] filename = test.split("\\.");
		test = filename[0];

		int error = 0;
		String line = null;

		try {
			if(Tester.debug == 1)
				System.out.println("diff "+outputtxt+" Tests/" + test + ".exp");
			//Send output.txt and expected output through diff
			if(Tester.windows == 0){//we are Linux
				Process p = Runtime.getRuntime().exec("diff "+outputtxt+" Tests/" + test + ".exp");
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = stdInput.readLine()) != null) {
					//If we execute this part it means that diff found some differences
					System.out.println(line);
					error = -1;
				}
			}else{//we are Windows
				Process p = Runtime.getRuntime().exec("fc "+outputtxt+" Tests/" + test + ".exp");
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = stdInput.readLine()) != null) {
					//If we execute this part it means that diff found some differences
					System.out.println(line);
					error = -1;
				}
			}
			if(error == -1){
				System.out.println("Differences has been found in: "+test);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return error;
	}

}