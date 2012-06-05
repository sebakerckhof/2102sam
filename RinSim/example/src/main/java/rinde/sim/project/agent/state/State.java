package rinde.sim.project.agent.state;

import rinde.sim.project.agent.TaxiAgent;

/**
 * State for state pattern
 */
public interface State{

	/**
	 * State belonging to a taxi agent
	 * @param context	Taxi agent for this state
	 */
	public void execute(TaxiAgent context);

}
