package rinde.sim.project.physical;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.old.FeasibilityHolder;

//Merge with package??
public class DeliveryLocation implements RoadUser{

	protected RoadModel rm;
	
	private Point position;
	
	public DeliveryLocation(Point position){
		this.position = position;
	}

	public Point getPosition(){
		return rm.getPosition(this);
	}
	
	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
		model.addObjectAt(this, position);
	}
	
}


