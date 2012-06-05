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

	public static final int SPEED = 100; //TODO: convert
	
	private RoadModel rm;
	private Point startLocation;
	private String taxiID;
	private double speed;
	
	private Passenger passenger;
	protected static final Logger LOGGER = LoggerFactory.getLogger(Taxi.class);
	
	public Taxi(String taxiID, Point startLocation){
		this(taxiID, startLocation, SPEED);
	}
	
	public Taxi(String taxiID, Point startLocation, double speed){
		this.taxiID = taxiID;
		this.startLocation = startLocation;
		this.speed = speed;
	}
	
	public String getTaxiID(){
		return taxiID;
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
	
	public boolean hasPassenger(){
		return passenger != null;
	}
	
	public Passenger getPassenger(){
		return this.passenger;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Taxi)
			return ((Taxi) o).taxiID.equals(this.taxiID);
		
		return false;
	}
	
	
	public boolean tryPickup(){
		if(passenger == null){
			Set<Passenger> passengers = rm.getObjectsAt(this, Passenger.class);
			if(!passengers.isEmpty()){
				passenger = (Passenger) passengers.toArray()[0];
				passenger.pickup();
				LOGGER.info(this.taxiID + " picked up "+passenger);
				return true;
			}
		}
		return false;
	}
	
	public boolean tryDelivery(){
		if(passenger!=null){
			if(passenger.getDestination().equals(this.getPosition())){
				LOGGER.info(this.taxiID + " delivered "+passenger);
				passenger.deposit();
				passenger = null;
				return true;
			}
		}
		return false;
	}
}
