package rinde.sim.contractnets;

import rinde.sim.core.graph.Point;

public class CoordinateConverter {

	private double xOrigin;
	private double yOrigin;
	private double minX;
	private double minY;
	private double scale;

	public CoordinateConverter(double xOrigin, double yOrigin, double minX,
			double minY, double scale) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.minX = minX;
		this.minY = minY;
		this.scale = scale;
	}
	
	public Point toScreen(Point p) {
		return new Point(xOrigin + (p.x - minX) * scale, yOrigin + (p.y - minY) * scale);
	}
}
