package rinde.sim.project.agent;

import java.util.Queue;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.communication.CommunicationAPI;
import rinde.sim.core.model.communication.Message;
import rinde.sim.project.agent.dmas.AntAgent;
import rinde.sim.project.agent.dmas.exploration.ExplorationDMAS;
import rinde.sim.project.agent.dmas.intention.IntentionDMAS;
import rinde.sim.project.agent.state.State;
import rinde.sim.project.agent.state.StateContext;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.VirtualRoadModel;
import rinde.sim.project.model.VirtualRoadUser;
import rinde.sim.project.physical.Truck;


public class TruckAgent implements StateContext, VirtualRoadUser, AntAcceptor, TickListener, SimulatorUser{

	private IntentionDMAS iDmas;
	private ExplorationDMAS eDmas;
	private Truck truck;
	private SimulatorAPI simulator;
	private VirtualRoadModel vrm;
	private Queue<Point> path;
	private State state;
	
	public TruckAgent(Truck truck){
		this.truck = truck;
	}
	
	public Truck getTruck(){
		return truck;
	}
	
	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
		
		iDmas = new IntentionDMAS();
		simulator.register(iDmas);
		eDmas = new ExplorationDMAS();
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
	public Point getPosition() {
		return truck.getPosition();
	}
	
	@Override
	public void init(VirtualRoadModel rm) {
		this.vrm = rm;
		rm.addAntAcceptor(this);
	}

}
