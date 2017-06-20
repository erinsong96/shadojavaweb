package Engine;
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


	private double[][] taskarray;
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

	public double[][] getTaskarray() {
		return this.taskarray;
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
		myQueue = new Queue();
		int i = (int) parameters.numHours * 6;

		taskarray = new double[i][parameters.numTaskTypes];
	}

	/****************************************************************************
	 *
	 *	Main Object:	Dispatcher
	 *
	 *	Purpose:		Generate a Dispatcher from the parameter file imported
	 *
	 ****************************************************************************/

	public Operator(int dpid, int[] tasks, double hours, int tasktype) {

		taskType = tasks;
		name = "Dispatcher " + Integer.toString(dpid);
		myQueue = new Queue();

		int i = (int) hours * 6;

		taskarray = new Double[i][tasktype];
	}
}
