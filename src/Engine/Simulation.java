package Engine;
import Input.loadparam;
import java.util.ArrayList;

public class Simulation {

	private loadparam parameters;


    private ArrayList<ArrayList<Task>> results;

	private ArrayList<Task> linked;

	private TrainSim[] trains;

	private Dispatch control;

	private double totaltime;

	// Inspectors:

    public TrainSim[] getTrains() {
        return trains;
    }

    public Dispatch getDispatch() {
        return control;
    }

    public double getTime() {
        return totaltime;
    }


    public Simulation(loadparam param) {
        parameters = param;
        totaltime = parameters.numHours * 60;

    }


    public void run() {

		// Initialize control center.

        control = new Dispatch(parameters);
		control.run();
		linked = control.gettasks();

        // Initialize trains.

        trains = new TrainSim[parameters.numTrains];

        for (int i = 0; i < parameters.numTrains; i++) {

			trains[i] = new TrainSim(parameters, i);
			trains[i].genbasis();

        }

        // Add linked tasks to trains.

        for (Task each : linked) {

			int trainid = each.getTrain();
			trains[trainid].linktask(each);

        }

        // Run each train

        for (TrainSim each : trains) {

			each.run();

        }

    }
	
}
