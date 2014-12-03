package automated_test;

public class Test_thread extends Thread{
	
	private int start, end;
	
	public Test_thread(int s, int e){
		this.start = s;
		this.end = e;
	}
	
	@Override
	public void run(){
		System.out.println("This is thread - " + this.getId() + " processing range " + this.start + "-" + this.end);
		
		for(int i = start; i < end; i++){
			if(Tester.llc_test == 1)
				Test_loader.llc_result.add(Test_llc.run(Test_loader.tests.get(i),this.getId()));
			if(Tester.dash_test == 1)
				Test_loader.dash_result.add(Test_dash.run(Test_loader.tests.get(i),this.getId()));
			try {
				Thread.sleep(100); //Artificial lag
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
