import java.util.*;
import Input.loadparam;

/***************************************************************************
 * 
 * 	FILE: 			Task.java
 * 
 * 	AUTHOR:			ROCKY LI
 * 	
 * 	DATE:			2017/6/12
 * 
 * 	VER: 			1.0
 * 
 * 	Purpose: 		Create simulation for multiple train and dispatch
 * 
 **************************************************************************/

public class Dispatch {
	
	public loadparam parameters;
	
	public TrainSim[] trains;
	
	private ArrayList<Task> linkedtasks;
	
	public Dispatch(loadparam Param){
		parameters = Param;
	}
	
	public void linkedgen(){
		
		for (int j = 0; j < parameters.numTrains ; j++){
			
			linkedtasks = new ArrayList<Task>();
			
			linkedtasks = new ArrayList<Task>();
			
			// For each type of tasks:
			
			for (int i = 0; i < parameters.numTaskTypes; i++){
				
				// Create a new empty list of Tasks
				
				ArrayList<Task> indlist = new ArrayList<Task>();
				
				// Start a new task with PrevTime = 0
				
				Task origin = new Task(i, 0, parameters);
				
				if (origin.linked()){
					continue;
				}
				
				origin.setID(j);
				indlist.add(origin);
				
				// While the next task is within the time frame, generate.
				
				while (origin.getArrTime() < parameters.numHours*60){
					origin = new Task(i, origin.getArrTime(), parameters);
					origin.setID(j);
					indlist.add(origin);
				}
				
				// Put all task into the master tasklist.
				
				linkedtasks.addAll(indlist);
			}
		}
	}

}
