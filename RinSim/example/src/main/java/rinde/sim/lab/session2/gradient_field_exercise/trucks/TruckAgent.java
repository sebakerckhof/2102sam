package rinde.sim.lab.session2.gradient_field_exercise.trucks;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.virtual.Field;
import rinde.sim.core.model.virtual.FieldData;
import rinde.sim.core.model.virtual.FieldType;
import rinde.sim.core.model.virtual.GradientFieldAPI;
import rinde.sim.core.model.virtual.VirtualEntity;
import rinde.sim.lab.session2.gradient_field_exercise.FieldDataImpl;
import rinde.sim.lab.session2.gradient_field_exercise.packages.PackageAgent;


public class TruckAgent implements TickListener, SimulatorUser, VirtualEntity {

	private SimulatorAPI simulator;
	private Queue<Point> path;
	private Truck truck;
	
	private boolean isEmitting;
	private GradientFieldAPI api;
	private FieldData fieldData; 
	
	public TruckAgent(Truck truck, int timerInterval){
		this.isEmitting = true;
		this.truck = truck;
		this.fieldData = new FieldDataImpl(FieldType.REPULSIVE, this);
	}
	
	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}
	
	
	/**
	 * Bereken het sterkste waarneembare gradient field
	 * @return
	 */
	public Field getStrongestField(){
		Collection<Field> fields = api.getFieldsFrom(this);
		Field strongest = null;
		double pull = 0;
		
		for(Field fj : fields){
			double current = 0;
			
			for(Field f : fields){
				
				double temp = 0;
				switch(f.getFieldData().getType()){
					case ATTRACTIVE:
						temp = ((PackageAgent) f.getFieldData().getEntity()).getPriority() * FieldType.ATTRACTIVE.getStrength();
						break;
					case REPULSIVE:
						temp = -1 * FieldType.REPULSIVE.getStrength();
						break;
				}
				current += temp / f.getDistance();
			}
			
			if(current > pull){
				pull = current;
				strongest = fj;
			}
		}
		return strongest;
	}

	/**
	 * Gradient field agent
	 */
	@Override
	public void tick(long currentTime, long timeStep) {
		
		if(path == null || path.isEmpty()){ 
			if(!truck.hasLoad() && truck.tryPickup()){ //haal een pakketje op
				this.isEmitting = false; //terwijl je naar je bestemming rijdt is het niet nodig een field uit te sturen
				this.path = new LinkedList<Point>(truck.getRoadModel().getShortestPathTo(truck, truck.getLoad().getDeliveryLocation()));
			}else{
				if(truck.hasLoad() && truck.tryDelivery()) //lever een pakketje af
					this.isEmitting = true; //Je bent op zoek naar een nieuw pakketje, hou andere trucks dus weg met je gradient field
				
				Field f = this.getStrongestField();
				if(f != null){ // Rij naar het sterkste gradient field
					this.path = new LinkedList<Point>(truck.getRoadModel().getShortestPathTo(truck, f.getFieldData().getEntity().getPosition()));
				}else{ //als er in de buurt geen gradient field waarneembaar is, rij dan enkele stappen naar in een willekeurige richting, tot je wel een field waarneemt
					Point destination = truck.getRoadModel().getGraph().getRandomNode(simulator.getRandomGenerator());
					LinkedList<Point> fullpath = new LinkedList<Point>(truck.getRoadModel().getShortestPathTo(truck, destination));
					this.path = new LinkedList<Point>();
					for(int i = 0; i < Math.max(1, fullpath.size()); i++){
						path.add(fullpath.poll());
					}
				}
			}
		}else{ //berij het vastgelegde pad
			truck.drive(path, timeStep);
		}
		
		
		
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GradientFieldAPI api) {
		this.api = api;
	}

	@Override
	public boolean isEmitting() {
		return isEmitting;
	}

	@Override
	public Point getPosition() {
		return this.truck.getPosition();
	}

	@Override
	public FieldData getFieldData() {
		return fieldData;
	}

}
