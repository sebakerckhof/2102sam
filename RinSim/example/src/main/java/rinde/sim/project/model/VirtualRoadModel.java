package rinde.sim.project.model;

import org.apache.commons.math.random.RandomGenerator;

import rinde.sim.core.TickListener;
import rinde.sim.core.model.Model;
import rinde.sim.core.model.communication.CommunicationModel;
import rinde.sim.project.agent.dmas.AntAgent;

public class VirtualRoadModel implements Model<AntAcceptor>
{

	public VirtualRoadModel(RandomGenerator generator) {
		
	}
	
	public void deploy(AntAgent a){
		//deploy with certain reliability
	}
	
	//smell & drop?


	@Override
	public boolean register(AntAcceptor element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unregister(AntAcceptor element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Class<AntAcceptor> getSupportedType() {
		return AntAcceptor.class;
	}

}
