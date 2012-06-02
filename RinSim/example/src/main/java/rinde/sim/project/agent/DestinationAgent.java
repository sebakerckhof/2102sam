package rinde.sim.project.agent;

import rinde.sim.project.agent.dmas.AntAgent;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityHolder;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityPheromone;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.old.PheromoneInfrastructure;

public class DestinationAgent implements AntAcceptor, FeasibilityHolder{

	PheromoneInfrastructure<FeasibilityPheromone> pheromones;
	
	public DestinationAgent(){
		
	}
	
	
	@Override
	public PheromoneInfrastructure<FeasibilityPheromone> getPheromoneInfrastructure() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}

}
