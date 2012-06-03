package rinde.sim.project.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.math.random.RandomGenerator;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.model.Model;
import rinde.sim.core.model.communication.CommunicationModel;
import rinde.sim.project.agent.dmas.AntAgent;

public class VirtualRoadModel implements Model<VirtualRoadUser>, SimulatorUser, TickListener
{

	public static final float DROP_RATE = 0.1f;
	
	protected Map<AntAcceptor,PheromoneInfrastructure> pheromones;
	protected Queue<AntAgent> queue;
	protected RandomGenerator generator;
	protected SimulatorAPI simulator;
	
	public VirtualRoadModel(RandomGenerator generator) {
		this.generator = generator;
		pheromones = new HashMap<AntAcceptor,PheromoneInfrastructure>();
	}
	
	public void deploy(AntAgent a){
		simulator.register(a);
		migrate(a);
	}
	
	public void migrate(AntAgent a){
		if(!pheromones.containsKey(a.next())){
			a.terminate();
		}
		queue.add(a);
	}
	
	@SuppressWarnings("unchecked")
	public <Y extends Pheromone> List<Y> smell(AntAcceptor a, Class<Y> type){
		LinkedList<Y> p = new LinkedList<Y>();
		if(pheromones.containsKey(a)){
			for(Pheromone pheromone : pheromones.get(a).smell()){
				if(type.isInstance(pheromone))
					p.add((Y) pheromone);
			}
		}
		
		return p;
	}
	
	public void drop(AntAcceptor a, Pheromone p){
		if(pheromones.containsKey(a)){
			pheromones.get(a).drop(p);
		}else{
			throw new IllegalArgumentException("Hier kunnen geen feromonen gedropped worden");
		}
	}

	public void addAntAcceptor(AntAcceptor a){
		pheromones.put(a, new PheromoneInfrastructure());
	}

	@Override
	public boolean register(VirtualRoadUser element) {
		element.init(this);
		return true;
	}

	@Override
	public boolean unregister(VirtualRoadUser element) {
		if(pheromones.containsKey(element)){
			pheromones.remove(element);
			return true;
		}
		return false;
	}

	@Override
	public Class<VirtualRoadUser> getSupportedType() {
		return VirtualRoadUser.class;
	}

	@Override
	public void tick(long currentTime, long timeStep) {}

	@Override
	public void afterTick(long currentTime, long timeStep) {
		
		//activate ants
		while(!queue.isEmpty())
		{
			send(queue.poll());
		}
		
		//Update pheromone infrastructure
		for(PheromoneInfrastructure pi : pheromones.values()){
			pi.update();
		}
		
	}
	
	protected void send(AntAgent a){
		if(generator.nextFloat() < DROP_RATE || !pheromones.containsKey(a.next())){ //migrate with certain probability
			a.terminate();
		}
		
		if(!a.isTerminated())
			a.move(a.next());

	}

	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}

}
