package rinde.sim.project.agent.heuristic;

import java.util.List;

import rinde.sim.project.agent.PassengerAgent;

public interface Heuristic {
	public int execute(List<PassengerAgent> path);
}
