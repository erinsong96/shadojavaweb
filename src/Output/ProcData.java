package Output;
import java.util.*;

import Engine.Task;

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
		//System.out.println(load());
		timeframe();
		//debug();
	}

	public void trim(double time) {

		ArrayList<Task> newset = new ArrayList<Task>();
		for (Task each : Dataset) {
			if (each.getEndTime() < time) {
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

	public void timeframe() {

		double[][] tasktime = new double[48][9];

		for (int i = 1; i < 49; i++) {
			for (Task each : Dataset) {
                //System.out.println(each.getType());
                if (each.getBeginTime() <= 10 * i) {
                    if (10 * (i - 1) <= each.getEndTime() && each.getEndTime() <= 10 * i) {
                        if (each.getBeginTime() <= 10 * (i - 1)) {
							tasktime[i - 1][each.getType()] += each.getEndTime() - (10 * (i - 1));
						}
                        tasktime[i - 1][each.getType()] += each.getELSTime();
                        //System.out.println(tasktime[i-1][each.getType()]);

					} else {
						tasktime[i - 1][each.getType()] += (10 * i) - each.getBeginTime();

					}
				} else {
                    i += 1;

				}


			}
		}


		for (double[] x : tasktime) {
			for (double y : x) {
				System.out.print(y + ",");
			}
			System.out.println();
		}
	}
	
	/*public void debug(){
		
		for (Task each : Dataset){
			System.out.println(each.getBeginTime() + " " + each.getELSTime() + " " + each.getEndTime()
			+ " " + each.getName() + " and " + each.getQueued() + " are in the queue. " + each.getExpTime());
		}
			
	}*/
	
	
}