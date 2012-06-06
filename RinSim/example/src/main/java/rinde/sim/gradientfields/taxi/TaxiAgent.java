package rinde.sim.gradientfields.taxi;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.gradientfields.model.virtual.*;
import rinde.sim.gradientfields.DefaultFieldData;
import rinde.sim.gradientfields.packages.*;


public class TaxiAgent implements TickListener, SimulatorUser, VirtualEntity {

	private SimulatorAPI simulator;
	private Queue<Point> path;
	private Taxi taxi;
	
	private boolean isEmitting;
	private GradientFieldAPI api;
	private FieldData fieldData; 
	
	public TaxiAgent(Taxi taxi, int timerInterval){
		this.isEmitting = true;
		this.taxi = taxi;
		this.fieldData = new DefaultFieldData(FieldType.REPULSIVE, this, 1);
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
						temp = ((PassengerAgent) f.getFieldData().getEntity()).getPriority() * FieldType.ATTRACTIVE.getStrength();
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
		//TODO: transform the mess into order with state pattern (if we feel like it)
		
		if(path == null || path.isEmpty()){ 
			if(!taxi.hasLoad() && taxi.tryPickup()){ //haal een pakketje op
				this.isEmitting = false; //terwijl je naar je bestemming rijdt is het niet nodig een field uit te sturen
				this.path = new LinkedList<Point>(taxi.getRoadModel().getShortestPathTo(taxi, taxi.getLoad().getDeliveryLocation()));
			}else{
				if(taxi.hasLoad() && taxi.tryDelivery()) //lever een pakketje af
					this.isEmitting = true; //Je bent op zoek naar een nieuw pakketje, hou andere trucks dus weg met je gradient field
				
				Field f = this.getStrongestField();
				if(f != null){ // Rij naar het sterkste gradient field
					this.path = new LinkedList<Point>(taxi.getRoadModel().getShortestPathTo(taxi, f.getFieldData().getEntity().getPosition()));
				}else{ //als er in de buurt geen gradient field waarneembaar is, rij dan enkele stappen naar in een willekeurige richting, tot je wel een field waarneemt
					Point destination = taxi.getRoadModel().getGraph().getRandomNode(simulator.getRandomGenerator());
					LinkedList<Point> fullpath = new LinkedList<Point>(taxi.getRoadModel().getShortestPathTo(taxi, destination));
					this.path = new LinkedList<Point>();
					for(int i = 0; i < Math.max(1, fullpath.size()); i++){
						path.add(fullpath.poll());
					}
				}
			}
		}
		
		//berij het vastgelegde pad
		taxi.drive(path, timeStep);
		
		
		
		
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {}

	@Override
	public void init(GradientFieldAPI gfapi) {
		this.api = gfapi;
	}

	@Override
	public boolean isEmitting() {
		return isEmitting;
	}

	@Override
	public Point getPosition() {
		return this.taxi.getPosition();
	}

	@Override
	public FieldData getFieldData() {
		return fieldData;
	}

}