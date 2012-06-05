package rinde.sim.project.agent.dmas.feasibility;

import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.TransportRequest;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.pheromone.Pheromone;

/**
 * Feasibility pheromone
 */
public class FeasibilityPheromone extends Pheromone implements Comparable<FeasibilityPheromone>{

	/**
	 * Default lifetime of this pheromone
	 */
	private static final long DEFAULT_LIFETIME = FeasibilityDMAS.DEFAULT_INTERVAL;
	
	/**
	 * Reference to the source of the pheromone
	 */
	public final Passenger source;
	
	/**
	 * The transport request for the passenger
	 */
	public final TransportRequest request;
	
	/**
	 * The estimated travel time between the passenger and the destination where we leave the pheromone
	 */
	public final long estimatedTravelTime;
	
	/**
	 * The estimated travel distance between the passenger and the destination where we leave the pheromone
	 */
	public final long estimatedTravelDistance;
	
	
	/**
	 * Constructor
	 * @param passenger					Passenger for which the ants leaves the pheromone
	 * @param estimatedTravelTime		The estimated travel time between the passenger and the destination where we leave the pheromone
	 * @param estimatedTravelDistance	The estimated travel distance between the passenger and the destination where we leave the pheromone
	 */
	public FeasibilityPheromone(Passenger passenger, long estimatedTravelTime, long estimatedTravelDistance){
		super(Math.round(DEFAULT_LIFETIME));
		this.source = passenger;
		this.request = passenger.getRequest();
		this.estimatedTravelTime = estimatedTravelTime;
		this.estimatedTravelDistance = estimatedTravelDistance;
	}
	
	/**
	 * {@link Comparable#compareTo(Object)}
	 */
	@Override
	public int compareTo(FeasibilityPheromone fp){
		return request.compareTo(fp.request);
	}
}
