package rinde.sim.project.model;

import rinde.sim.core.graph.Point;
import rinde.sim.project.agent.dmas.AntAgent;

public interface AntAcceptor {
	public void accept(AntAgent a);
	public Point getPosition();
}
