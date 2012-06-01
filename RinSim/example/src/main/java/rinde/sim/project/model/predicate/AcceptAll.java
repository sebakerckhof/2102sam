package rinde.sim.project.model.predicate;

import com.google.common.base.Predicate;

public class AcceptAll<T> implements Predicate<T> {
	public AcceptAll(){}
	
	@Override
	public boolean apply(T input) {
		return true;
	}
}