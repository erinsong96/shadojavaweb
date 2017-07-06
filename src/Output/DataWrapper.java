package Output;

import java.util.ArrayList;

import Engine.Dispatch;
import Engine.Operator;
import Engine.Simulation;
import Engine.TrainSim;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

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

    private Simulation once;

    private Dispatch here;

    private TrainSim[] there;

    private String file_name;

    public DataWrapper(Simulation o) {
        once = o;
    }

    public void read() {

        here = once.getDispatch();
        there = once.getTrains();

    }

    public void generate() throws FileNotFoundException {

        int i = 0;
        int j = 0;
        System.out.println("here are the Dispatchers");
        Operator[] dispatchers = here.getDispatch();
        for (Operator each : dispatchers) {
            file_name = "/Users/erinsong/Documents/shadojava/out/" + each.name + ".csv";
            System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(file_name)), true));
            new ProcData(each.getQueue().records()).run(once.getTime());
        }

        for (TrainSim each : there) {

            Operator[] operators = each.operators;

            for (Operator him : operators) {
                //System.out.println("for train " + each.trainID);
                file_name = "/Users/erinsong/Documents/shadojava/out/" + him.name + i + ".csv";
                System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(file_name)), true));
                new ProcData(him.getQueue().records()).run(once.getTime());

            }
            i += 1;
        }

        for (int k = 0; k < parameter.numTaskTypes; k++) {
            file_name = "/Users/erinsong/Documents/shadojava/out/" + "expiredtask" + ".csv";
            System.setOut(new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(file_name, true)), true));

            System.out.println("expired" + where.getExpiredtask()[k]);
            System.out.println("completed" + where.getCompletedtaskcount()[k]);

        }
    }

    }


