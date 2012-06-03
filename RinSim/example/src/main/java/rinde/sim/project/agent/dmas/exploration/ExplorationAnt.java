package rinde.sim.project.agent.dmas.exploration;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import rinde.sim.project.agent.DestinationAgent;
import rinde.sim.project.agent.PackageAgent;
import rinde.sim.project.agent.TruckAgent;
import rinde.sim.project.agent.dmas.AntAgent;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityPheromone;
import rinde.sim.project.model.AntAcceptor;


public class ExplorationAnt extends AntAgent{

	public Queue<PackageAgent> plan; 
	
	public ExplorationAnt(AntAcceptor start, int hops) {
		super(start, hops);
		plan = new LinkedList<PackageAgent>();
	}
	
	@Override
	public AntAcceptor next(){
		if(!forward())
			return path.get(0);
		else
			return super.next();
	}
	

	@Override
	public void visit(TruckAgent t){
		//check if home, report to edmas && terminate
		if(t.equals(plan.peek())){
		
		}
			
		terminate();
	}
	
	@Override
	public void visit(PackageAgent t) {
		//calculate pick up cost & move to destination
		if(forward()){
			
		}
	}

	@Override
	public void visit(DestinationAgent t) {
		//smell feasibility, clone & spread
		//avoid loops
		List<FeasibilityPheromone> pheromones = getEnvironment().smell(t, FeasibilityPheromone.class);
		
	}
	
}
