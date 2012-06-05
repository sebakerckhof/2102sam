package rinde.sim.contractnets;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import rinde.sim.core.graph.Connection;
import rinde.sim.core.graph.Graph;
import rinde.sim.core.graph.MultiAttributeEdgeData;
import rinde.sim.core.graph.Point;
import rinde.sim.ui.renderers.Renderer;

public class SpeedRenderer implements Renderer {
	
	private Graph<MultiAttributeEdgeData> graph;

	public SpeedRenderer(Graph<MultiAttributeEdgeData> graph) {
		this.graph = graph;
	}

	@Override
	public void render(GC gc, double xOrigin, double yOrigin, double minX,
			double minY, double scale) {
		Color red = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		Color black = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
		CoordinateConverter conv = new CoordinateConverter(xOrigin, yOrigin, minX, minY, scale);
		List<Connection<MultiAttributeEdgeData>> connections = graph.getConnections();
		for (Connection<MultiAttributeEdgeData> c: connections) {
			Point start = conv.toScreen(c.from);
			Point end = conv.toScreen(c.to);
			double speed = c.getEdgeData().getMaxSpeed();
			if (Double.isNaN(speed)) {
				gc.setForeground(red);
			} else {
				gc.setForeground(black);
			}
			gc.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);
		}
	}

}
