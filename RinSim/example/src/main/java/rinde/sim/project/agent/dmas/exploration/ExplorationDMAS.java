package rinde.sim.project.agent.dmas.exploration;

import java.util.Iterator;
import java.util.Set;

import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.DMAS;
import rinde.sim.project.util.Utils;

public class ExplorationDMAS extends DMAS implements RoadUser{
	
	public static final int EXPLORATION_DEPTH = 3;
	public static final long DEFAULT_INTERVAL = Utils.timeConverter.min(60).toTime();
	
	private RoadModel rm;
	private TaxiAgent agent;
	
	public ExplorationDMAS(TaxiAgent agent){
		this(agent, DEFAULT_INTERVAL);
	}
	
	public ExplorationDMAS(TaxiAgent agent, long interval) {
		super(interval);
		this.agent = agent;
	}


	
	//beliefs
	@Override
	public void execute(long currentTime, long timeStep){

		if(agent.getTaxi().hasPassenger()){
			ExplorationAnt ant = new ExplorationAnt(agent.getTaxi(), currentTime, agent.getTaxi().getPassenger().getDestination(), EXPLORATION_DEPTH);
			this.environment.deploy(ant);
		}else{
			Set<Passenger> passengers = rm.getObjectsNearby(agent.getTaxi().getPosition(), Passenger.class, 500);
			Iterator<Passenger> iterator = passengers.iterator();
			int i = 0;
			while(iterator.hasNext() && i < 3){
				ExplorationAnt ant = new ExplorationAnt(agent.getTaxi(), currentTime, iterator.next(), EXPLORATION_DEPTH);
				this.environment.deploy(ant);
				i++;
			}
		}

	}

	public void getBestPath(){
		//evaluate beliefs and return path
	}

	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
	}
	
}
