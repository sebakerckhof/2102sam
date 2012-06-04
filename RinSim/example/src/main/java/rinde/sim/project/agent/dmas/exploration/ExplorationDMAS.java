package rinde.sim.project.agent.dmas.exploration;

import java.util.Iterator;
import java.util.Set;

import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.DMAS;

public class ExplorationDMAS extends DMAS implements RoadUser{
	
	public static final int EXPLORATION_DEPTH = 3;
	public static final int DEFAULT_INTERVAL = 500;
	
	private RoadModel rm;
	private TaxiAgent agent;
	
	public ExplorationDMAS(TaxiAgent agent){
		this(agent, DEFAULT_INTERVAL);
	}
	
	public ExplorationDMAS(TaxiAgent agent, int interval) {
		super(interval);
		this.agent = agent;
	}


	
	//beliefs
	@Override
	public void execute(){

		if(agent.getTruck().hasLoad()){
			ExplorationAnt ant = new ExplorationAnt(agent.getTruck().getLoad().getDestination(), EXPLORATION_DEPTH);
			this.environment.deploy(ant);
		}else{
			Set<Passenger> passengers = rm.getObjectsNearby(agent.getTruck().getPosition(), Passenger.class, 500);
			Iterator<Passenger> iterator = passengers.iterator();
			int i = 0;
			while(iterator.hasNext() && i < 3){
				ExplorationAnt ant = new ExplorationAnt(iterator.next(), EXPLORATION_DEPTH);
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
