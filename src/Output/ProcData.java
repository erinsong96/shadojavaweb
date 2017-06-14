package Engine;
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
		//debug();
		System.out.println(initial()[8]);
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
				worktime += each.getELSTime();
			}
		}
		return worktime;
		
	}
	
	public void debug(){
		
		for (Task each : Dataset){
			System.out.println(each.getBeginTime() + " " + each.getELSTime() + " " + each.getEndTime()
			+ " " + each.getName() + " and " + each.getQueued() + " are in the queue. " + each.getExpTime());
		}
			
	}

	public double[] initial() {

		double[] utilization = new double[9];
		for (Task each : Dataset) {
			if (each.getBeginTime() <= 10) {
				utilization[each.getType()] = each.getELSTime();

			}
		}

		return utilization;
	}
	
	
}