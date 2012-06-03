package rinde.sim.project.agent;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.project.agent.dmas.AntAgent;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityDMAS;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityPheromone;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.VirtualRoadModel;
import rinde.sim.project.model.VirtualRoadUser;
import rinde.sim.project.old.FeasibilityHolder;
import rinde.sim.project.old.PheromoneInfrastructure;

public class DestinationAgent implements AntAcceptor, SimulatorUser, VirtualRoadUser, TickListener{

	private VirtualRoadModel vrm;
	private SimulatorAPI simulator;

	public DestinationAgent(){
		
	}
	

	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}


	@Override
	public void tick(long currentTime, long timeStep) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void afterTick(long currentTime, long timeStep) {}


	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void init(VirtualRoadModel rm) {
		this.vrm = rm;
		rm.addAntAcceptor(this);
	}


	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}



}
