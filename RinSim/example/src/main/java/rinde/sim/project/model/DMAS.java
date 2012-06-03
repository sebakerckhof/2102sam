package rinde.sim.project.model;


import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;

public abstract class DMAS implements VirtualRoadUser, SimulatorUser, TickListener{
	
	public static final int ADAPTABILITY_RATE = 1;
	protected int sendInterval;
	protected long previousTime;
	protected VirtualRoadModel model;
	protected SimulatorAPI simulator;
	
	public DMAS(int sendInterval){
		this.sendInterval = sendInterval;
	}
	
	@Override
	public void init(VirtualRoadModel model) {
		this.model = model;
	}
	
	@Override
	public void setSimulator(SimulatorAPI simulator){
		this.simulator = simulator;
	}

	public void setModel(VirtualRoadModel model) {
		this.model = model;
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
