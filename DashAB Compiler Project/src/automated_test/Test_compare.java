package automated_test;

import java.io.*;

public class Test_compare {
	
	public static int run(String test){
		
		int error = 0;
		String line = null;
		
		try {
			Process p = Runtime.getRuntime().exec("diff output.txt Tests/" + test + ".exp");
			System.out.println("diff output.txt Tests/" + test + ".exp");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdInput.readLine()) != null) {
				System.out.println(line);
				error = -1;
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return error;
	}
	
}
