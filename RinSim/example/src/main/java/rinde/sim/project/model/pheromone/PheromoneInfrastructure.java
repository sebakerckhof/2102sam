package rinde.sim.project.model.pheromone;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Predicate;

import rinde.sim.core.TickListener;
import rinde.sim.project.model.pheromone.Pheromone;

public class PheromoneInfrastructure extends LinkedList<Pheromone>{

	public static final long serialVersionUID = 1;
	public List<Pheromone> queue;
	protected PheromoneHandler handler;
	
	public PheromoneInfrastructure(PheromoneHandler handler){
		this.handler = handler;
	}
	
	public void drop(Pheromone pheromone){
		handler.handle(this, pheromone);
	}
	
	public LinkedList<Pheromone> smell(){
		return new LinkedList<Pheromone>(this);
	}
	
	public boolean smell(Pheromone pheromone){
		return this.contains(pheromone);
	}
	

	public void update() {
		//Evaporate pheromones
		Iterator<Pheromone> iterator = this.iterator();
		while(iterator.hasNext()){
			Pheromone pheromone = iterator.next();
			if(pheromone.hasEvaporated())
				iterator.remove();
		}
		
		//A
		this.addAll(queue);
		queue.clear();
		
	}
	


}
