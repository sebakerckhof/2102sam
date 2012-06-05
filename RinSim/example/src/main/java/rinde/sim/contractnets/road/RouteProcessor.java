package rinde.sim.contractnets.road;

import java.util.LinkedList;
import java.util.List;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.MovingRoadUser;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadModel.PathProgress;

public class RouteProcessor {
	private double speed;
	private RoadModel roads;
	
	private class Buggy implements MovingRoadUser {
		@Override
		public void initRoadUser(RoadModel model) {
		}

		@Override
		public double getSpeed() {
			return speed;
		}
	}
	
	public RouteProcessor(RoadModel model, double speed) {
		this.roads = model;
		this.speed = speed;
	}

	public double getDistance(Point from, Point to) {
		return getDistance(from, roads.getShortestPathTo(from, to));
	}
	
	public double getDistance(Point from, List<Point> path) {
		Buggy b = new Buggy();
		roads.addObjectAt(b, from);
		LinkedList<Point> p = new LinkedList<Point>(path);
		PathProgress pp = roads.followPath(b, p, Long.MAX_VALUE);
		roads.removeObject(b);
		return pp.distance;
	}
	
	public long getTravelTime(Point from, Point to) {
		return getTravelTime(from, roads.getShortestPathTo(from, to));
	}
	
	public long getTravelTime(Point from, List<Point> path) {
		Buggy b = new Buggy();
		roads.addObjectAt(b, from);
		LinkedList<Point> p = new LinkedList<Point>(path);
		PathProgress pp = roads.followPath(b, p, Long.MAX_VALUE);
		roads.removeObject(b);
		return pp.time;
	}
}
