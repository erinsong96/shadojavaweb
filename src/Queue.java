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
	private Task[] tasklist;
	
	// inspectors:
	
	public int getfinTime(){
		return finTime;
	}
	
	public Task[] getTasklist(){
		return tasklist;
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
		taskqueue.peek().beginTime = time;
	}
	
	/****************************************************************************
	*																			
	*	Method:																
	*																			
	*	Purpose:		remove a finished task
	*																			
	****************************************************************************/
	
	public double finTime(){
		Task onhand = taskqueue.peek();
		return onhand.beginTime + onhand.serTime;
	}
}
