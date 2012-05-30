package rinde.sim.project.agent;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.communication.CommunicationAPI;
import rinde.sim.core.model.communication.Message;
import rinde.sim.project.agent.dmas.AntAgent;
import rinde.sim.project.model.DMASUser;

public class TruckAgent implements DMASUser, TickListener, SimulatorUser{

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
	public void setCommunicationAPI(CommunicationAPI api) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getReliability() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void receive(Message message){
		if(message instanceof AntAgent){
			((AntAgent) message).visit(this);
		}else{
			throw new IllegalArgumentException("");
		}
	}

}
