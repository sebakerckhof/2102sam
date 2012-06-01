package rinde.sim.project.agent;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.communication.CommunicationAPI;
import rinde.sim.core.model.communication.Message;
import rinde.sim.project.agent.dmas.AntAgent;
import rinde.sim.project.agent.dmas.exploration.ExplorationDMAS;
import rinde.sim.project.agent.dmas.intention.IntentionDMAS;
import rinde.sim.project.agent.state.StateContext;
import rinde.sim.project.model.AntAcceptor;


public class TruckAgent implements StateContext, AntAcceptor, TickListener, SimulatorUser{

	IntentionDMAS iDmas = new IntentionDMAS();
	ExplorationDMAS eDmas = new ExplorationDMAS();
	
	@Override
	public void setSimulator(SimulatorAPI api) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(long currentTime, long timeStep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}

}
