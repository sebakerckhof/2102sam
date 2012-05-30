package rinde.sim.project.physical;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityHolder;

public class DeliveryLocation implements FeasibilityHolder, RoadUser{

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


