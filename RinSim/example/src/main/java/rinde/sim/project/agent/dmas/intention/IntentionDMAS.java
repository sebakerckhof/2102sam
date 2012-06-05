package rinde.sim.project.agent.dmas.intention;

import rinde.sim.project.agent.Plan;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.DMAS;
import rinde.sim.project.util.Utils;

/**
 * Intention DMAS sends intention ants for the plan given by the taxi agent
 */
public class IntentionDMAS extends DMAS{
	
	/**
	 * Default interval to send intention ants
	 */
	public static final long DEFAULT_INTERVAL = Utils.timeConverter.min(60).toTime();
	
	/**
	 * The plan we want to make reservations for
	 */
	protected Plan intention;
	
	/**
	 * The taxi agent
	 */
	protected TaxiAgent agent;
	
	/**
	 * Default interval to send feasibility ants
	 */
	protected IntentionAnt ant;
	
	/**
	 * {@link #IntentionDMAS(TaxiAgent, long)}
	 * For default interval
	 */
	public IntentionDMAS(TaxiAgent agent){
		this(agent, DEFAULT_INTERVAL * (1));
	}
	
	/**
	 * Constructor
	 * @param agent		The taxi agent
	 * @param interval	Interval to send intention ants
	 * 
	 * {@link DMAS#DMAS(long)}
	 */
	public IntentionDMAS(TaxiAgent agent, long interval){
		super(interval);
		this.agent = agent;
	}
	
	/**
	 * Change the plan
	 * @param plan	Plan to make reservations for
	 */
	public void setIntention(Plan plan){
		intention = plan;
	}
	

	/**
	 * {@link DMAS#execute(long, long)}
	 * Send intention ant
	 */
	@Override
	public void execute(long currentTime, long timeStep){
		if(intention != null && intention.size() > 0){
			IntentionAnt ant = new IntentionAnt(intention);
			simulator.register(ant);
		}
	}

	/**
	 * Get report from intentionAnt
	 * @param intentionAnt
	 */
	public void report(IntentionAnt intentionAnt) {
		this.ant = intentionAnt;
	}
}
