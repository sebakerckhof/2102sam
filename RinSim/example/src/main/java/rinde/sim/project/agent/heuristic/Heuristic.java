package rinde.sim.project.agent.heuristic;

import java.util.List;

import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.PassengerAgent;
import rinde.sim.project.agent.Taxi;
import rinde.sim.util.Tuple;

public interface Heuristic {
	public float execute(Taxi taxi, List<Passenger> path, List<Long> distances, List<Long> travelTimes);
}
