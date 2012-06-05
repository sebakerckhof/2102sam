package rinde.sim.project.agent.dmas.exploration;

import java.util.Iterator;
import java.util.Set;

import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.DMAS;
import rinde.sim.project.util.Utils;

/**
 * DMAS for exploration/discovery of paths
 * 
 * Will send exploration ants that try to find paths and report them by using exploration pheromones
 */
public class ExplorationDMAS extends DMAS implements RoadUser{
	
	/**
	 * Size of the path we want to explore
	 */
	public static final int EXPLORATION_DEPTH = 3;
	
	/**
	 * Default interval to send exploration ants
	 */
	public static final long DEFAULT_INTERVAL = Utils.timeConverter.min(60).toTime();
	
	/**
	 * RoadModel API
	 */
	private RoadModel rm;
	
	/**
	 * Corresponding taxi agent
	 */
	private TaxiAgent agent;
	
	/**
	 * {@link #ExplorationDMAS(TaxiAgent, long)}
	 */
	public ExplorationDMAS(TaxiAgent agent){
		this(agent, DEFAULT_INTERVAL);
	}
	
	/**
	 * Constructor
	 * @param agent		Corresponding taxi agent
	 * @param interval	Interval to send exploration ants
	 * 
	 * {@link DMAS#DMAS(long)}
	 */
	public ExplorationDMAS(TaxiAgent agent, long interval) {
		super(interval);
		this.agent = agent;
	}


	
	/**
	 * {@link DMAS#execute(long, long)}
	 * Send exploration ants
	 */
	@Override
	public void execute(long currentTime, long timeStep){

		if(agent.getTaxi().hasPassenger()){ //Not going anywhere special now (= no passenger)
			ExplorationAnt ant = new ExplorationAnt(agent.getTaxi(), currentTime, agent.getTaxi().getPassenger().getRequest().getDestination(), EXPLORATION_DEPTH);
			this.environment.deploy(ant);
		}else{	//start from current passengers destination
			Set<Passenger> passengers = rm.getObjectsNearby(agent.getTaxi().getPosition(), Passenger.class, 500);
			Iterator<Passenger> iterator = passengers.iterator();
			int i = 0;
			while(iterator.hasNext() && i < 3){
				ExplorationAnt ant = new ExplorationAnt(agent.getTaxi(), currentTime, iterator.next(), EXPLORATION_DEPTH);
				simulator.register(ant);
				i++;
			}
		}

	}

	/**
	 * Find the best path
	 */
	public void getBestPath(){
		//evaluate beliefs and return path
	}

	/**
	 * {@link RoadUser#initRoadUser(RoadModel)}
	 */
	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
	}
	
}
