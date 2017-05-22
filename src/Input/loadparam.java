package Input;
import java.io.*;
import java.util.*;

/***************************************************************************
 * 
 * 	FILE: 			loadparam.java
 * 
 * 	AUTHOR: 		BRANCH VINCENT
 * 
 * 	TRANSLATOR: 	ROCKY LI
 * 	
 * 	LATEST EDIT:	2017/5/22
 * 
 * 	VER: 			1.0
 * 
 * 	Purpose: 		Load parameter in text.
 * 
 **************************************************************************/

public class loadparam {
	
	// General input variables
	
	public
		String outputPath;
		double numHours;
        double[] traffic;
        int numReps;
        int[] ops;
        
    // Operator settings
        
    public
	    int numOps;
		String[] opNames;
		int[][] opTasks;
		
	// Task Settings
		
	public
		int numTaskTypes;
	    String[] taskNames;
	    int[][] taskPrty;
	    char[] arrDists;
	    double[][] arrPms;
	    char[] serDists;
	    double[][] serPms;
	    char[] expDists;
	   	double[][] expPmsLo;
	    double[][] expPmsHi;
		int[][] affByTraff;
		int[][] opNums;
		
	
	/****************************************************************************
	*																			
	*	Main Method:	loadparameter													
	*																			
	*	Purpose:		Load ALL parameter in text
	*																			
	****************************************************************************/
		
	public void loadparameter(String file) throws FileNotFoundException{
		
		//Declare a scanner for the file
		
		Scanner in = new Scanner(new File(file));
		
		//Read the header of the file
		
		outputPath = readString(in);
		numHours = readDouble(in);
		traffic = readTraff(in);
		numReps = readInt(in);
		numOps = readInt(in);
		numTaskTypes = readInt(in);
		
		//Initiate array sizes
		
		opNames = new String[numOps];
		opTasks = new int[numOps][];
		taskNames = new String[numTaskTypes];
		taskPrty = new int[numTaskTypes][];
		arrDists = new char[numTaskTypes];
		
		//Read in person type and tasks they can do
		
		for (int i = 0; i< numOps; i++){
			opNames[i] = readString(in);
			opTasks[i] = readIntArr(in);
		}
		
		//Read in the task parameters
		
		for (int i = 0; i< numTaskTypes; i++){
			taskNames[i] = readString(in);
			taskPrty[i] = readIntArr(in);
			arrDists[i] = readChar(in);
		}
		
//		for (int i = 0; i < numTaskTypes; i++)
//		{
//			readString(fin, taskNames[i]);						// name
//			readArr(fin, taskPrty[i]);							// priority
//			readVal(fin, arrDists[i]);							// arrival dist
//			readArr(fin, arrPms[i]); //, isInverted(arrDists[i]));	// arrival params
//			readVal(fin, serDists[i]);							// service dist
//			readArr(fin, serPms[i]); //, isInverted(serDists[i]));	// service params
//			readVal(fin, expDists[i]);							// expiration dist
//			readArr(fin, expPmsLo[i]); //, isInverted(expDists[i]));	// expiration params
//			readArr(fin, expPmsHi[i]); //, isInverted(expDists[i]));	// expiration params
//			readArr(fin, affByTraff[i]);						// traffic
//		}
		
	}
	
	/****************************************************************************
	*																			
	*	Method:		ridparametername													
	*																			
	*	Purpose:	Read a line in the text and remove the parameter name, also 
	*				returns the line as a scanner while moving the main scanner to
	*				the next line. Also ignore lines if it's empty.
	*
	*	NOTE:		ALL OF THE FOLLOWING METHODS include this method to skip
	*																			
	****************************************************************************/
	
	public Scanner ridparametername(Scanner in){
		
		//get rid of the parameter name in source file.
		String line = "";
		while (true){
			line = in.nextLine();
			if (!line.isEmpty())
				break;
		}
		Scanner input = new Scanner(line);
		input.next();
		return input;
		
	}
	
	/****************************************************************************
	*																			
	*	Method:		readString													
	*																			
	*	Purpose:	Read a string line in text and return string
	*																			
	****************************************************************************/
	
	public String readString(Scanner in){
		
		//Read string object
		
		Scanner input = ridparametername(in);
		String ret = input.nextLine();
		input.close();
		return ret;
		
	}
	
	/****************************************************************************
	*																			
	*	Method:		readTraff													
	*																			
	*	Purpose:	Read traffic line in text and return a double array
	*
	****************************************************************************/
	
	public double[] readTraff(Scanner in){
		
		Scanner input = ridparametername(in);
		
		ArrayList<String> traff = new ArrayList<String>();
		ArrayList<Double> traffic = new ArrayList<Double>();
		
		while (input.hasNext()){
			traff.add(input.next());
		}
		
		for (int i = 0; i<traff.size() ; i++){
			String get = traff.get(i);
			double myDouble = 0;
			switch(get){
			case "l": myDouble = 0.5; break;
			case "m": myDouble = 1.0; break;
			case "h": myDouble = 2.0; break;
			}
			traffic.add(myDouble);
		}
		input.close();
		
		return traffic.stream().mapToDouble(Double::doubleValue).toArray();
		
	}
	
	/****************************************************************************
	*																			
	*	Method:		readInt													
	*																			
	*	Purpose:	Read a integer line in text and return ONE int value
	*																			
	****************************************************************************/
	
	public int readInt(Scanner in){
		
		Scanner input = ridparametername(in);
		return input.nextInt();
		
	}
	
	/****************************************************************************
	*																			
	*	Method:		readDouble													
	*																			
	*	Purpose:	Read a integer line in text and return ONE int value
	*																			
	****************************************************************************/
	
	public double readDouble(Scanner in){
		
		Scanner input = ridparametername(in);
		return Double.parseDouble(input.next());
		
	}
	
	/****************************************************************************
	*																			
	*	Method:		readIntArr													
	*																			
	*	Purpose:	read an integer array from one line
	*																			
	****************************************************************************/
	
	public int[] readIntArr(Scanner in){
		
		Scanner input = ridparametername(in);
		ArrayList<Integer> ints = new ArrayList<Integer>();
		while (input.hasNextInt()){
			ints.add(input.nextInt());
		}
		input.close();
		return ints.stream().mapToInt(Integer::intValue).toArray();
		
	}
	
	/****************************************************************************
	*																			
	*	Method:		readDuobleArr													
	*																			
	*	Purpose:	read a double array from one line
	*																			
	****************************************************************************/
	
	public double[] readDoubleArr(Scanner in){
		
		Scanner input = ridparametername(in);
		ArrayList<Double> doubs = new ArrayList<Double>();
		while (input.hasNext()){
			double myDouble = Double.parseDouble(input.next());
			doubs.add(myDouble);
		}
		input.close();
		return doubs.stream().mapToDouble(Double::doubleValue).toArray();
		
	}
	
	/****************************************************************************
	*																			
	*	Method:		readChar													
	*																			
	*	Purpose:	read a character from one line
	*																			
	****************************************************************************/
	
	public char readChar(Scanner in){
		
		Scanner input = ridparametername(in);
		char myChar = input.next().charAt(0);
		input.close();
		return myChar;
		
	}
}
