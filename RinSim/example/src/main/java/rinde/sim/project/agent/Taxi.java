package rinde.sim.project.agent;

import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.MovingRoadUser;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadModel.PathProgress;

/**
 * Taxi
 * This class represents a taxi
 */
public class Taxi implements MovingRoadUser{

	/**
	 * Maximum driving speed of taxi vehicles
	 */
	public static final int SPEED = 100; //TODO: convert
	
	/**
	 * RoadModel on which taxi 'drives'
	 */
	private RoadModel rm;
	
	/**
	 * Initial location of taxi
	 */
	private Point startLocation;
	
	/**
	 * Taxi unique identifier
	 */
	private String taxiID;
	
	/**
	 * Maximum speed for this taxi
	 */
	private double speed;
	
	/**
	 * Current passenger
	 */
	private Passenger passenger;
	
	/**
	 * Logger
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(Taxi.class);
	
	/**
	 * Constructor
	 * 
	 * {@link #Taxi(String, Point, double)}
	 */
	public Taxi(String taxiID, Point startLocation){
		this(taxiID, startLocation, SPEED);
	}
	
	/**
	 * Constructor
	 * 
	 * @param taxiID		Taxi unique identifier
	 * @param startLocation	Initial location of taxi
	 * @param speed			Maximum speed for this taxi
	 */
	public Taxi(String taxiID, Point startLocation, double speed){
		this.taxiID = taxiID;
		this.startLocation = startLocation;
		this.speed = speed;
	}
	
	/**
	 * TAXI LOGIC
	 */
	
	/**
	 * Drive
	 * @param path	Path to drive
	 * @param time	Time time to drive on path
	 * @return {@link RoadModel#followPath(MovingRoadUser, Queue, long)}
	 */
	public PathProgress drive(Queue<Point> path, long time){
		return this.rm.followPath(this, path, time);
	}
	
	/**
	 * Try picking up passenger at current location
	 * @param 	p	Passenger to pick up
	 * @return 	true if passenger could be picked up, false otherwise
	 */
	public boolean tryPickup(Passenger p){
		if(passenger == null){
			Set<Passenger> passengers = rm.getObjectsAt(this, Passenger.class);
			if(!passengers.isEmpty() && passengers.contains(p)){
				passenger = p;
				passenger.pickup();
				LOGGER.info(this.taxiID + " picked up "+passenger);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Deliver passenger
	 * @return true if passenger could be deposited, false otherwise
	 */
	public boolean tryDeposit(){
		if(passenger!=null){
			if(passenger.getRequest().getDestination().getPosition().equals(this.getPosition())){
				LOGGER.info(this.taxiID + " delivered "+passenger);
				passenger.deposit();
				passenger = null;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * GETTERS & SETTERS
	 */
	public String getTaxiID(){
		return taxiID;
	}
	
	@Override
	public double getSpeed() {
		return speed;
	}
	
	public RoadModel getRoadModel(){
		return rm;
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
	
	/**
	 * MODEL INITIALIZATION
	 */
	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
		this.rm.addObjectAt(this, startLocation);
	}

	/**
	 * EXTRA METHODS
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof Taxi)
			return ((Taxi) o).taxiID.equals(this.taxiID);
		
		return false;
	}
	
	

}
