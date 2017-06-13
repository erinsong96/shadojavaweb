package Engine;
import Input.loadparam;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		// LOAD the parameter file.
		
		loadparam data;
		if (args.length == 0){
			data =  new loadparam("/Users/erinsong/Documents/shadojava/in/params.txt");
		} else {
			data = new loadparam(args[0]);
		}
		
		// Runs simulation.
		
		TrainSim runs = new TrainSim(data, 0);
		runs.genbasis();
		runs.run();
		for (Operator each: runs.operators){
			ProcData proc = new ProcData(each.getQueue().records());
			proc.run(data.numHours*60);
	 	}
		
	}
}
