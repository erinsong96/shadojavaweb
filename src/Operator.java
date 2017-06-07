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
 * 	Purpose: 		generate operator that wraps Queue objects. This class is 
 * 					mainly meant for understanding purposes - Queue objects represent
 * 					operators quite well - we might be able to get rid of this class
 * 					in the future.
 * 
 **************************************************************************/

public class Operator {

	public int opId;
	public String name;
	public int[] taskType;
	
	private Queue myQueue;
	private loadparam parameters;
	
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
		
		// Next line generates an empty queue.
		myQueue = new Queue(opId);
		
	}
}
