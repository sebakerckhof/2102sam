package rinde.sim.project.model;


import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;

public abstract class DMAS implements DMASUser, SimulatorUser, TickListener{
	

	protected long sendInterval;
	protected long previousTime;
	protected DMASModel environment;
	protected SimulatorAPI simulator;
	
	public DMAS(long sendInterval){
		this.sendInterval = sendInterval;
	}
	
	@Override
	public void init(DMASModel model) {
		this.environment = model;
	}
	
	@Override
	public void setSimulator(SimulatorAPI simulator){
		this.simulator = simulator;
	}

	public long getSendInterval(){
		return this.sendInterval;
	}
	
	public void setSendInterval(int sendInterval){
		this.sendInterval = sendInterval;
	}
	
	@Override
	public void tick(long currentTime, long timeStep) {
		//TODO do something better with the adaptability rate
		if(currentTime > (previousTime + sendInterval) * (1 / DMASModel.ADAPTABILITY_RATE)){
			execute(currentTime, timeStep);
		}
		
	}
	
	public abstract void execute(long currentTime, long timeStep);

	@Override
	public void afterTick(long currentTime, long timeStep) {}
	
}
