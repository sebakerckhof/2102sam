package rinde.sim.project.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Predicate;

import rinde.sim.core.TickListener;
import rinde.sim.project.model.Pheromone;

public class PheromoneInfrastructure extends LinkedList<Pheromone>{

	public List<Pheromone> queue;

	public void drop(Pheromone pheromone){
		queue.add(pheromone);
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
