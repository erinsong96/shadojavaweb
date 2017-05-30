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

public class Task {
	
	//Task specific variables.
	
	public int Type;
	public int Phase;
	public int Priority;
	public double prevTime;
	public double arrTime;
	public double serTime;
	public double expTime;
	public double depTime;
	public double beginTime;
	public double endTime;
	public int[] opNums;
	private loadparam parameters;
	
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
		beginTime = 0;
		opNums = Param.opNums[Type];
	}
	
	/****************************************************************************
	*																			
	*	Method:			GetPhase													
	*																			
	*	Purpose:		Return a phase with the time input.
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
	
	public double Exponential(double lambda){
		
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
	
	public double Lognormal(double mean, double stddev){
		
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
	
	public double Uniform(double min, double max){
		
		return min + (max-min)*Math.random();
		
	}
	
	/****************************************************************************
	*																			
	*	Method:			genArrTime												
	*																			
	*	Purpose:		Generate a new exponentially distributed arrival time by phase.
	*																			
	****************************************************************************/
	
	public double GenTime (char type, double start, double end){
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
	
	public double genArrTime(double PrevTime){
		
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
	
	public double genSerTime(){
		
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
	
	public double genExpTime(){
		
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
