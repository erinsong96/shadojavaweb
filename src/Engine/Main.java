package Engine;
import Input.loadparam;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("/home/aperocky/workspace/"
				+ "shadojava/out/log.txt")), true));
		
		// LOAD the parameter file.
		
		loadparam data;
		if (args.length == 0){
			data =  new loadparam("/home/aperocky/workspace/shadojava/in/params.txt");
		} else {
			data = new loadparam(args[0]);
		}
		
		// Runs simulation.
		
		Simulation once = new Simulation(data);
		once.run();
		
		DataWrapper analyze = new DataWrapper(once);
		analyze.read();
		analyze.generate();
		
	}
}
