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
		timeframe(you);
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

	public void timeframe(Operator who) {

		for (int i = 1; i < who.getTaskarray().length + 1; i++) {
			for (Task each : Dataset) {
                //System.out.println(each.getType());
                if (each.getBeginTime() <= 10 * i) {
                    if (10 * (i - 1) <= each.getEndTime() && each.getEndTime() <= 10 * i) {
                        if (each.getBeginTime() <= 10 * (i - 1)) {
							who.getTaskarray()[i - 1][each.getType()] += (each.getEndTime() - (10 * (i - 1)));
						}

						who.getTaskarray()[i - 1][each.getType()] += each.getELSTime();


					} else {


						who.getTaskarray()[i - 1][each.getType()] += ((10 * i) - each.getBeginTime());

					}
				} else {
                    i += 1;

				}


			}
		}


		for (double[] x : who.getTaskarray()) {
			for (double y : x) {
				System.out.print(y + ",");
			}
			System.out.println();
		}
	}

	public void debug() {
		
		for (Task each : Dataset){
			System.out.println(each.getBeginTime() + " " + each.getELSTime() + " " + each.getEndTime()
			+ " " + each.getName() + " and " + each.getQueued() + " are in the queue. " + each.getExpTime());
		}

	}
	
	
}