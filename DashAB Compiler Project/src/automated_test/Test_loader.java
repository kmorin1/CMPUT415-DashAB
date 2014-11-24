package automated_test;

import java.io.*;
import java.util.*;


public class Test_loader {

	public static void load(){

		System.out.print("Getting list of tests... ");
		
		//List of tests to do
		List <String> tests = new ArrayList();
		
		//Test result list
		List <Integer> compare_result = new ArrayList();
		List <Integer> lli_result = new ArrayList();
		List <Integer> dash_result = new ArrayList();
		
		//Error counters for statistics
		int compare_err = 0;
		int lli_err = 0;
		int dash_err = 0;
		
		String line = null;
		
		//Get list of tests
		try{
			Process p = Runtime.getRuntime().exec("ls Tests");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdInput.readLine()) != null) {
				if(line.indexOf(".ds") != -1)
					tests.add(line);
			}
			System.out.println("Done, " + tests.size() + " tests loaded\n");
		}catch (IOException e) {
			System.out.println("Error starting process");
		}

		//Do the tests one by one...
		for(int i = 0; i < tests.size(); i++){
			//compare_result.add(Test_compare.run(tests.get(i)));
			lli_result.add(Test_lli.run(tests.get(i)));
			dash_result.add(Test_dash.run(tests.get(i)));
		}


		//Show warnings
		System.out.println("\n### Summary ###");
		System.out.println("### WARNING LLC TESTING MIGHT BE VERY BROKEN AT THIS POINT ###");		//Delete me when fixed
		/*
		System.out.println("The following tests does not match DASH: ");
		for(int i = 0; i < tests.size(); i++){
			if(compare_result.get(i) == -1){
				System.out.println(tests.get(i));
				compare_err++;
			}
		}
		*/
		System.out.println("\nThe following tests does not work with lli: ");
		for(int i = 0; i < tests.size(); i++){
			if(lli_result.get(i) == -1){
				System.out.println(" - " + tests.get(i));
				lli_err++;
			}
		}
		
		System.out.println("\nThe following test does not work with dash:");
		for(int i = 0; i < tests.size(); i++){
			if(dash_result.get(i) == -1){
				System.out.println(" - " + tests.get(i));
				dash_err++;
			}
		}
		
		//Print Statistics
		System.out.println("\n### Score ###");
		//System.out.println(compare_err + " of " + tests.size() + " didn't match with DASH");
		System.out.println(lli_err + " of " + tests.size() + " didn't work with lli");
		System.out.println(dash_err + " of " + tests.size() + " didn't work with dash");
	}
}
