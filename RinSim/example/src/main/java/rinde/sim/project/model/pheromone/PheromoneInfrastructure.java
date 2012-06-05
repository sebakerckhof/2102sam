package rinde.sim.project.model.pheromone;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import rinde.sim.project.model.pheromone.Pheromone;

/**
 * PheromoneInfrastructure
 * Handles pheromones
 */
public class PheromoneInfrastructure extends LinkedList<Pheromone>{

	public static final long serialVersionUID = 1;
	
	/**
	 * Queue of pheromones waiting to be added
	 */
	public List<Pheromone> queue;
	
	/**
	 * Handler that is called when new pheromone gets dropped
	 */
	protected PheromoneHandler handler;
	
	/**
	 * Constructor
	 * @param handler	PheromoneHandler
	 */
	public PheromoneInfrastructure(PheromoneHandler handler){
		this.handler = handler;
	}
	
	/**
	 * Drop pheromone
	 * @param pheromone	Pheromone to drop
	 * @return {@link PheromoneHandler#handle(PheromoneInfrastructure, Pheromone)}
	 */
	public boolean drop(Pheromone pheromone){
		return handler.handle(this, pheromone);
	}
	
	public List<Pheromone> smell(){
		return new LinkedList<Pheromone>(this);
	}
	
	/**
	 * Smell pheromones of given type
	 * @param type	Type of pheromones to smell
	 * @return	List with pheromones of requested type
	 */
	@SuppressWarnings("unchecked")
	public <Y extends Pheromone> List<Y> smell(Class<Y> type){
		LinkedList<Y> p = new LinkedList<Y>();
		for(Pheromone pheromone : this.smell()){
			if(type.isInstance(pheromone))
				p.add((Y) pheromone);
		}
		
		return p;
	}
	

	/**
	 * Update pheromones
	 * -removes evaporated pheromones
	 * -adds pheromones waiting to be added
	 */
	public void update() {
		//Remove evaporated pheromones
		Iterator<Pheromone> iterator = this.iterator();
		while(iterator.hasNext()){
			Pheromone pheromone = iterator.next();
			if(pheromone.hasEvaporated())
				iterator.remove();
		}
		
		//Add waiting pheromones
		this.addAll(queue);
		queue.clear();
		
	}
	


}
