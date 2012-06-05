package rinde.sim.contractnets;

import java.util.Set;

import org.eclipse.swt.graphics.GC;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.lab.common.packages.Package;
import rinde.sim.ui.renderers.Renderer;

public class PackageIDRenderer implements Renderer {
	
	private RoadModel roads;
	
	public PackageIDRenderer(RoadModel model) {
		this.roads = model;
	}

	@Override
	public void render(GC gc, double xOrigin, double yOrigin, double minX,
			double minY, double scale) {
		Set<RoadUser> users = roads.getObjects();
		for (RoadUser roadUser : users) {
			if (roadUser instanceof rinde.sim.lab.common.packages.Package) {
				Package p = (Package) roadUser;
				Point pl = p.getPickupLocation();
				CoordinateConverter cc = new CoordinateConverter(xOrigin, yOrigin, minX, minY, scale);
				Point pls = cc.toScreen(pl);
				gc.drawText(p.getPackageID(), (int)pls.x, (int)pls.y, true);
			}
		}
	}

}
