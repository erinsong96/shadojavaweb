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


	private Data utilization;
	//private Data expiredtask;
	//private Data taskin;
	//private Data taskout;

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

	public Data getUtilization() {
		return this.utilization;
	}

	/*public Data getExpiredtask() {
		return this.expiredtask;
	}

	public Data getTaskin() {
		return this.taskin;
	}

	public Data getTaskout() {
		return this.taskout;
	}/*

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

		utilization = new Data(parameters.numTaskTypes, i, param.numTrains);
		//expiredtask = new Data(parameters.numTaskTypes, i + 1, param.numTrains);
		//taskin = new Data(parameters.numTaskTypes, i + 1, param.numTrains);
		//taskout = new Data(parameters.numTaskTypes, i + 1, param.numTrains);
	}

	/****************************************************************************
	 *
	 *	Main Object:	Dispatcher
	 *
	 *	Purpose:		Generate a Dispatcher from the parameter file imported
	 *
	 ****************************************************************************/

	public Operator(int dpid, int[] task, double numHours, int numTrains, int tasktype) {


		taskType = task;
		name = "Dispatcher " + Integer.toString(dpid);
		myQueue = new Queue();

		int i = (int) numHours * 6;

		utilization = new Data(tasktype, i, numTrains);
		//expiredtask = new Data(taskType.length, i + 1, numTrains);
		//taskin = new Data(taskType.length, i + 1, numTrains);
		//taskout = new Data(taskType.length, i + 1, numTrains);
	}
}
