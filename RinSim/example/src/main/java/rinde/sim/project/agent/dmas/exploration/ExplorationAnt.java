package rinde.sim.project.agent.dmas.exploration;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.graph.Point;
import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.PassengerAgent;
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


public class ExplorationAnt extends AntAgent{

	public static final int SPLIT = 3;
	
	protected LinkedList<Passenger> plan; 
	protected LinkedList<Long> travelTimes;
	protected LinkedList<Long> travelDistances;
	
	protected long estimatedArrival;
	protected long startTime;
	protected Taxi taxi;
	
	protected Tuple<Long,Long> nextData;
	
	public ExplorationAnt(Taxi taxi, long startTime, AntAcceptor start, int hops) {
		super(start, hops);
		this.taxi = taxi;
		this.startTime = startTime;
		plan = new LinkedList<Passenger>();
		travelTimes = new LinkedList<Long>();
		travelDistances = new LinkedList<Long>();
	}

	
	@Override
	public AntAcceptor next(){
		if(!forward())
			return path.get(0);
		else
			return super.next();
	}
	

	@Override
	public void visit(TaxiAgent t){
		//check if home, report to edmas && terminate
		if(t.equals(plan.peek())){
			environment.drop(t, new ExplorationPheromone(new Plan(taxi, plan, travelDistances, travelTimes, new MoneyHeuristic())));
		}
			
		terminate();
	}
	
	
	@Override
	public void visit(Passenger p) {

		List<IntentionPheromone> pheromones = environment.smell(p, IntentionPheromone.class);
		
		if(nextData == null){
			nextData = rm.getTravelData(taxi.getSpeed(), taxi.getPosition(), p.getPosition());
		}
		
		updateEstimatedArrival(nextData.getValue());
		if(estimatedArrival < p.getRequest().getStart())
			estimatedArrival = p.getRequest().getStart();
		
		if(pheromones.isEmpty() && feasible(p.getRequest())){
			plan.add(p);
			travelDistances.add(nextData.getKey());
			travelTimes.add(nextData.getValue());
			
			path.add(p.getDestination());
			
			nextData = null;
		}else{
			terminate();
		}
	}

	@Override
	public void visit(Destination d) {
	
		
		if(estimatedArrival == 0){
			updateEstimatedArrival(taxi.getPosition(), d.getPosition());
		}else{
			updateEstimatedArrival(d.getRequest().getTravelTime());
		}
		
		List<FeasibilityPheromone> pheromones = environment.smell(d, FeasibilityPheromone.class);
		if(pheromones.isEmpty()){
			hops = 0;
		}else{
			Queue<FeasibilityPheromone> orderedPheromones = new PriorityQueue<FeasibilityPheromone>(pheromones);
			int i = 0;
			for(FeasibilityPheromone pheromone : orderedPheromones){
				if(++i > SPLIT)
					break;
				
				if(!feasible(pheromone) || plan.contains(pheromone.source))
					continue;
				
				ExplorationAnt ant = this.clone();
				ant.nextData = new Tuple<Long,Long>(pheromone.estimatedTravelDistance, pheromone.estimatedTravelTime);
				ant.path.add(pheromone.source);
				environment.deploy(ant);
			}
			terminate();
		}
		
	}
	
	public void updateEstimatedArrival(long time){
		this.estimatedArrival += time;
	}
	
	public long updateEstimatedArrival(Point from, Point to){
		if(estimatedArrival == 0)
			estimatedArrival = startTime;
		long traveltime = rm.getTravelTime(taxi, from, to);
		updateEstimatedArrival(traveltime);
		return traveltime;
	}
	
	@Override
	public ExplorationAnt clone(){
		ExplorationAnt ant = new ExplorationAnt(taxi, startTime, path.get(0), hops);
		ant.plan = plan;
		ant.path = path;
		ant.index = index;
		ant.estimatedArrival = estimatedArrival;
		return ant;
	}
	

	
	protected boolean feasible(TransportRequest r){
		return feasible(estimatedArrival, r);
	}
	
	protected boolean feasible(FeasibilityPheromone p){
		long time = estimatedArrival;
		time += p.estimatedTravelTime;
		return feasible(time, p.request);
	}
	
	protected boolean feasible(long time, TransportRequest r){
		return time + r.getTravelTime() < r.getDeadline();
	}


	
}
