package Engine;
import Input.loadparam;

import java.util.ArrayList;

public class Simulation {

	private loadparam parameters;

    private int repnumber;
    private int repID;

    private Replication[] completesimulation;

    public Replication[] getCompletesimulation() {
        return completesimulation;
    }

    public Simulation(loadparam param) {
        parameters = param;
        repnumber = param.numReps;
        completesimulation = new Replication[repnumber];
    }


    public void run() {
        for (int i = 0; i < repnumber; i++) {
            repID = i;
            completesimulation[i] = new Replication(parameters);
            completesimulation[i].run();
        }
    }

	
}
