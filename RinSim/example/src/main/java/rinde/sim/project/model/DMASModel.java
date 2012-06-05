package rinde.sim.project.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.math.random.RandomGenerator;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.model.Model;
import rinde.sim.project.model.pheromone.DefaultHandler;
import rinde.sim.project.model.pheromone.Pheromone;
import rinde.sim.project.model.pheromone.PheromoneHandler;
import rinde.sim.project.model.pheromone.PheromoneInfrastructure;

/**
 * DMAS Model
 * 
 * The dmas model provides the pheromone infrastructure at every ant acceptor node
 * Furthermore it has to move ants around, this is not based on ticks, but it would be very easy to change it so it does move ants with every tick
 */
public class DMASModel implements Model<DMASUser>, SimulatorUser, TickListener
{

	/**
	 * The rate of which connections drop messages (ants)
	 */
	public static final float DROP_RATE = 0.1f;
	
	/**
	 * ADAPTABILITY RATE
	 * 
	 * -changes everywhere: the pheromone lifetime
	 * -the dmas run frequency
	 */
	public static final float ADAPTABILITY_RATE = 1;
	
	/**
	 * Map of ant acceptor and the pheromones that have been dropped there
	 */
	protected Map<AntAcceptor,PheromoneInfrastructure> pheromones;
	
	/**
	 * Queue of ants waiting to be moved
	 */
	protected Queue<AntAgent> queue;
	
	/**
	 * Random generator for those special occasions
	 */
	protected RandomGenerator generator;
	
	/**
	 * API's
	 */
	protected SimulatorAPI simulator;
	
	/**
	 * Constructor
	 * @param generator	Random generator for those special occasions
	 */
	public DMASModel(RandomGenerator generator) {
		this.generator = generator;
		pheromones = new HashMap<AntAcceptor,PheromoneInfrastructure>();
	}
	
	/**
	 * PHEROMONE HANDLING
	 */

	/**
	 * Smell pheromones of given type at given ant acceptor
	 * 
	 * @param a		Ant acceptor to smell
	 * @param type	Smell for this type of pheromones
	 * @return	Corresponding pheromones
	 */
	public <Y extends Pheromone> List<Y> smell(AntAcceptor a, Class<Y> type){
		LinkedList<Y> p = new LinkedList<Y>();
		if(pheromones.containsKey(a)){
			pheromones.get(a).smell(type);
		}
		
		return p;
	}
	
	/**
	 * Drop a pheromone at an ant acceptor
	 * @param a	Ant acceptor to drop the pheromone at
	 * @param p	Pheromone to drop
	 * @return	{@link PheromoneInfrastructure#drop(Pheromone)}
	 */
	public boolean drop(AntAcceptor a, Pheromone p){
		if(pheromones.containsKey(a)){
			p.setLifetime(Math.round(p.getLifetime() / ADAPTABILITY_RATE));
			return pheromones.get(a).drop(p);
		}else{
			throw new IllegalArgumentException("Hier kunnen geen feromonen gedropped worden");
		}
	}

	/**
	 * Add an ant acceptor with default Pheromone handler
	 * @param a	Ant acceptor to add
	 * {@link #addAntAcceptor(AntAcceptor, PheromoneHandler)}
	 */
	public void addAntAcceptor(AntAcceptor a){
		this.addAntAcceptor(a, new DefaultHandler());
	}
	
	/**
	 * Add an ant acceptor with custom pheromone handler
	 * @param a			Ant acceptor to add
	 * @param handler	custom pheromone handler
	 */
	public void addAntAcceptor(AntAcceptor a, PheromoneHandler handler){
		pheromones.put(a, new PheromoneInfrastructure(handler));
	}

	/**
	 * ANT SEND LOGIC
	 */
	
	/**
	 * Deploy an ant (add it to the travelling ants queue, if the node the ant wants to travel to exists)
	 * @param a	Ant to add
	 */
	public void deploy(AntAgent a){
		if(!pheromones.containsKey(a.next())){ //next ant acceptor is not registered => terminate
			a.terminate();
		}
		queue.add(a);
	}
	/**
	 * {@link TickListener#tick(long, long)}
	 */
	@Override
	public void tick(long currentTime, long timeStep) {}

	/**
	 * {@link TickListener#afterTick(long, long)}
	 */
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
	/**
	 * Send ant to destination
	 * @param a	Ant to send to destination
	 */
	protected void send(AntAgent a){
		if(generator.nextFloat() < DROP_RATE || !pheromones.containsKey(a.next())){ //migrate with certain probability
			a.terminate(); //connection failed...
		}
		
		if(!a.isTerminated())
			a.move(a.next());

	}

	
	/**
	 * MODEL TASKS
	 */
	
	/**
	 * {@link Model#register(Object)}
	 */
	@Override
	public boolean register(DMASUser element) {
		element.init(this);
		return true;
	}

	/**
	 * {@link Model#unregister(Object)}
	 * destroys pheromone infrastructure at given node
	 */
	@Override
	public boolean unregister(DMASUser element) {
		if(pheromones.containsKey(element)){
			pheromones.remove(element);
			return true;
		}
		return false;
	}

	/**
	 * {@link Model#getSupportedType()}
	 */
	@Override
	public Class<DMASUser> getSupportedType() {
		return DMASUser.class;
	}
	
	
	/**
	 * API's
	 */
	
	/**
	 * {@link SimulatorUser#setSimulator(SimulatorAPI)}
	 */
	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}

}
