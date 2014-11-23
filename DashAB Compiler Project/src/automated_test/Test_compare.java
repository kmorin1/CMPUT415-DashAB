package automated_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test_compare {
//work on this later
	public static int run(String test){
		System.out.println("Comparing output for " + test);
		String line = null;
		try{
			Process p = Runtime.getRuntime().exec("ls Tests");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdInput.readLine()) != null) {
				//do something here
			}
		}catch (IOException e) {
			System.out.println("Error starting process");
		}
		return 0;
	}
}
