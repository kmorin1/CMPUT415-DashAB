//Tested to work
package automated_test;

import java.io.*;
import java.util.*;


public class Test_loader {

	public static void load(){

		System.out.print("Getting list of tests... ");

		//List of tests to do
		List <String> tests = new ArrayList<String>();

		//Test result list
		List <Integer> llc_result = new ArrayList<Integer>();
		List <Integer> dash_result = new ArrayList<Integer>();

		//Error counters for statistics
		int llc_err = 0;
		int dash_err = 0;

		String line = null;

		//Get list of tests
		try{
			if(Tester.specific_test == 1){
				tests.add(Tester.specific);
			}else{
				Process p = Runtime.getRuntime().exec("ls Tests");
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = stdInput.readLine()) != null) {
					if(line.indexOf(".ds") != -1)
						tests.add(line);
				}
			}
			System.out.println("Done, " + tests.size() + " tests loaded\n");
		}catch (IOException e) {
			System.out.println("Error starting process");
		}

		//Do the tests one by one...
		for(int i = 0; i < tests.size(); i++){
			if(Tester.llc_test == 1)
				llc_result.add(Test_llc.run(tests.get(i)));
			if(Tester.dash_test == 1)
				dash_result.add(Test_dash.run(tests.get(i)));
		}


		//Show warnings
		System.out.println("\n### Summary ###");

		if(Tester.llc_test == 1){
			System.out.println("\nThe following tests does not work with llc: ");
			for(int i = 0; i < tests.size(); i++){
				if(llc_result.get(i) == -1 && Tester.llc_test == 1){
					System.out.println(" - " + tests.get(i));
					llc_err++;
				}
			}
		}
		if(Tester.llc_test == 1){
			System.out.println("\nThe following tests works with llc: ");
			for(int i = 0; i < tests.size(); i++){
				if(llc_result.get(i) == 0 && Tester.llc_test == 1){
					System.out.println(" - " + tests.get(i));
				}
			}
		}
		if(Tester.dash_test == 1){
			System.out.println("\nThe following test does not work with dash:");
			for(int i = 0; i < tests.size(); i++){
				if(dash_result.get(i) == -1 && Tester.dash_test == 1){
					System.out.println(" - " + tests.get(i));
					dash_err++;
				}
			}
		}
		if(Tester.dash_test == 1){
			System.out.println("\nThe following test works with dash:");
			for(int i = 0; i < tests.size(); i++){
				if(dash_result.get(i) == 0 && Tester.dash_test == 1){
					System.out.println(" - " + tests.get(i));
					dash_err++;
				}
			}
		}
		//Print Score
		System.out.println("\n### Score ###");
		int passed = tests.size() - llc_err;
		if(Tester.llc_test == 1)
			System.out.println("LLC tests passed " + passed + " out of " + tests.size());
		if(Tester.dash_test == 1)
			System.out.println(dash_err + " of " + tests.size() + " didn't work with dash");
	}
}
