package rinde.sim.gradientfields.model;

import java.util.List;

import rinde.sim.core.graph.EdgeData;
import rinde.sim.core.graph.Graph;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;

public class ExtendedRoadModel extends RoadModel{

	public ExtendedRoadModel(Graph<? extends EdgeData> graph) {
		super(graph);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Convenience method for gradient field extension
	 *
	 * @param from
	 * @param to
	 * 
	 * @return
	 */
	public float getTravelLength(Point from, Point to){
		List<Point> points = getShortestPathTo(from, to);
		float distance = 0;
		Point previous = from;
		for(Point p : points){
			distance += Point.distance(previous, p);
			previous = p;
		}
		return distance;
	}
}
