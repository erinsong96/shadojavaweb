import java.util.*;
import Input.loadparam;

/***************************************************************************
 * 
 * 	FILE: 			Operator.java
 * 
 * 	AUTHOR: 		ROCKY LI
 * 	
 * 	LATEST EDIT:	2017/5/24
 * 
 * 	VER: 			1.0
 * 
 * 	Purpose: 		generate operator that wraps Queue objects. This is where
 * 					the distinction between an operator and a dispatcher is made.
 * 
 **************************************************************************/

public class Operator {

	public int opId;
	public String name;
	public int[] taskType;
	
	private Queue myQueue;
	private loadparam parameters;
	private boolean isDispatcher;
	
	// Inspector
	
	public boolean isBusy(){
		return this.myQueue.getStatus();
	}
	
	public Queue getQueue(){
		return this.myQueue;
	}
	
	// Mutator
		
	/****************************************************************************
	*																			
	*	Main Object:	Operator															
	*																			
	*	Purpose:		Generate an operator object from the parameter file imported
	*																			
	****************************************************************************/
		
	public Operator (int opid, loadparam param){
		
		parameters = param;
		opId = opid;
		taskType = parameters.opTasks[opid];
		name = parameters.opNames[opid];
		if (name.equals("Dispatcher")){
			isDispatcher = true;
		} else { isDispatcher = false;}
		
		// Next line generates an empty queue.
		myQueue = new Queue(opId);
		
	}
}
