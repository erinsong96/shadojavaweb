package Input;

/***************************************************
 * 
 * 	FILE: loadparam.java
 * 
 * 	AUTHOR: BRANCH VINCENT
 * 
 * 	TRANSLATOR (FROM C++ to JAVA): ROCKY LI
 * 	
 * 	LATEST EDIT: 2017/5/22
 * 
 * 	VER: 1.0
 * 
 * 	Purpose: Load parameter in text.
 * 
 ***************************************************/

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
	
	public void loadparameter(String file){
		
		
		
	}
	
}
