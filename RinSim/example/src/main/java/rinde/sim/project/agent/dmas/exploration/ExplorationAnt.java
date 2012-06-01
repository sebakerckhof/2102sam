package rinde.sim.project.agent.dmas.exploration;

import rinde.sim.project.agent.DestinationAgent;
import rinde.sim.project.agent.PickupAgent;
import rinde.sim.project.agent.TruckAgent;
import rinde.sim.project.agent.dmas.AntAgent;
import rinde.sim.project.model.AntAcceptor;


public class ExplorationAnt extends AntAgent{

	public ExplorationAnt(AntAcceptor start, int hops) {
		super(start, ExplorationDMAS.EXPLORATION_DEPTH);
	}

	@Override
	public void visit(TruckAgent t){
		//check if home, report to edmas
	}
	
	@Override
	public void visit(PickupAgent t) {
		//calculate pick up cost & move to destination
	}

	@Override
	public void visit(DestinationAgent t) {
		//smell feasibility, clone & spread
		//avoid loops
	}
	
}
