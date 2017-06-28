package Output;

import java.lang.reflect.Parameter;
import java.util.*;

import Engine.Data;
import Engine.Operator;
import Engine.Task;
import Input.loadparam;

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

	public void run(double time, Operator you, int trainID) {
		trim(time);
		//System.out.println(load());
		outpututilization(you, time, trainID);
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

	public void outpututilization(Operator who, double time, int trainID) {

        int i = 1;


			for (Task each : Dataset) {

                if (i > (int) (time / 10) + 1) {
                    break;
                }

				double beginscale = each.getBeginTime() / 10;
				double endscale = each.getEndTime() / 10;
                int endINT = (int) endscale + 1;

				double percBusy = 0;


				if (i > endscale) {

						percBusy = each.getSerTime() / 10;

						who.getUtilization().datainc(each.getType(), i - 1, trainID, percBusy);


				} else {

                    if (i > beginscale) {
                        percBusy = i - beginscale;
                        who.getUtilization().datainc(each.getType(), i - 1, trainID, percBusy);
						percBusy = endscale - i;
						who.getUtilization().datainc(each.getType(), i, trainID, percBusy);

                    }

                    i = endINT;

					percBusy = each.getSerTime() / 10;

					who.getUtilization().datainc(each.getType(), i - 1, trainID, percBusy);


				}
			}


		who.getUtilization().avgdata();
		// was in the process of debugging
		for (double[] x : who.getUtilization().avg) {
			for (double y : x) {

				System.out.print(y + ",");

			}
			System.out.println();
		}

	}



	public void debug() {
		
		for (Task each : Dataset){
			System.out.println(each.getBeginTime() + " " + each.getELSTime() + " " + each.getEndTime()
					+ " " + each.getName() + " and " + each.getQueued() + " are in the queue. " + each.getSerTime());
		}

	}
	
	
}