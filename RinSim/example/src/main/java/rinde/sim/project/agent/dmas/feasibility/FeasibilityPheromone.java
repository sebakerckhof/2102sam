package rinde.sim.project.agent.dmas.feasibility;

import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.TransportRequest;
import rinde.sim.project.model.DMAS;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.pheromone.Pheromone;

public class FeasibilityPheromone extends Pheromone implements Comparable<FeasibilityPheromone>{

	private static final long DEFAULT_LIFETIME = FeasibilityDMAS.DEFAULT_INTERVAL;
	
	public final Passenger source;
	public final TransportRequest request;
	public final long estimatedTravelTime;
	public final long estimatedTravelDistance;
	
	public FeasibilityPheromone(Passenger passenger, long estimatedTravelTime, long estimatedTravelDistance){
		super(Math.round(DEFAULT_LIFETIME *  (1 / DMASModel.ADAPTABILITY_RATE)));
		this.source = passenger;
		this.request = passenger.getRequest();
		this.estimatedTravelTime = estimatedTravelTime;
		this.estimatedTravelDistance = estimatedTravelDistance;
	}
	
	@Override
	public int compareTo(FeasibilityPheromone fp){
		return request.compareTo(fp.request);
	}
}
