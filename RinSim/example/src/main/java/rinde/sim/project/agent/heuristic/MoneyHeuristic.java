package rinde.sim.project.agent.heuristic;

import java.util.List;

import rinde.sim.project.agent.PackageAgent;

public class MoneyHeuristic implements Heuristic{

	public static final float startPrice = 5;
	public static final float kmPrice = 5;
	public static final float kmCost = 5;
	public static final float hourCost = 5;

	
	@Override
	public int execute(List<PackageAgent> path) {
		return 0;
	}
	
}