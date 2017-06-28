package Output;

import java.io.*;
import java.util.ArrayList;

import Engine.Dispatch;
import Engine.Operator;
import Engine.Simulation;
import Engine.TrainSim;
import Input.loadparam;

/***************************************************************************
 *
 * 	FILE: 			DataWrapper.java
 *
 * 	AUTHOR: 		ROCKY LI
 *
 * 	DATE:			2017/6/5
 *
 * 	VER: 			1.0
 *
 * 	Purpose: 		Wrapping the data field for analysis.
 *
 **************************************************************************/

public class DataWrapper {

    public loadparam parameter;

    private Simulation once;

    private Dispatch here;

    private TrainSim[] there;

    private Simulation[] where;


    private String file_name;

    public DataWrapper(Simulation o) {
        once = o;
    }

    public void read() {

        here = once.getDispatch();
        there = once.getTrains();

    }

    public void generate(loadparam param) throws IOException {


        where = new Simulation[param.numReps];

        for (int i = 0; i < param.numReps; i++) {


            Operator[] dispatchers = here.getDispatch();


            for (TrainSim each : there) {

                for (Operator such : dispatchers) {
                    file_name = "/Users/erinsong/Documents/shadojava/out/" + such.name + ".csv";
                    System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(file_name)), true));
                    new ProcData(such.getQueue().records()).run(once.getTime(), such,
                            each.getTrainID(), i);
                }


                Operator[] operators = each.operators;


                for (Operator him : operators) {

                    file_name = "/Users/erinson g/Documents/shadojava/out/" + him.name + ".csv";

                    System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(file_name, true)), true));
                    //System.out.println("for train " + each.trainID);
                    new ProcData(him.getQueue().records()).run(once.getTime(), him,
                            each.getTrainID(), i);


            }
        }

    }

    }

    public void process(loadparam param) throws IOException {
        // I'm going to call in run 
    }
}
