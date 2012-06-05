package rinde.sim.project.agent.dmas.exploration;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.Plan;
import rinde.sim.project.agent.Taxi;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.agent.TransportRequest;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityPheromone;
import rinde.sim.project.agent.dmas.intention.IntentionPheromone;
import rinde.sim.project.agent.heuristic.MoneyHeuristic;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.util.Tuple;

/**
 * Ant to explore paths for taxi's
 * 
 * This ant explores by fetching feasibility information on destination nodes and cloning towards the passengers who are most in need of handling
 * It checks whether paths are possible and reports the path to the taxi agent by leaving an exploration pheromone
 */
public class ExplorationAnt extends AntAgent{

	/**
	 * How many clones to make at each destination
	 */
	public static final int SPLIT = 3;
	
	/**
	 * Plan of passengers to pick-up
	 */
	protected Plan plan;
	

	/**
	 * The estimated arrival time in the current visiting node
	 */
	protected long estimatedArrival;
	
	/**
	 * The start time when the ant was send
	 */
	protected long startTime;
	
	/**
	 * The taxi that sends out this agent
	 */
	protected Taxi taxi;
	
	/**
	 * Data about the next passenger
	 * Taken from feasibility data
	 */
	protected Tuple<Long,Long> nextData;
	
	/**
	 * Constructor
	 * @param taxi		The taxi that sends out this agent
	 * @param startTime	The start time when the ant was send
	 * @param start		The node to visit first
	 * @param hops		The amount of nodes to visit
	 */
	public ExplorationAnt(Taxi taxi, long startTime, AntAcceptor start, int hops) {
		super(start, hops);
		this.taxi = taxi;
		this.startTime = startTime;
	}

	
	/**
	 * {@link #next()}
	 * Returns to first node directly when finished
	 */
	@Override
	public AntAcceptor next(){
		if(!forward())
			return path.get(0);
		else
			return super.next();
	}
	
	/**
	 * VISITS
	 */
	
	/**
	 * {@link #visit(TaxiAgent)}
	 * When visiting the same taxi agent as the sender, report the explored path by leaving an exploration pheromone
	 */
	@Override
	public void visit(TaxiAgent t){
		//check if home
		if(t.getTaxi().equals(taxi)){
			//report found path
			environment.drop(t, new ExplorationPheromone(plan)); 
		}
		
		//terminate ant
		terminate(); 
	}
	
	/**
	 * {@link #visit(Passenger)}
	 * When visiting a passenger, check whether it still needs to be picked-up and no other taxi's already plan on picking it up
	 * If we can't add this passenger, we terminate the ant
	 */
	@Override
	public void visit(Passenger p) {
		
		//Smell for other taxi's intentions on this passenger
		List<IntentionPheromone> pheromones = environment.smell(p, IntentionPheromone.class);
		
		//If we don't come from a destination ant, this is the first passenger for this taxi
		if(nextData == null){
			nextData = rm.getTravelData(taxi.getSpeed(), taxi.getPosition(), p.getPosition());
		}
		
		updateEstimatedArrival(nextData.getValue());
		
		//Add possible waiting time
		if(estimatedArrival < p.getRequest().getStart()){
			nextData.setValue(nextData.getValue() + (p.getRequest().getStart() - estimatedArrival));
			estimatedArrival = p.getRequest().getStart();
		}
		
		//If we can pick up this agent & no other taxi's intend to pick it up already, add this passenger
		if(pheromones.isEmpty() && feasible(p.getRequest()) && p.needsPickUp()){
			//add passenger
			plan.addPassenger(p, nextData.getKey(), nextData.getValue());
			
			//Make sure we visit the destination to find next passengers
			path.add(p.getDestination());
			
			nextData = null;
		}else{
			//if not possible, terminate
			terminate();
		}
	}

	/**
	 * {@link #visit(Destination)}
	 * On visiting a destination node, we smell for feasibility data left by other passengers
	 * We clone towards the passengers most in need of transportation (least left time)
	 */
	@Override
	public void visit(Destination d) {
	
		//Update arrival time
		if(estimatedArrival == 0){ //This is our first node, so taxi is already on the way
			updateEstimatedArrival(taxi.getPosition(), d.getPosition());
		}else{ //We yet have to pick up the passenger for this destination
			updateEstimatedArrival(d.getRequest().getTravelTime());
		}
		
		//Smell for feasibility pheromones
		List<FeasibilityPheromone> pheromones = environment.smell(d, FeasibilityPheromone.class);
		
		if(pheromones.isEmpty()){ //No information = we're done here
			hops = 0;
		}else{ //Clone towards passengers most in need
			Queue<FeasibilityPheromone> orderedPheromones = new PriorityQueue<FeasibilityPheromone>(pheromones);
			int i = 0;
			for(FeasibilityPheromone pheromone : orderedPheromones){
				if(++i > SPLIT) //we've reached clone limit
					break;
				
				if(!feasible(pheromone) || plan.getPath().contains(pheromone.source)) //We can't help this passenger anymore
					continue;
				
				ExplorationAnt ant = this.clone();	//Clone & spread
				ant.nextData = new Tuple<Long,Long>(pheromone.estimatedTravelDistance, pheromone.estimatedTravelTime);
				ant.path.add(pheromone.source);
				simulator.register(ant);
			}
			terminate(); //Terminate this ant, let clones do the rest of the job
		}
		
	}
	
	/**
	 * OTHER LOGIC
	 */
	
	/**
	 * Updates estimated arrival time with given time
	 * @param time	Time to add to estimated arrival time
	 */
	public void updateEstimatedArrival(long time){
		this.estimatedArrival += time;
	}
	
	/**
	 * Add travel time to estimated arrival time
	 * @param from	From
	 * @param to	To
	 * @return	Travel time
	 */
	public long updateEstimatedArrival(Point from, Point to){
		if(estimatedArrival == 0)
			estimatedArrival = startTime;
		long traveltime = rm.getTravelTime(taxi, from, to);
		updateEstimatedArrival(traveltime);
		return traveltime;
	}
	
	/**
	 * Clone this ant
	 */
	@Override
	public ExplorationAnt clone(){
		ExplorationAnt ant = new ExplorationAnt(taxi, startTime, path.get(0), hops);
		ant.plan = plan.clone();
		ant.path = new LinkedList<AntAcceptor>(path);
		ant.index = index;
		ant.estimatedArrival = estimatedArrival;
		return ant;
	}
	

	/**
	 * {@link #feasible(long, TransportRequest)}
	 */
	protected boolean feasible(TransportRequest r){
		return feasible(estimatedArrival, r);
	}
	
	/**
	 * {@link #feasible(long, TransportRequest)}
	 */
	protected boolean feasible(FeasibilityPheromone p){
		long time = estimatedArrival;
		time += p.estimatedTravelTime;
		return feasible(time, p.request);
	}
	
	/**
	 * Check whether we can handle the transportRequest if it would be our next move
	 * @param time	Time of arrival at passenger
	 * @param r		TransportRequest we want to handle
	 * @return	Yes if we can handle the passenger within its time window
	 */
	protected boolean feasible(long time, TransportRequest r){
		return time + r.getTravelTime() < r.getDeadline();
	}
	
	/**
	 * RoadUser#initRoadUser(RoadModel)
	 */
	@Override
	public void initRoadUser(RoadModel roadModel){
		super.initRoadUser(roadModel);
		plan = new Plan(rm,taxi,new LinkedList<Passenger>(),new LinkedList<Long>(), new LinkedList<Long>(),new MoneyHeuristic());
	}


	
}
