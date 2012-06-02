package rinde.sim.project.model;

import rinde.sim.core.TickListener;

public abstract class DMAS implements TickListener{
	
	public static final int ADAPTABILITY_RATE = 1;
	public int sendInterval;
	protected long previousTime;
	
	public DMAS(int sendInterval){
		this.sendInterval = sendInterval;
	}
	
	public int getSendInterval(){
		return this.sendInterval;
	}
	
	public void setSendInterval(int sendInterval){
		this.sendInterval = sendInterval;
	}
	
	@Override
	public void tick(long currentTime, long timeStep) {
		//TODO do something better with the adaptability rate
		if(currentTime > (previousTime + sendInterval) * ADAPTABILITY_RATE){
			execute();
		}
		
	}
	
	public abstract void execute();

	@Override
	public void afterTick(long currentTime, long timeStep) {}
	
}
