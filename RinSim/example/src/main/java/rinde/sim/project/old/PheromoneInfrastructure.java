package rinde.sim.project.old;

import java.util.Iterator;
import java.util.LinkedList;

import com.google.common.base.Predicate;

import rinde.sim.core.TickListener;
import rinde.sim.project.model.pheromone.Pheromone;

public class PheromoneInfrastructure<T extends Pheromone> implements TickListener{

	LinkedList<T> pheromones = new LinkedList<T>();
	
	Predicate<T> accept;
	public int size; //-1 = unlimited
	
	public PheromoneInfrastructure(){
		this(-1);
	}
	
	public PheromoneInfrastructure(int size){
		this.size = size;
	}
	
	public PheromoneInfrastructure(Predicate<T> accept, int size){
		this(size);
		this.accept = accept;
	}
	
	public void drop(T pheromone){
		if(accept.apply(pheromone)){
			if(pheromones.size() == this.size){
				//remove or apply filter
			}
			pheromones.add(pheromone);
		}
	}
	
	public LinkedList<T> smell(){
		return new LinkedList<T>(this.pheromones);
	}
	
	public boolean smell(T pheromone){
		return pheromones.contains(pheromone);
	}
	
	@Override
	public void tick(long currentTime, long timeStep) {
		
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {
		Iterator<T> iterator = pheromones.iterator();
		while(iterator.hasNext()){
			T pheromone = iterator.next();
			if(pheromone.hasEvaporated())
				iterator.remove();
		}
	}
	


}
