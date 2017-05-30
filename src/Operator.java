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
 * 	Purpose: 		generate task objects.
 * 
 **************************************************************************/

public class Operator {

	public int opId;
	public String name;
	public int[] tasklist;
	private loadparam parameters;
		
	// THIS IS A CONSTRUCTOR
		
	public Operator (int opid, loadparam param){
		parameters = param;
		opId = opid;
		name = parameters.opNames[opid];
		
	}
		
}
