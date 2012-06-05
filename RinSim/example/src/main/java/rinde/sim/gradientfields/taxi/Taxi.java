package rinde.sim.gradientfields.taxi;

import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.MovingRoadUser;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadModel.PathProgress;
import rinde.sim.gradientfields.packages.Passenger;


public class Taxi implements MovingRoadUser{

	public static final double SPEED = 50;
	
	private RoadModel rm;
	private Point startLocation;
	private String taxiID;
	private double speed;
	
	private Passenger load;
	protected static final Logger LOGGER = LoggerFactory.getLogger(Taxi.class);
	
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
	
	public boolean hasLoad(){
		return load != null;
	}
	
	public Passenger getLoad(){
		return this.load;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Taxi)
			return ((Taxi) o).taxiID.equals(this.taxiID);
		
		return false;
	}
	
	
	public boolean tryPickup(){
		if(load == null){
			Set<Passenger> passengers = rm.getObjectsAt(this, Passenger.class);
			if(!passengers.isEmpty()){
				Passenger p = (Passenger) passengers.toArray()[0];
				load = p;
				p.pickup();
				LOGGER.info(this.taxiID + " picked up "+p);
				return true;
			}
		}
		return false;
	}
	
	public boolean tryDelivery(){
		if(load!=null){
			if(load.getDeliveryLocation().equals(this.getPosition())){
				LOGGER.info(this.taxiID + " delivered "+load);
				load.deliver();
				load = null;
				return true;
			}
		}
		return false;
	}
}