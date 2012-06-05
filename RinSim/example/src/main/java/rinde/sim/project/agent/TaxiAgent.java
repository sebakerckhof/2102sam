package rinde.sim.project.agent;

import java.util.Queue;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.project.agent.dmas.exploration.ExplorationDMAS;
import rinde.sim.project.agent.dmas.intention.IntentionDMAS;
import rinde.sim.project.agent.state.State;
import rinde.sim.project.agent.state.StateContext;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.DMASModel;

/**
 * TAXI AGENT
 * 
 * Uses exploration Delegate MAS to explore possible passengers
 * Uses intention Delegate MAS to make 'promises' to passengers
 * 
 * Uses a State Design Pattern to choose actions
 */
public class TaxiAgent implements StateContext, AntAcceptor, TickListener, SimulatorUser{

	/**
	 * Intention DMAS makes 'promises' to passengers
	 */
	public IntentionDMAS iDmas;
	
	/**
	 * Exploration Dmas to explore possible passenger routes
	 */
	public ExplorationDMAS eDmas;
	
	/**
	 * Taxi for this agent
	 */
	private final Taxi taxi;
	
	/**
	 * Pick-up plan / A list of passengers this taxi wants to pick up
	 */
	private Queue<Point> plan;
	
	/**
	 * State for state pattern
	 */
	private State state;


	public TaxiAgent(Taxi taxi){
		this.taxi = taxi;
	}
	
	public Taxi getTaxi(){
		return taxi;
	}
	
	/**
	 * API INIT
	 */
	
	/**
	 * {@link SimulatorUser#setSimulator(SimulatorAPI)}
	 */
	@Override
	public void setSimulator(SimulatorAPI api) {
		//Register dmas's
		iDmas = new IntentionDMAS(this);
		api.register(iDmas);
		eDmas = new ExplorationDMAS(this);
		api.register(eDmas);
	}

	/**
	 * {@link TickListener#tick(long, long)}
	 * Uses the state pattern to select actions
	 */
	@Override
	public void tick(long currentTime, long timeStep) {
		state.execute(this);
	}

	/**
	 * {@link TickListener#afterTick(long, long)}
	 */
	@Override
	public void afterTick(long currentTime, long timeStep) {}


	/**
	 * {@link AntAcceptor#accept(AntAgent)}
	 */
	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}

	/**
	 * {@link StateContext#setState(State)}
	 */
	@Override
	public void setState(State s) {
		this.state = s;
	}

	/**
	 * {@link AntAcceptor#init(DMASModel)}
	 */
	@Override
	public void init(DMASModel rm) {
		rm.addAntAcceptor(this);
	}

}
