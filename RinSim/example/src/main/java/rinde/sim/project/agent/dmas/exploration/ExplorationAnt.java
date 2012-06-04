package rinde.sim.project.agent.dmas.exploration;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.DestinationAgent;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.PassengerAgent;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityPheromone;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;


public class ExplorationAnt extends AntAgent{

	public Queue<PassengerAgent> plan; 
	
	public ExplorationAnt(AntAcceptor start, int hops) {
		super(start, hops);
		plan = new LinkedList<PassengerAgent>();
	}
	
	@Override
	public AntAcceptor next(){
		if(!forward())
			return path.get(0);
		else
			return super.next();
	}
	

	@Override
	public void visit(TaxiAgent t){
		//check if home, report to edmas && terminate
		if(t.equals(plan.peek())){
			environment.drop(t, new ExplorationPheromone());
		}
			
		terminate();
	}
	
	@Override
	public void visit(Passenger t) {
		//calculate pick up cost & move to destination
		path.add(t);
	}

	@Override
	public void visit(Destination t) {
		//smell feasibility, clone & spread
		//avoid loops
		List<FeasibilityPheromone> pheromones = environment.smell(t, FeasibilityPheromone.class);
		
	}


	
}
