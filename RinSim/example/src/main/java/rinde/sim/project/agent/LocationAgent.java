package rinde.sim.project.agent;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.DMASModel;
import rinde.sim.util.Tuple;

/**
 * Agent at a location (destination location)
 */
public class LocationAgent implements AntAcceptor, RoadUser{

	/**
	 * Unique identifier for this location
	 */
	private String locationID;
	
	/**
	 * Passenger for which this is the destination node
	 */
	private Point position;
	
	/**
	 * RoadModel API
	 */
	private RoadModel rm;
	

	/**
	 * Constu
	 * @param locationID	
	 * @param position		
	 */
	public LocationAgent(String locationID, Point position){
		this.position = position;
		this.locationID = locationID;
	}
	
	public Point getPosition(){
		return position;
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
		rm.addObjectAt(this, getPosition());
	}
	
	
	/**
	 * EXTRA METHODS
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof LocationAgent)
			return ((LocationAgent) o).locationID.equals(this.locationID);
		
		return false;
	}
	
	@Override
	public String toString() {
		return locationID;
	}

}
