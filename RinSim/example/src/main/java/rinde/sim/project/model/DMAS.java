package rinde.sim.project.model;


import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;

/**
 * Class for a Delegate MAS
 */
public abstract class DMAS implements DMASUser, SimulatorUser, TickListener{
	
	/**
	 * The frequency (in ms) that determines when the DMAS sends out its ants
	 */
	protected long sendInterval;
	
	
	/**
	 * The last time ants were send
	 */
	protected long previousTime;
	
	
	/**
	 * The DMASModel
	 */
	protected DMASModel environment;
	
	
	/**
	 * The simulator
	 */
	protected SimulatorAPI simulator;
	
	/**
	 * Constructor
	 * @param sendInterval	The frequency (in ms) that determines when the DMAS sends out its ants
	 */
	public DMAS(long sendInterval){
		this.sendInterval = sendInterval;
	}
	
	/**
	 * {@link TickListener#tick(long, long)}
	 */
	@Override
	public void tick(long currentTime, long timeStep) {

		//Check if interval has passed to run the dmas
		if(currentTime > (previousTime + (sendInterval / DMASModel.ADAPTABILITY_RATE))){
			execute(currentTime, timeStep);
		}
		previousTime = currentTime;
		
	}
	
	/**
	 * Run the delegate MAS
	 * @param currentTime	Current time
	 * @param timeStep		Size of time step
	 */
	public abstract void execute(long currentTime, long timeStep);

	/**
	 * {@link TickListener#afterTick(long, long)}
	 */
	@Override
	public void afterTick(long currentTime, long timeStep) {}
	
	/**
	 * GETTERS & SETTERS
	 */
	
	public long getSendInterval(){
		return this.sendInterval;
	}
	
	public void setSendInterval(int sendInterval){
		this.sendInterval = sendInterval;
	}
	
	/**
	 * MODEL INIT
	 */
	
	/**
	 * {@link DMASUser#init(DMASModel)}
	 */
	@Override
	public void init(DMASModel model) {
		this.environment = model;
	}
	
	/**
	 * {@link SimulatorUser#setSimulator(SimulatorAPI)}
	 */
	@Override
	public void setSimulator(SimulatorAPI simulator){
		this.simulator = simulator;
	}
	
}
