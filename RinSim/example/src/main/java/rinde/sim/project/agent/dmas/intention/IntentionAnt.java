package rinde.sim.project.agent.dmas.intention;

import java.util.LinkedList;

import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.Plan;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;

/**
 * An intention ant is send by a taxi to make reservations for a given plan
 */
public class IntentionAnt extends AntAgent{

	/**
	 * The plan from the exploration ant for which we want to make reservations
	 */
	protected final Plan plan;
	
	/**
	 * Constructor
	 * @param plan	The plan from the exploration ant for which we want to make reservations
	 */
	public IntentionAnt(Plan plan) {
		super(new LinkedList<AntAcceptor>(plan.getPath()));
		this.plan = plan;
	}

	/**
	 * {@link #visit(TaxiAgent)}
	 */
	@Override
	public void visit(TaxiAgent t){
		if(t.getTaxi().equals(plan.getTaxi())) //we're home! => report Success!
			t.iDmas.report(this);
	}
	
	/**
	 * {@link #visit(Passenger)}
	 */
	@Override
	public void visit(Passenger t) {	//No allowed to drop pheromone => terminate 
		if(!environment.drop(t, new IntentionPheromone(plan.getTaxi(), plan.getCost()))){
			terminate();
		}
	}

}
