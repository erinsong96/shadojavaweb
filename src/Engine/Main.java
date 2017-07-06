package Engine;

import Input.loadparam;
import Output.DataWrapper;
import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {


		// LOAD the parameter file.

		loadparam data;
		if (args.length == 0){
			data =  new loadparam("/Users/erinsong/Documents/shadojava/in/params.txt");
		} else {
			data = new loadparam(args[0]);
		}

		// Runs simulation.

		Simulation once = new Simulation(data);
		once.run();

		DataWrapper analyze = new DataWrapper(once, data);
		analyze.generate();
		analyze.output();

	}

}
