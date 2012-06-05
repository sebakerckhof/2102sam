package rinde.sim.project.agent;

import java.util.List;
import rinde.sim.project.agent.heuristic.Heuristic;
import rinde.sim.util.Tuple;

public class Plan implements Comparable<Plan>{
	
	private List<Passenger> path;
	private List<Long> distances;
	private List<Long> travelTimes;

	private Taxi taxi;
	private Heuristic heuristic;
	private float cost;
	
	public Plan(Taxi taxi, List<Passenger> path, List<Long> distances, List<Long> travelTimes, Heuristic heuristic){
		this.path = path;
		this.taxi = taxi;
		this.heuristic = heuristic;
		this.distances = distances;
		this.travelTimes = travelTimes;
		this.calculateCost();
	}
	
	public float getCost(){
		return cost;
	}
	
	public void setCost(float cost){
		this.cost = cost;
	}
	
	public void updateCost(Tuple<Long,Long> data){
		this.distances.set(0, data.getKey());
		this.travelTimes.set(0, data.getValue());
		calculateCost();
	}
	
	protected void calculateCost(){
		this.cost = heuristic.execute(taxi, path, distances, travelTimes);
	}
	
	public List<Long> getDistances() {
		return distances;
	}

	public void setDistances(List<Long> distances) {
		this.distances = distances;
	}

	public List<Long> getTravelTimes() {
		return travelTimes;
	}

	public void setTravelTimes(List<Long> travelTimes) {
		this.travelTimes = travelTimes;
	}
	
	public List<Passenger> getPath(){
		return path;
	}
	
	public Taxi getTaxi(){
		return taxi;
	}
	
	public int size(){
		return path.size();
	}
	
	@Override
	public int compareTo(Plan p){
		if(p.getCost() < this.getCost())
			return -1;
		if(p.getCost() == this.getCost())
			return 0;
		else return 1;
	}

}
