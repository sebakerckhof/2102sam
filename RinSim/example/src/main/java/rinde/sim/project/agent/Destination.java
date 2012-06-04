package rinde.sim.project.agent;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityDMAS;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityPheromone;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.DMASUser;
import rinde.sim.project.old.FeasibilityHolder;
import rinde.sim.project.old.PheromoneInfrastructure;

public class Destination implements AntAcceptor, RoadUser, SimulatorUser{

	private DMASModel environment;
	private RoadModel rm;
	private SimulatorAPI simulator;
	private Passenger passenger;
	
	private Point location;


	public Destination(Passenger passenger, Point location){
		this.passenger = passenger;
		this.location = location;
	}
	
	public Passenger getPassenger(){
		return this.passenger;
	}

	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}


	@Override
	public void init(DMASModel model) {
		this.environment = model;
		model.addAntAcceptor(this);
	}


	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}


	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
		rm.addObjectAt(this, location);
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Destination)
			return passenger.equals(((Destination) o).passenger);
		return false;
	}
}
