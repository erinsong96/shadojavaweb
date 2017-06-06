import java.util.*;
import java.util.stream.*;
import Input.loadparam;

/***************************************************************************
 * 
 * 	FILE: 			Simulation.java
 * 
 * 	AUTHOR: 		ROCKY LI
 * 	
 * 	DATA:			2017/6/2
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
	
	/****************************************************************************
	*																			
	*	Main Object:	Simulation 													
	*																			
	*	Purpose:		Create a simulation based on the parameters file.
	*																			
	****************************************************************************/
	
	public Simulation (loadparam param){
		parameters = param;
		taskgen();
	}
	
	/****************************************************************************
	*																			
	*	Method:			taskgen 													
	*																			
	*	Purpose:		Generate a list of task based on time order.
	*																			
	****************************************************************************/
	
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
	
	/****************************************************************************
	*																			
	*	Method:			operatorgen 													
	*																			
	*	Purpose:		Generate an array of operators. 
	*																			
	****************************************************************************/
	
	public void operatorgen(){
		
		// Create Operators
		
		operators = new Operator[parameters.ops.length];
		for (int i = 0; i < parameters.ops.length; i++){
			operators[i] = new Operator(i, parameters);
		}
		
	}
	
	/****************************************************************************
	*																			
	*	Method:			puttask 													
	*																			
	*	Purpose:		putting tasks into the operator with the least queue.
	*																			
	****************************************************************************/
	
	public void puttask(Task task){
		
		ArrayList<Queue> proc = new ArrayList<Queue>();
		for (int i = 0; i < operators.length; i++){
			if (IntStream.of(operators[i].taskType).anyMatch(x -> x == task.getType())){
				proc.add(operators[i].getQueue());
			}
		}
		Collections.sort(proc);

		while (proc.get(0).getfinTime()<task.getArrTime()){
			proc.get(0).done();
		}
		proc.get(0).add(task);
		
	}
	
	/****************************************************************************
	*																			
	*	Main Method:	run													
	*																			
	*	Purpose:		run the simulation based on time order.
	*																			
	****************************************************************************/
	
	public void run(){
		
		taskgen();
		operatorgen();
		for (Task task: tasktime){
			puttask(task);
		}
		
		double totaltime = parameters.numHours * 60;
		for (Operator each: operators){
			while (each.getQueue().getfinTime()<totaltime){
				each.getQueue().done();
			}
		}
		
	}
	
}
