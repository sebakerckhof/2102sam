package rinde.sim.project.model;


import java.util.LinkedList;
import java.util.List;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.PassengerAgent;
import rinde.sim.project.agent.TaxiAgent;

public abstract class AntAgent implements RoadUser, SimulatorUser, DMASUser, Cloneable{
	
	protected int hops;
	protected int index;
	protected boolean terminated = false;
	protected List<AntAcceptor> path;
	
	protected DMASModel environment;
	protected SimulatorAPI simulator;
	protected RoadModel rm;
	
	public AntAgent(List<AntAcceptor> path, int hops){
		this.hops = hops;
		this.path = path;
	}
	
	public AntAgent(AntAcceptor start, int hops){
		this(new LinkedList<AntAcceptor>(), hops);
		this.path.add(start);
	}
	
	public AntAgent(List<AntAcceptor> path){
		this(path,path.size());
	}
	
	public AntAcceptor current(){
		return path.get(index);
	}
	
	public AntAcceptor next(){
		if(hops > 0) //forward
			return path.get(index + 1);
		else //backward
			return path.get(index - 1);
	}
	
	public void move(AntAcceptor a){
		hops--;
		index = path.indexOf(a);
		if(index == path.size())
			hops = 0;
		
		a.accept(this);
		
		if(!isTerminated())
			environment.deploy(this);
	}
	
	public void visit(Passenger t){}
	public void visit(Destination t){}
	public void visit(TaxiAgent t){}
	
	public boolean isTerminated(){
		return terminated;
	}
	
	public void terminate(){
		this.simulator.unregister(this);
		this.terminated = true;
	}
	
	public boolean forward(){
		return hops > 0;
	}
	
	public boolean backward(){
		return !forward();
	}
	
	@Override
	public void init(DMASModel vrm){
		this.environment = vrm;
	}
	
	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}
	
	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
	}

}
