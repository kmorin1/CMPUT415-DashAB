import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class TestFolderizer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File testdir = new File("Tests");
		File[] tests = testdir.listFiles();
		for (int i=0; i<tests.length-1; i++) {
			//System.out.println(testfile.getName());
			if (tests[i].getName().endsWith(".ds") || tests[i].getName().endsWith(".in")) {
				File newdir = new File(testdir.getName() + "\\" + tests[i].getName().substring(0, tests[i].getName().length()-3));
				//System.out.println(newdir.getName());
				if (!newdir.exists()) {
					newdir.mkdir();
				} 
				
				File dest = new File(testdir.getName() + "\\" + newdir.getName() + "\\" + tests[i].getName());
				System.out.println(dest);
				FileReader freader = new FileReader(tests[i]);
				BufferedReader reader = new BufferedReader(freader);
				FileWriter fwriter = new FileWriter(dest);
				BufferedWriter writer = new BufferedWriter(fwriter);
				String line;
				while ((line = reader.readLine()) != null)
					writer.write(line + "\r\n");
				writer.close();
				reader.close();
				//tests[i].delete();
			}
			
		}
	}

}
