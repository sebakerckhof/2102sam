package rinde.sim.project.agent;

import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.MovingRoadUser;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadModel.PathProgress;
import rinde.sim.project.old.Package;


public class Taxi implements MovingRoadUser{

	private RoadModel rm;
	private Point startLocation;
	private String truckID;
	private double speed;
	
	private Passenger load;
	protected static final Logger LOGGER = LoggerFactory.getLogger(Taxi.class);
	
	public Taxi(String truckID, Point startLocation, double speed){
		this.truckID = truckID;
		this.startLocation = startLocation;
		this.speed = speed;
	}
	
	public String getTruckID(){
		return truckID;
	}
	
	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
		this.rm.addObjectAt(this, startLocation);
	}

	@Override
	public double getSpeed() {
		return speed;
	}
	
	public RoadModel getRoadModel(){
		return rm;
	}
	
	public PathProgress drive(Queue<Point> path, long time){
		return this.rm.followPath(this, path, time);
	}
	
	public Point getPosition(){
		return rm.getPosition(this);
	}
	
	public Point getLastCrossRoad(){
		return rm.getLastCrossRoad(this);
	}
	
	public boolean hasLoad(){
		return load != null;
	}
	
	public Passenger getLoad(){
		return this.load;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Taxi)
			return ((Taxi) o).truckID.equals(this.truckID);
		
		return false;
	}
	
	
	public boolean tryPickup(){
		if(load == null){
			Set<Passenger> passengers = rm.getObjectsAt(this, Passenger.class);
			if(!passengers.isEmpty()){
				Passenger p = (Passenger) passengers.toArray()[0];
				load = p;
				p.pickup();
				LOGGER.info(this.truckID + " picked up "+p);
				return true;
			}
		}
		return false;
	}
	
	public boolean tryDelivery(){
		if(load!=null){
			if(load.getDestination().equals(this.getPosition())){
				LOGGER.info(this.truckID + " delivered "+load);
				load.deposit();
				load = null;
				return true;
			}
		}
		return false;
	}
}
