import java.util.*;

/***************************************************************************
 * 
 * 	FILE: 			ProcData.java
 * 
 * 	AUTHOR: 		ROCKY LI
 * 	
 * 	DATE:			2017/6/5
 * 
 * 	VER: 			1.0
 * 
 * 	Purpose: 		Analyze the data being outputted.
 * 
 **************************************************************************/

public class ProcData {

	private ArrayList<Task> Dataset;
	
	public ProcData (ArrayList<Task> thisone){
		Dataset = thisone;
	}
	
	public void run(double time){
		trim(time);
		System.out.println(load());
		debug();
	}
	
	public void trim(double time){
		
		ArrayList<Task> newset = new ArrayList<Task>();
		for (Task each: Dataset){
			if (each.getEndTime()<time){
				newset.add(each);
			}
		}
		Dataset = newset;
		
	}
	
	public double load(){
		
		double worktime = 0;

		if (Dataset != null) {
			for (Task each : Dataset) {
				worktime += each.getEndTime() - each.getBeginTime();
			}
		}
		return worktime;
		
	}
	
	public void debug(){
		
		for (Task each : Dataset){
			System.out.println(each.getBeginTime() + " " + each.getSerTime() + " " + each.getEndTime() + " " + 
		each.getType());
		}
			
	}
	
	
}