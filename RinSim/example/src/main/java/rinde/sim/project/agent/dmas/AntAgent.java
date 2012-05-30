package rinde.sim.project.agent.dmas;

import rinde.sim.core.model.communication.Message;
import rinde.sim.project.agent.PackageAgent;
import rinde.sim.project.agent.TruckAgent;
import rinde.sim.project.model.DMAS;

public abstract class AntAgent extends Message{
	
	private int hops;
	
	public AntAgent(DMAS sender, int hops){
		super(sender);
		this.hops = hops;
	}
	
	//forward-backward behaviour
	public void visit(TruckAgent t){}
	public void visit(PackageAgent t){}
}
