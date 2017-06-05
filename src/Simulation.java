import java.util.*;
import java.util.stream.*;
import Input.loadparam;

/***************************************************************************
 * 
 * 	FILE: 			Simulation.java
 * 
 * 	AUTHOR: 		ROCKY LI
 * 	
 * 	LATEST EDIT:	2017/6/2
 * 
 * 	VER: 			1.0
 * 
 * 	Purpose: 		Create and manage the entire simulation and the objects inside.
 * 
 **************************************************************************/


public class Simulation {
	
	public loadparam parameters;
	public Operator[] operators;
	public ArrayList<Task> tasktime;
	
	public Simulation (loadparam param){
		parameters = param;
		taskgen();
	}
	
	public void taskgen(){
		
		tasktime = new ArrayList<Task>();
		
		for (int i = 0; i < parameters.numTaskTypes; i++){
			ArrayList<Task> indlist = new ArrayList<Task>();
			Task origin = new Task(i, 0, parameters);
			indlist.add(origin);
			while (origin.getArrTime() < parameters.numHours*60){
				origin = new Task(i, origin.getArrTime(), parameters);
				indlist.add(origin);
			}
			tasktime.addAll(indlist);
		}
		
		Collections.sort(tasktime, (o1, o2) -> Double.compare(o1.getArrTime(), o2.getArrTime()));
		
	}
	
	public void operatorgen(){
		
		// Create Operators
		
		operators = new Operator[parameters.ops.length];
		for (int i = 0; i < parameters.ops.length; i++){
			operators[i] = new Operator(i, parameters);
		}
		
		// Put tasklist into queue.
		
		for (int i = 0; i< operators.length ; i++){
			Queue his = operators[i].getQueue();
		}
		
	}
	
	public void puttask(Task task){
		
		ArrayList<Queue> proc = new ArrayList<Queue>();
		for (int i = 0; i< operators.length; i++){
			if (IntStream.of(operators[i].taskType).anyMatch(x -> x == task.getType())){
				proc.add(operators[i].getQueue());
			}
		}
		Collections.sort(proc);

		while (proc.get(0).finTime()<task.getArrTime()){
			proc.get(0).done();
		}
		proc.get(0).add(task);
		
	}
	
	public void run(){
		
		taskgen();
		operatorgen();
		for (Task task: tasktime){
			puttask(task);
		}
	}
	
}
