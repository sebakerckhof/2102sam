package rinde.sim.project.agent;

import rinde.sim.project.agent.dmas.AntAgent;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityDMAS;
import rinde.sim.project.agent.dmas.intention.IntentionHolder;
import rinde.sim.project.agent.dmas.intention.IntentionPheromone;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.old.PheromoneInfrastructure;

public class PickupAgent implements AntAcceptor, IntentionHolder{

	protected FeasibilityDMAS feasibilityDMAS;
	protected PheromoneInfrastructure<IntentionPheromone> intentions;
	
	public PickupAgent(){
		this.feasibilityDMAS = new FeasibilityDMAS();
	}
	
	public void run
	
	@Override
	public PheromoneInfrastructure<IntentionPheromone> getIntentions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}

}
