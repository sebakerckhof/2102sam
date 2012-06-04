package rinde.sim.project.agent;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.gradientfields.model.virtual.FieldData;
import rinde.sim.gradientfields.model.virtual.GradientFieldAPI;
import rinde.sim.gradientfields.packages.Package;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityDMAS;
import rinde.sim.project.agent.dmas.intention.IntentionPheromone;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.DMASUser;

public class PassengerAgent implements SimulatorUser, TickListener{

	private SimulatorAPI simulator;
	private Passenger passenger;
	
	private FeasibilityDMAS fDmas;

	public PassengerAgent(Passenger passenger){
		this.passenger = passenger;
	}
	
	public Passenger getPassenger(){
		return this.passenger;
	}
	

	@Override
	public void tick(long currentTime, long timeStep) {}


	@Override
	public void afterTick(long currentTime, long timeStep) {}

	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
		
		this.fDmas = new FeasibilityDMAS(this);
		api.register(fDmas);
	}


	
	@Override
	public boolean equals(Object o){
		if(o instanceof PassengerAgent)
			return passenger.equals(((PassengerAgent) o).passenger);
		return false;
	}


}
