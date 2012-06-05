package rinde.sim.project.agent.heuristic;

import java.util.List;

import rinde.sim.project.agent.Passenger;

/**
 * Heuristic to evaluate a route
 */
public interface Heuristic {
	
	/**
	 * Calculate cost for given path
	 * @param path			List with passengers to pick-up (in the order given in the path)	
	 * @param distances		List of distances corresponding to the path's passengers (destination of previous passenger to pick-up location of this passenger
	 * @param travelTimes	List of travel times corresponding to the path's passengers (destination of previous passenger to pick-up location of this passenger
	 * @return cost of path
	 */
	public float execute(List<Passenger> path, List<Long> distances, List<Long> travelTimes);
}
