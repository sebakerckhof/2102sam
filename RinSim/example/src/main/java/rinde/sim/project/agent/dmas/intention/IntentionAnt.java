package rinde.sim.project.agent.dmas.intention;

import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.DestinationAgent;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.PassengerAgent;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;

public class IntentionAnt extends AntAgent{

	public IntentionAnt(AntAcceptor start, int hops) {
		super(start, hops);
	}

	@Override
	public void visit(TaxiAgent t){
		//check if home
	}
	
	@Override
	public void visit(Passenger t) {
		//calculate pick up cost & move to destination
	}

	@Override
	public void visit(Destination t) {
		//smell feasibility, clone & spread
	}
}
