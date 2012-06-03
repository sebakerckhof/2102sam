package rinde.sim.project.agent.heuristic;

import java.util.List;

import rinde.sim.project.agent.PackageAgent;

public interface Heuristic {
	public int execute(List<PackageAgent> path);
}
