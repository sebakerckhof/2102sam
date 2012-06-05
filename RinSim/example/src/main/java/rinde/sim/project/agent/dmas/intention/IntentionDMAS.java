package rinde.sim.project.agent.dmas.intention;

import java.util.List;

import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.PassengerAgent;
import rinde.sim.project.agent.Plan;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.DMAS;
import rinde.sim.project.util.Utils;

public class IntentionDMAS extends DMAS{
	
	public static final long DEFAULT_INTERVAL = Utils.timeConverter.min(60).toTime();
	
	protected Plan intention;
	protected TaxiAgent agent;
	protected IntentionAnt ant;
	
	public IntentionDMAS(TaxiAgent agent){
		this(agent,DEFAULT_INTERVAL);
	}
	
	public IntentionDMAS(TaxiAgent agent, long interval){
		super(interval);
		this.agent = agent;
	}
	
	
	public void setIntention(Plan plan){
		intention = plan;
	}
	

	@Override
	public void execute(long currentTime, long timeStep){
		if(intention != null && intention.size() > 0){
			IntentionAnt a = new IntentionAnt(intention);
			environment.deploy(a);
		}
	}

	public void report(IntentionAnt intentionAnt) {
		this.ant = intentionAnt;
	}
}
