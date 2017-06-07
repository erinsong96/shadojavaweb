import java.util.*;

/***************************************************************************
 * 
 * 	FILE: 			Queue.java
 * 
 * 	AUTHOR: 		ROCKY LI
 * 	
 * 	LATEST EDIT:	2017/5/24
 * 
 * 	VER: 			1.0
 * 
 * 	Purpose: 		Queue up each of the workers.
 * 
 **************************************************************************/

public class Queue implements Comparable<Queue>{
	
	public PriorityQueue<Task> taskqueue;
	public int opId;
	
	// Set the time to move forward with general time.
	
	private double time;
	
	// See if the Queue is populated or not
	
	public boolean isBusy;
	
	// Service time to complete the current task in queue
	
	private double finTime;
	
	// Number of tasks in the queue
	
	private int NumTask;
	
	// Record all done tasks for data analysis
	
	private ArrayList<Task> recordtasks = new ArrayList<>();
	
	// inspectors:
	
	public ArrayList<Task> records(){
		return recordtasks;
	}
	
	public double getfinTime(){
		return finTime;
	}
	
	public int getNumTask(){
		return NumTask;
	}
	
	// Mutator:
	
	public void SetTime(double Time){
		this.time = Time;
	}
	
	@Override
	public int compareTo(Queue other){
		return this.NumTask - other.NumTask;
	}
	
	/****************************************************************************
	*																			
	*	Main Object:	Queue													
	*																			
	*	Purpose:		Create an empty queue at the start
	*																			
	****************************************************************************/
	
	public Queue(int opid){
		opId = opid;
		taskqueue = new PriorityQueue<Task>();
		time = 0;
        finTime = Double.POSITIVE_INFINITY;
	}
	
	/****************************************************************************
	*																			
	*	Method:			Add													
	*																			
	*	Purpose:		add a task to queue.
	*																			
	****************************************************************************/
	
	public void add(Task task){
		SetTime(task.getArrTime());
		taskqueue.add(task);
		if (taskqueue.peek().equals(task)){
			taskqueue.peek().setBeginTime(time);
			finTime();
		}
		numtask();
	}
	
	/****************************************************************************
	*																			
	*	Method:			done													
	*																			
	*	Purpose:		remove a finished task
	*																			
	****************************************************************************/
	
	public void done(){


		if (taskqueue.peek() != null){
			taskqueue.peek().setEndTime(finTime);
			recordtasks.add(taskqueue.poll());
			SetTime(finTime);
		}

		if (taskqueue.peek()!= null){
			taskqueue.peek().setBeginTime(time);
		}
		finTime();
		numtask();
		
	}
	
	/****************************************************************************
	*																			
	*	Method:			finTime													
	*																			
	*	Purpose:		calculate the finish time of the present task
	*																			
	****************************************************************************/
	
	public void finTime(){
		if (taskqueue.peek() == null){
			finTime = Double.POSITIVE_INFINITY;
		}
		else {
			Task onhand = taskqueue.peek();
			finTime = onhand.getBeginTime() + onhand.getSerTime();
		}
	}
	
	/****************************************************************************
	*																			
	*	Method:			numtask													
	*																			
	*	Purpose:		return the number of tasks in the queue
	*																			
	****************************************************************************/
	
	private void numtask(){
		NumTask = taskqueue.size();
		if (NumTask == 0) { isBusy = false; }
		else { isBusy = true; }
	}
}
