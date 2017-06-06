import java.util.*;
import Input.loadparam;

/***************************************************************************
 * 
 * 	FILE: 			Task.java
 * 
 * 	AUTHOR:			BRANCH VINCENT
 * 
 * 	TRANSLATOR		ROCKY LI
 * 	
 * 	LATEST EDIT:	2017/5/24
 * 
 * 	VER: 			1.0
 * 
 * 	Purpose: 		generate task objects.
 * 
 **************************************************************************/

public class Task implements Comparable<Task> {

//Task specific variables.

	private int Type;
	private int Phase;
	private int Priority;
	private double prevTime;
	private double arrTime;
	private double serTime;
	private double expTime;
	private double depTime;
	private double beginTime;
	private double endTime;
	private int[] opNums;
	private loadparam parameters;

// Mutators
	
	public void setEndTime(double time){
		endTime = time;
	}

	public void setBeginTime(double time){
		beginTime = time;
	}

	/****************************************************************************
	 *
	 *	Main Object:	Task
	 *
	 *	Purpose:		Generate a new task on completion of old task. And return
	 *					it's parameters.
	 *
	 ****************************************************************************/

	public Task (int type, double PrevTime, loadparam Param){
		Type = type;
		prevTime = PrevTime;
		parameters = Param;
		Phase = getPhase(PrevTime, parameters.numHours);
		Priority = Param.taskPrty[Type][Phase];
		arrTime = genArrTime(PrevTime);
		serTime = genSerTime();
		expTime = genExpTime();
		beginTime = arrTime;
		opNums = Param.opNums[Type];
	}

	/****************************************************************************
	 *
	 *	Method:			compareTo
	 *
	 *	Purpose:		Compare two task based on their priority
	 *
	 ****************************************************************************/

	@Override
	public int compareTo(Task other){
		return this.Priority - other.Priority;
	}

	// The following are inspector functions.

	public int getType() {return this.Type;}

	public int getPriority(){return this.Priority;}

	public double getPrevTime(){return this.prevTime;}

	public double getArrTime(){return this.arrTime;}

	public double getSerTime(){return this.serTime;}

	public double getEndTime(){return this.endTime;}

	public double getExpTime() {return this.expTime;}

	public double getDepTime() {return this.depTime;}

	public double getBeginTime() {return this.beginTime;}

	public int[] getOpNums() {return this.opNums;}


	/****************************************************************************
	 *
	 *	Method:			GetPhase
	 *
	 *	Purpose:		Return the Phase
	 *
	 ****************************************************************************/


	public int getPhase(double time, double hours){
		
		if (time<30){
			return 0;
		} 
		else if (time>=30 && time<hours*60-30){
			return 1;
		}
		else return 2;
		
	}
	
	/****************************************************************************
	*																			
	*	Method:			Exponential													
	*																			
	*	Purpose:		Return an exponential distributed random number with input
	*					parameter lambda.
	*																			
	****************************************************************************/
	
	private double Exponential(double lambda){
		
		if (lambda == 0){
			return Double.POSITIVE_INFINITY;
		}
		return Math.log(1-Math.random())/(-lambda);
		
	}
	
	/****************************************************************************
	*																			
	*	Method:			Lognormal												
	*																			
	*	Purpose:		Return a lognormal distributed random number with input 
	*					mean and standard deviation.
	*																			
	****************************************************************************/
	
	private double Lognormal(double mean, double stddev){
		
		Random rng = new Random();
		double normal = rng.nextGaussian();
		return mean + stddev*normal;
		
	}
	
	/****************************************************************************
	*																			
	*	Method:			Uniform												
	*																			
	*	Purpose:		Return a uniform distribution with input minimum and maximum
	*																			
	****************************************************************************/
	
	private double Uniform(double min, double max){
		
		return min + (max-min)*Math.random();
		
	}
	
	/****************************************************************************
	*																			
	*	Method:			genArrTime												
	*																			
	*	Purpose:		Generate a new exponentially distributed arrival time by phase.
	*																			
	****************************************************************************/
	
	private double GenTime (char type, double start, double end){
		switch (type){
		case 'E' : return Exponential(start);
		case 'L' : return Lognormal(start, end);
		case 'U' : return Uniform(start, end);
		default: 
			throw new IllegalArgumentException("Wrong Letter");
		}
	}
	
	/****************************************************************************
	*																			
	*	Method:			genArrTime												
	*																			
	*	Purpose:		Generate a new exponentially distributed arrival time by phase.
	*																			
	****************************************************************************/
	
	private double genArrTime(double PrevTime){
		
		double TimeTaken = Exponential(parameters.arrPms[Type][Phase]);
		
		if (TimeTaken == Double.POSITIVE_INFINITY){
			return Double.POSITIVE_INFINITY;
		}
		
		double newArrTime = TimeTaken + prevTime;
		
		if (parameters.affByTraff[Type][Phase] == 1 && loadparam.TRAFFIC_ON){
			
			double budget = TimeTaken;
			double currTime = prevTime;
			int currHour = (int) currTime/60;
			double traffLevel = parameters.traffic[currHour];
			double TimeToAdj = (currHour+1)*60 - currTime;
			double adjTime = TimeToAdj * traffLevel;
			
			while (budget > adjTime){
				
				budget -= adjTime;
				currTime += TimeToAdj;
				currHour ++;
				
				if (currHour >= parameters.traffic.length){
					return Double.POSITIVE_INFINITY;
				}
				
				traffLevel = parameters.traffic[currHour];
				TimeToAdj = (currHour + 1)*60 - currTime;
				adjTime = TimeToAdj * traffLevel;
				
			}
			
			newArrTime = currTime + budget/traffLevel;
		}
		
		return newArrTime;
	}
	
	/****************************************************************************
	*																			
	*	Method:			genSerTime												
	*																			
	*	Purpose:		Generate a new service time.
	*																			
	****************************************************************************/
	
	private double genSerTime(){
		
		char type = parameters.arrDists[Type];
		double start = parameters.arrPms[Type][0];
		double end = parameters.arrPms[Type][1];
		return GenTime(type, start, end);
		
	}
	
	/****************************************************************************
	*																			
	*	Method:			genExpTime												
	*																			
	*	Purpose:		Generate a new Expiration time.
	*																			
	****************************************************************************/
	
	private double genExpTime(){
		
		double param;
		double expiration = 0;
		int hour = (int) arrTime/60;
		if (hour >= parameters.traffic.length){
			return arrTime;
		} else if (parameters.traffic[hour] == 2){
			param = parameters.expPmsHi[Type][Phase];
		} else {
			param = parameters.expPmsLo[Type][Phase];
		}
		expiration = GenTime(parameters.expDists[Type], param, 0);
		return 2*serTime + expiration;
	}
}
