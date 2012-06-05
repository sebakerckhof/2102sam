package rinde.sim.project.agent;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityDMAS;
import rinde.sim.project.agent.dmas.intention.ReservationPheromoneHandler;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.DMASModel;

/**
 * Passenger Agent
 * 
 * This agent uses a feasibility MAS to spread information to destination agents of other passengers to spread awareness
 */
public class Passenger implements AntAcceptor, RoadUser, SimulatorUser{
	/**
	 * Unique identifier for passenger
	 */
	public final String passengerID;

	/**
	 * Whether this passenger has been picked up
	 */
	private boolean pickedUp;
	
	/**
	 * Whether this passenger has been deposited
	 */
	private boolean deposited;
	
	/**
	 * The request this passenger has
	 */
	private final TransportRequest request;
	
	/**
	 * The destination agent
	 */
	private final Destination destination;
	
	/**
	 * Feasiblity DMAS that spread passenger information
	 */
	private FeasibilityDMAS fDmas;
	
	/**
	 * API's
	 */
	private SimulatorAPI simulator;
	private DMASModel environment; //TODO: do we need this one here??
	private RoadModel rm;

	/**
	 * Constructor
	 * @param passengerID	Unique identifier for passenger
	 * @param request		The request this passenger has
	 */
	public Passenger(String passengerID, TransportRequest request) {
		this.passengerID = passengerID;

		this.pickedUp = false;
		this.deposited = false;
		
		this.request = request;
		
		this.destination = new Destination(this,request);
	}
	
	/**
	 * PASSENGER LOGIC
	 */
	
	/**
	 * Enter taxi
	 */
	public void pickup(){
		this.pickedUp = true;
		//TODO
	}
	
	/**
	 * Leave taxi
	 */
	public void deposit(){
		this.deposited = true;
		//TODO
	}
	
	
	/**
	 * {@link AntAcceptor#accept(AntAgent)}
	 */
	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}
	
	/**
	 * GETTERS & SETTERS
	 */
	public Point getPosition() {
		return request.getPickupLocation();
	}
	
	public TransportRequest getRequest(){
		return this.request;
	}
	
	public boolean needsPickUp(){
		return !pickedUp;
	}

	public boolean deposited(){
		return deposited;
	}
	
	public String getPackageID(){
		return passengerID;
	}
	
	public Destination getDestination(){
		return destination;
	}
	

	/**
	 * API BINDING
	 */

	/**
	 * {@link AntAcceptor#init(DMASModel)}
	 */
	@Override
	public void init(DMASModel model) {
		this.environment = model;
		environment.addAntAcceptor(this, new ReservationPheromoneHandler());
	}

	/**
	 * {@link RoadUser#initRoadUser(RoadModel)}
	 */
	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
		rm.addObjectAt(this, this.request.getPickupLocation());
	}
	
	/**
	 * {@link SimulatorUser#setSimulator(SimulatorAPI)}
	 */
	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
		simulator.register(destination);
		
		this.fDmas = new FeasibilityDMAS(this);
		api.register(fDmas);
	}
	
	/**
	 * EXTRA METHODS
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof Passenger)
			return ((Passenger) o).passengerID.equals(this.passengerID);
		
		return false;
	}
	
	@Override
	public String toString() {
		return passengerID;
	}


}
