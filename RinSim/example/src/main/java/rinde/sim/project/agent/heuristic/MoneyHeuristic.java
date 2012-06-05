package rinde.sim.project.agent.heuristic;

import java.util.List;

import rinde.sim.project.agent.Passenger;

public class MoneyHeuristic implements Heuristic{

	public static final float startPrice = 5;
	public static final float kmPrice = 5;
	public static final float kmCost = 5;
	public static final float hourCost = 5;

	
	//TODO: convert units
	/**
	 * {@link Heuristic#execute(List, List, List)}
	 */
	@Override
	public float execute(List<Passenger> path, List<Long> distances, List<Long> travelTimes) {
		float result = 0;
		int i = 0;
		for(Passenger p : path){
		
			result += startPrice; //Starting price taxed at pickup
			result += kmPrice * p.getRequest().getTravelDistance(); //Price taxed for the ride from pickup to destination
			
			result -= kmCost * (distances.get(i) + p.getRequest().getTravelDistance()); //Cost per driven km
			result -= hourCost * (travelTimes.get(i) + p.getRequest().getTravelTime()); //Salary for the driver per hour, this can't be calculted in km as there can be waiting times
			
			i++;
		}
		return (result/path.size()); //normalize
	}
	
}
