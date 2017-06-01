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

public class Queue {
	
	public PriorityQueue<Task> taskqueue;
	public int opId;
	private double time;
	
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
		time = Time;
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
		taskqueue.add(task);
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
		taskqueue.peek().getBeginTime() = time;
	}
	
	/****************************************************************************
	*																			
	*	Method:			finTime													
	*																			
	*	Purpose:		calculate the finish time of the present task
	*																			
	****************************************************************************/
	
	public double finTime(){
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
	}
}
