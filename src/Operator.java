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
 * 	Purpose: 		generate task objects.
 * 
 **************************************************************************/

public class Operator {

	public int opId;
	public String name;
	public double time;
	public int[] taskType;
	private Queue myQueue;
	private loadparam parameters;
	
	// Mutator
	
	public void update(double Time){
		
	}
		
	/****************************************************************************
	*																			
	*	Main Object:	Operator															
	*																			
	*	Purpose:		This is a human object.
	*																			
	****************************************************************************/
		
	public Operator (int opid, loadparam param){
		parameters = param;
		opId = opid;
		taskType = parameters.opTasks[opid];
		name = parameters.opNames[opid];
		myQueue = new Queue(opId);
	}
	
	
}
