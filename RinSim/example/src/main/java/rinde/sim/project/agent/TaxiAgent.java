package rinde.sim.project.agent;

import java.util.Queue;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.communication.CommunicationAPI;
import rinde.sim.core.model.communication.Message;
import rinde.sim.project.agent.dmas.exploration.ExplorationDMAS;
import rinde.sim.project.agent.dmas.intention.IntentionDMAS;
import rinde.sim.project.agent.state.State;
import rinde.sim.project.agent.state.StateContext;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.DMASUser;


public class TaxiAgent implements StateContext, AntAcceptor, TickListener, SimulatorUser{

	public IntentionDMAS iDmas;
	public ExplorationDMAS eDmas;
	private Taxi taxi;
	private SimulatorAPI simulator;
	private DMASModel vrm;
	private Queue<Point> path;
	private State state;
	
	public TaxiAgent(Taxi taxi){
		this.taxi = taxi;
	}
	
	public Taxi getTruck(){
		return taxi;
	}
	
	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
		
		iDmas = new IntentionDMAS(this);
		simulator.register(iDmas);
		eDmas = new ExplorationDMAS(this);
		simulator.register(eDmas);
	}

	@Override
	public void tick(long currentTime, long timeStep) {
		state.execute(this);
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {}


	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}

	@Override
	public void setState(State s) {
		this.state = s;
	}

	@Override
	public void init(DMASModel rm) {
		this.vrm = rm;
		rm.addAntAcceptor(this);
	}

}
