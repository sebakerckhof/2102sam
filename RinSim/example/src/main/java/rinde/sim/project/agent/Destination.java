package rinde.sim.project.agent;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.DMASModel;
import rinde.sim.util.Tuple;

/**
 * Agent at destination location
 */
public class Destination implements AntAcceptor, RoadUser{

	/**
	 * RoadModel API
	 */
	private RoadModel rm;
	
	/**
	 * Passenger for which this is the destination node
	 */
	private Passenger passenger;
	
	/**
	 * Request 
	 */
	private TransportRequest request;


	public Destination(Passenger passenger, TransportRequest request){
		this.passenger = passenger;
		this.request = request;
	}
	
	public Point getPosition(){
		return request.getDepositLocation();
	}
	
	public TransportRequest getRequest() {
		return request;
	}

	

	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}


	@Override
	public void init(DMASModel model) {
		model.addAntAcceptor(this);
	}


	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
		rm.addObjectAt(this, request.getDepositLocation());
		Tuple<Long,Long> data = rm.getTravelData(Taxi.SPEED, request.getPickupLocation(), request.getDepositLocation());
		request.setTravelDistance(data.getKey());
		request.setTravelTime(data.getValue());
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Destination)
			return passenger.equals(((Destination) o).passenger);
		return false;
	}
}
