package rinde.sim.gradientfields.packages;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.gradientfields.AutoAdjustingFieldData;
import rinde.sim.gradientfields.DefaultFieldData;
import rinde.sim.gradientfields.model.virtual.FieldType;
import rinde.sim.gradientfields.model.virtual.FieldData;
import rinde.sim.gradientfields.model.virtual.GradientFieldAPI;
import rinde.sim.gradientfields.model.virtual.VirtualEntity;


public class PassengerAgent implements TickListener, SimulatorUser, VirtualEntity {

	private SimulatorAPI simulator;
	private final Passenger passenger;
	private double priority;
	private GradientFieldAPI api;
	private float fieldMultiplier;
	private final FieldData fieldData;
	
	public PassengerAgent(Passenger passenger){
		this.priority = 1;
		this.passenger = passenger;
		this.fieldData = new AutoAdjustingFieldData(FieldType.ATTRACTIVE, this, 1);
	}

	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}

	@Override
	public void tick(long currentTime, long timeStep) {
		if(this.passenger.delivered())
			this.simulator.unregister(this);
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {
		fieldMultiplier = 1000 /passenger.getDeadline() - passenger.getTravelTime() - currentTime;
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
		return !passenger.delivered() && passenger.needsPickUp();
	}

	@Override
	public Point getPosition() {
		return passenger.getPickupLocation();
	}

	@Override
	public FieldData getFieldData() {
		return new AutoAdjustingFieldData(FieldType.ATTRACTIVE, this, 1);
	}
	
	public float getMultiplier(){
		return fieldMultiplier;
	}

}