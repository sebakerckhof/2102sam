package rinde.sim.project.agent.dmas.intention;

import rinde.sim.project.agent.DestinationAgent;
import rinde.sim.project.agent.PickupAgent;
import rinde.sim.project.agent.TruckAgent;
import rinde.sim.project.agent.dmas.AntAgent;
import rinde.sim.project.model.AntAcceptor;

public class IntentionAnt extends AntAgent{

	public IntentionAnt(AntAcceptor start, int hops) {
		super(start, hops);
	}

	@Override
	public void visit(TruckAgent t){
		//check if home
	}
	
	@Override
	public void visit(PickupAgent t) {
		//calculate pick up cost & move to destination
	}

	@Override
	public void visit(DestinationAgent t) {
		//smell feasibility, clone & spread
	}
}
