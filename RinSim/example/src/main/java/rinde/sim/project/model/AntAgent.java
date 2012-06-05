package rinde.sim.project.model;


import java.util.LinkedList;
import java.util.List;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.TaxiAgent;

/**
 * Abstract ant agent
 * An ant agent is an agent sent by a DMAS
 * 
 * ANT agents visit nodes using double dispatch (visitor pattern), this way it is easy to take action according to the node we are at
 */
public abstract class AntAgent implements RoadUser, SimulatorUser, DMASUser, Cloneable{
	
	/**
	 * Hops to travel
	 */
	protected int hops;
	
	/**
	 * Current node
	 */
	protected int index;
	
	/**
	 * Ant is dead or not
	 */
	protected boolean terminated = false;
	
	/**
	 * Path for ant to follow
	 */
	protected List<AntAcceptor> path;
	
	/**
	 * API'S
	 */
	protected DMASModel environment;
	protected SimulatorAPI simulator;
	protected RoadModel rm;
	
	/**
	 * Constructor
	 * @param path	Path for ant to follow
	 * @param hops  Hops to travel
	 */
	public AntAgent(List<AntAcceptor> path, int hops){
		this.hops = hops;
		this.path = path;
	}
	
	/**
	 * {@link #AntAgent(List, int)}
	 * @param start	Starting point to start travelling to
	 */
	public AntAgent(AntAcceptor start, int hops){
		this(new LinkedList<AntAcceptor>(), hops);
		this.path.add(start);
	}
	
	/**
	 * {@link #AntAgent(List, int)}
	 * @param path Path to travel
	 */
	public AntAgent(List<AntAcceptor> path){
		this(path,path.size());
	}
	
	/**
	 * VISITOR PATTERN
	 */
	public void visit(Passenger t){}
	public void visit(Destination t){}
	public void visit(TaxiAgent t){}
	
	/**
	 * TRAVEL LOGIC
	 */
	
	/**
	 * Get current host node
	 */
	public AntAcceptor current(){
		return path.get(index);
	}
	
	/**
	 * Finds next node to travel to
	 * @return	next node to travel to
	 */
	public AntAcceptor next(){
		if(hops > 0) //forward
			return path.get(index + 1);
		else //backward
			return path.get(index - 1);
	}
	
	/**
	 * Move ant to new node
	 * @param a	Node ant needs to move to
	 */
	public void move(AntAcceptor a){
		hops--;
		index = path.indexOf(a);
		
		a.accept(this);
		
		if(!isTerminated())
			environment.deploy(this);
	}
	
	public boolean isTerminated(){
		return terminated;
	}
	
	/**
	 * Terminate ant, unregisters ant from simulator
	 */
	public void terminate(){
		this.simulator.unregister(this);
		this.terminated = true;
	}
	
	/**
	 * Whether we are moving forwards
	 * @return	true if forward, false otherwise
	 */
	public boolean forward(){
		return hops > 0;
	}
	
	/**
	 * Whether we are moving backwards
	 * @return	true if backwards, false otherwise
	 */
	public boolean backward(){
		return !forward();
	}
	
	
	/**
	 * API'S
	 */
	/**
	 * {@link DMASUser#init(DMASModel)}
	 */
	@Override
	public void init(DMASModel vrm){
		this.environment = vrm;
		environment.deploy(this);
	}
	
	/**
	 * {@link SimulatorUser#setSimulator(SimulatorAPI)}
	 */
	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}
	
	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
	}

}
