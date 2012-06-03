package rinde.sim.project.agent.dmas;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import rinde.sim.project.agent.DestinationAgent;
import rinde.sim.project.agent.PackageAgent;
import rinde.sim.project.agent.PackageAgent;
import rinde.sim.project.agent.TruckAgent;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.DMAS;
import rinde.sim.project.model.VirtualRoadModel;
import rinde.sim.project.model.VirtualRoadUser;

public abstract class AntAgent implements VirtualRoadUser, Cloneable{
	
	protected int hops;
	protected int index;
	protected boolean terminated = false;
	protected List<AntAcceptor> path;
	protected VirtualRoadModel environment;
	
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
			this.getEnvironment().deploy(this);
	}
	
	public void visit(PackageAgent t){};
	public void visit(DestinationAgent t){};
	public void visit(TruckAgent t){};
	
	public boolean isTerminated(){
		return terminated;
	}
	
	public void terminate(){
		this.terminated = true;
	}
	
	public boolean forward(){
		return hops > 0;
	}
	
	public boolean backward(){
		return !forward();
	}
	
	@Override
	public void init(VirtualRoadModel vrm){
		this.environment = vrm;
	}
	
	protected VirtualRoadModel getEnvironment(){
		return this.environment;
	}
}
