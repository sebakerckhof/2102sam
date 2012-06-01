package rinde.sim.project.agent.dmas;

import java.util.Collection;
import java.util.LinkedList;

import rinde.sim.project.agent.DestinationAgent;
import rinde.sim.project.agent.PackageAgent;
import rinde.sim.project.agent.PickupAgent;
import rinde.sim.project.agent.TruckAgent;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.DMAS;

public abstract class AntAgent implements Cloneable{
	
	private int hops;
	private boolean terminated = false;
	private Collection<AntAcceptor> path;
	
	public AntAgent(Collection<AntAcceptor> path, int hops){
		this.hops = hops;
		this.path = path;
	}
	
	public AntAgent(AntAcceptor start, int hops){
		this(new LinkedList<AntAcceptor>(), hops);
		this.path.add(start);
	}
	
	public AntAgent(Collection<AntAcceptor> path){
		this(path,path.size());
	}
	
	
	public void visit(PickupAgent t){};
	public void visit(DestinationAgent t){};
	public void visit(TruckAgent t){};
	
	public boolean isTerminated(){
		return terminated;
	}
	
	public void terminate(){
		this.terminated = true;
	}
}
