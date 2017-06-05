import java.util.*;
import Input.loadparam;

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
	public double time;
	public boolean isBusy;
	
	// Service time to complete all tasks in queue
	
	private int finTime;
	private int NumTask;
	
	// inspectors:
	
	public int getfinTime(){
		return finTime;
	}
	
	public int getNumTask(){
		return NumTask;
	}
	
	// Mutator:
	
	public void SetTime(double Time){
		this.time = Time;
	}
	
	public void Update(){
		SetTime(finTime);
		done();
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
		}
		finTime();
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
		taskqueue.poll();
		if (taskqueue.peek()!= null){
			taskqueue.peek().setBeginTime(time);
			finTime();
		}
		numtask();
	}
	
	/****************************************************************************
	*																			
	*	Method:			finTime													
	*																			
	*	Purpose:		calculate the finish time of the present task
	*																			
	****************************************************************************/
	
	public double finTime(){
		if (taskqueue.peek() == null){
			return Double.POSITIVE_INFINITY;
		}
		Task onhand = taskqueue.peek();
		return onhand.getBeginTime() + onhand.getSerTime();
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
