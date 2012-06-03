package rinde.sim.project.agent;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.gradientfields.model.virtual.FieldData;
import rinde.sim.gradientfields.model.virtual.GradientFieldAPI;
import rinde.sim.gradientfields.packages.Package;
import rinde.sim.project.agent.dmas.AntAgent;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityDMAS;
import rinde.sim.project.agent.dmas.intention.IntentionPheromone;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.VirtualRoadModel;
import rinde.sim.project.model.VirtualRoadUser;

public class PackageAgent implements AntAcceptor, VirtualRoadUser, SimulatorUser, TickListener{

	private SimulatorAPI simulator;
	private VirtualRoadModel vrm;
	
	private Package myPackage;
	private double priority;

	private FeasibilityDMAS fDmas;

	public PackageAgent(){
		
	}
	

	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}


	@Override
	public void tick(long currentTime, long timeStep) {
		
	}


	@Override
	public void afterTick(long currentTime, long timeStep) {}


	@Override
	public Point getPosition() {
		return myPackage.getPickupLocation();
	}


	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
		
		this.fDmas = new FeasibilityDMAS();
		api.register(fDmas);
	}


	@Override
	public void init(VirtualRoadModel model) {
		this.vrm = model;
		model.addAntAcceptor(this);
	}


}
