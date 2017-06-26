package Output;
import java.util.*;

import Engine.Operator;
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

	public void run(double time, Operator you) {
		trim(time);
		//System.out.println(load());
		output(you);
		debug();
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

	public void output(Operator who) {


		// calling so that the utilization data is being averaged across trains
		who.getUtilization().avgdata();
		// was in the process of debugging
		for (double[][] x : who.getUtilization().data) {
			for (double[] y : x) {
				for (double z : y) {

					System.out.print(z + ",");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public void debug() {
		
		for (Task each : Dataset){
			System.out.println(each.getBeginTime() + " " + each.getELSTime() + " " + each.getEndTime()
					+ " " + each.getName() + " and " + each.getQueued() + " are in the queue. " + each.getSerTime());
		}

	}
	
	
}