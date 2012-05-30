package rinde.sim.project.agent;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.project.model.DMASUser;

public class PackageAgent implements TickListener, SimulatorUser, DMASUser{

	PickupDMASAgent pickup;
	DestinationDMASAgent destination;
	Package myPackage;
	
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

}
