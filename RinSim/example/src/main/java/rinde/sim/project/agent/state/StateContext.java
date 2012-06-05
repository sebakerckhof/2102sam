package rinde.sim.project.agent.state;

/**
 * Class for a user of the state pattern
 */
public interface StateContext {
	
	/**
	 * Set state to given state
	 * @param s	New state for statecontext object
	 */
	public void setState(State s);
}
