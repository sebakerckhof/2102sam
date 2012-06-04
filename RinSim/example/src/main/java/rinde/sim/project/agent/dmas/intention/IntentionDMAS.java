package rinde.sim.project.agent.dmas.intention;

import java.util.List;

import rinde.sim.project.agent.PassengerAgent;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.DMAS;

public class IntentionDMAS extends DMAS{
	
	public static final int DEFAULT_INTERVAL = 500;
	
	protected List<PassengerAgent> intention;
	protected TaxiAgent agent;
	
	public IntentionDMAS(TaxiAgent agent){
		this(agent,DEFAULT_INTERVAL);
	}
	
	public IntentionDMAS(TaxiAgent agent, int interval){
		super(interval);
		this.agent = agent;
	}
	
	
	public void setIntention(List<PassengerAgent> packages){
		intention = packages;
	}
	

	@Override
	public void execute(){
		IntentionAnt a = new IntentionAnt();
		model.deploy(a);
	}
}
