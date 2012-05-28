package rinde.sim.lab.session2.gradient_field_exercise.packages;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.core.model.virtual.FieldData;
import rinde.sim.core.model.virtual.FieldType;
import rinde.sim.core.model.virtual.GradientFieldAPI;
import rinde.sim.core.model.virtual.VirtualEntity;
import rinde.sim.lab.session2.gradient_field_exercise.FieldDataImpl;


public class PackageAgent implements TickListener, SimulatorUser, VirtualEntity {

	private SimulatorAPI simulator;
	private Package myPackage;
	private double priority;
	private GradientFieldAPI api;
	private FieldData fieldData; 
	
	
	public PackageAgent(Package myPackage){
		this.priority = 1;
		this.myPackage = myPackage;
		this.fieldData = new FieldDataImpl(FieldType.ATTRACTIVE, this);
	}
	
	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}

	@Override
	public void tick(long currentTime, long timeStep) {
		// TODO Auto-generated method stub
		if(this.myPackage.delivered())
			this.simulator.unregister(this);
		
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {
		// TODO Auto-generated method stub
		
	}

	public double getPriority() {
		return priority;
	}

	public void setPriority(double priority) {
		this.priority = priority;
	}

	@Override
	public void init(GradientFieldAPI api) {
		this.api = api;
	}

	@Override
	public boolean isEmitting() {
		return !myPackage.delivered() && myPackage.needsPickUp();
	}

	@Override
	public Point getPosition() {
		return myPackage.getPickupLocation();
	}

	@Override
	public FieldData getFieldData() {
		return fieldData;
	}

}
