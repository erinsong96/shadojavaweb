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
	
	public double load(){
		
		double worktime = 0;

		if (Dataset != null) {
			for (Task each : Dataset) {
				worktime += each.getEndTime() - each.getBeginTime();
			}
		}
		return worktime;
		
	}
	
	
	
}