package rinde.sim.project.model;

import rinde.sim.core.TickListener;

public abstract class DMAS implements TickListener{
	public static final int ADAPTABILITY_RATE = 1;
	
	@Override
	public void tick(long currentTime, long timeStep) {
		//do something with the adaptability rate
		execute();
	}
	
	public abstract void execute();

	@Override
	public void afterTick(long currentTime, long timeStep) {}
	
}
