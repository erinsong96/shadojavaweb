package Engine;
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
	
	private ArrayList<Task> dispatchrecords;
	
	private Operator[] dispatchers;
	
	private int[] linked;
	
	private ArrayList<Task> proctasks;
	
	public Dispatch(loadparam Param){
		parameters = Param;
	}
	
	// Inspectors:
	
	public ArrayList<Task> gettasks(){
		return proctasks;
	}
	
	public Operator[] getDispatch(){
		return dispatchers;
	}
	
	public void linkedgen(){
		
		// Creates a new task arraylist of the tasks that are linked
		
		ArrayList<Integer> linkedt = new ArrayList<Integer>();

		linkedtasks = new ArrayList<Task>();
		
		// For each train:
		
		for (int j = 0; j < parameters.numTrains ; j++){
			
			// For each type of tasks:
			
			for (int i = 0; i < parameters.numTaskTypes; i++){
				
				// Create a new empty list of Tasks
				
				ArrayList<Task> indlist = new ArrayList<Task>();
				
				// Start a new task with PrevTime = 0
				
				Task origin = new Task(i, 0, parameters);
				
				if (!origin.linked()){
					continue;
				}
				
				linkedt.add(i);
				
				// Set train ID.
				
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
		
		linked = linkedt.stream().mapToInt(Integer::intValue).toArray();
	}
	
	public void genDispatch(){
		
		dispatchers = new Operator[parameters.numDispatch];
		
		for (int i = 0; i < parameters.numDispatch; i++){
			
			dispatchers[i] = new Operator(i, linked);
			
		}
		
	}
	
	public void runDispatch(){
		
		TrainSim DispatchSim = new TrainSim(parameters, dispatchers, linkedtasks);
		DispatchSim.run();
		proctasks = new ArrayList<Task>();
		for (Operator dispatcher: DispatchSim.operators){
			proctasks.addAll(dispatcher.getQueue().records());
		}
		
	}
	
	public void run(){
		
		linkedgen();
		genDispatch();
		runDispatch();
		
	}
}
