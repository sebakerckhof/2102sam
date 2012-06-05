package rinde.sim.project.agent.dmas.intention;

import java.util.LinkedList;

import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.PassengerAgent;
import rinde.sim.project.agent.Plan;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;

public class IntentionAnt extends AntAgent{

	protected Plan plan;
	
	public IntentionAnt(Plan plan) {
		super(new LinkedList<AntAcceptor>(plan.getPath()));
	}

	@Override
	public void visit(TaxiAgent t){
		t.iDmas.report(this);
	}
	
	@Override
	public void visit(Passenger t) {
		if(!environment.drop(t, new IntentionPheromone(plan.getTaxi(), plan.getCost()))){
			terminate();
		}
	}

}
