package rinde.sim.project.agent.dmas.intention;

import java.util.List;

import rinde.sim.project.Utils;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.PassengerAgent;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.DMAS;

public class IntentionDMAS extends DMAS{
	
	public static final long DEFAULT_INTERVAL = Utils.minutesToMicroSeconds(50);
	
	protected List<Passenger> intention;
	protected TaxiAgent agent;
	protected IntentionAnt ant;
	
	public IntentionDMAS(TaxiAgent agent){
		this(agent,DEFAULT_INTERVAL);
	}
	
	public IntentionDMAS(TaxiAgent agent, long interval){
		super(interval);
		this.agent = agent;
	}
	
	
	public void setIntention(List<Passenger> passengers){
		intention = passengers;
	}
	

	@Override
	public void execute(){
		
		IntentionAnt a = new IntentionAnt();
		environment.deploy(a);
	}

	public void report(IntentionAnt intentionAnt) {
		this.ant = intentionAnt;
	}
}
