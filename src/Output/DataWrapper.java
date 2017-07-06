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


    private Replication what;


    private String file_name;

    public DataWrapper(Simulation o, loadparam param) {
        parameter = param;
        where = o;
    }



    public void generate() throws IOException {

        for (int i = 0; i < where.getCompletesimulation().length; i++) {

            what = where.getCompletesimulation()[i];

            Dispatch dispatch = what.getDispatch();
            Operator[] dispatchers = dispatch.getDispatch();

            for (TrainSim each : what.getTrains()) {
                for (Operator such : dispatchers) {

                    new ProcData(such.getQueue().records()).store(where.getCompletesimulation()[i].getTime(), such,
                            each.getTrainID(), where, i);

                }

                Operator[] operators = each.operators;


                for (Operator him : operators) {


                    new ProcData(him.getQueue().records()).store(where.getCompletesimulation()[i].getTime(), him,
                            each.getTrainID(), where, i);


                }
            }

        }

    }

    public void output() throws IOException {

        for (int i = 0; i < parameter.numDispatch; i++) {
            file_name = "/Users/erinsong/Documents/shadojava/out/" + "Dispatcher" + i + ".csv";
            System.setOut(new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(file_name, true)), true));
            where.getDispatchoutput(i).outputdata();
        }

        for (int j = 0; j < parameter.numOps; j++) {
            file_name = "/Users/erinsong/Documents/shadojava/out/" + parameter.opNames[j] + ".csv";
            System.setOut(new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(file_name, true)), true));
            where.getOperatoroutput(j).outputdata();

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


