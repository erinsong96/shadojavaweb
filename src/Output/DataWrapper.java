package Output;

import java.io.*;
import java.util.ArrayList;

import Engine.Dispatch;
import Engine.Operator;
import Engine.Simulation;
import Engine.TrainSim;
import Input.loadparam;
import Engine.Replication;

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

    private Simulation where;


    private Dispatch[] here;

    private TrainSim[][] there;


    private String file_name;

    public DataWrapper(Simulation o, loadparam param) {
        parameter = param;
        where = o;
        here = new Dispatch[parameter.numReps];
        there = new TrainSim[parameter.numReps][parameter.numTrains];
    }

    public void read() {

        for (Replication each : where.getCompletesimulation()) {

            here[each.getRepID()] = each.getDispatch();
            there[each.getRepID()] = each.getTrains();

        }
    }

    public void generate() throws IOException {


        for (int i = 0; i < where.getCompletesimulation().length; i++) {


            Operator[] dispatchers = here[i].getDispatch();


            for (TrainSim each : there[i]) {

                for (Operator such : dispatchers) {
                    file_name = "/Users/erinsong/Documents/shadojava/out/" + such.name + ".csv";
                    System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(file_name)), true));
                    new ProcData(such.getQueue().records()).store(where.getCompletesimulation()[i].getTime(), such,
                            each.getTrainID());
                    new ProcData(such.getQueue().records()).run(such,
                            i);

                }


                Operator[] operators = each.operators;


                for (Operator him : operators) {

                    file_name = "/Users/erinsong/Documents/shadojava/out/" + him.name + ".csv";

                    System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(file_name)), true));
                    //System.out.println("for train " + each.trainID);
                    new ProcData(him.getQueue().records()).store(where.getCompletesimulation()[i].getTime(), him,
                            each.getTrainID());
                    new ProcData(him.getQueue().records()).run(him,
                            i);


                }
            }

        }

    }

}
