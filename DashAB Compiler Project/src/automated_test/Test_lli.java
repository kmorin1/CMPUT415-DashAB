package automated_test;

import java.io.*;

public class Test_lli {

	public static int run(String test){
		System.out.println("Putting it through lli for " + test);
		String line = null;
		try{
			Process p = Runtime.getRuntime().exec("ls");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdInput.readLine()) != null) {
				System.out.println(line);
			}
			
		}catch (IOException e) {
			System.out.println("Error starting process");
		}
		return 0;
	}
}
