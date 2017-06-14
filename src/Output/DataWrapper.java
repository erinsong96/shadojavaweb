package Output;
import java.util.ArrayList;

import Engine.Dispatch;
import Engine.Operator;
import Engine.Simulation;
import Engine.TrainSim;

/***************************************************************************
 * 
 * 	FILE: 			DataWrapper.java
 * 
 * 	AUTHOR: 		ROCKY LI
 * 	
 * 	DATE:			2017/6/5
 * 
 * 	VER: 			1.0
 * 
 * 	Purpose: 		Wrapping the data field for analysis.
 * 
 **************************************************************************/

public class DataWrapper {
	
	private Simulation once;
	
	private Dispatch here;
	
	private TrainSim[] there;
	
	public DataWrapper(Simulation o){
		once = o;
	}
	
	public void read(){
		
		here = once.getDispatch();
		there = once.getTrains();
		
	}
	
	public void generate(){
		
		System.out.println("here are the Dispatchers");
		Operator[] dispatchers = here.getDispatch();
		for (Operator each: dispatchers){
			new ProcData(each.getQueue().records()).run(once.getTime());
		}
		
		for (TrainSim each: there){
			
			System.out.println("for train " + each.trainID);
			Operator[] operators = each.operators;
			for (Operator him: operators){
				new ProcData(him.getQueue().records()).run(once.getTime());
			}
		}
		
	}
	
	public void generatecsv(){
		
	}

}
