package rinde.sim.contractnets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Permutator{
	private List<Integer> found;
	private Collection<Integer> free;
	private boolean atEnd;
	private int cutAt;
	
	public Permutator(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("argument size (== " + size + ") < 0");
		}
		found = new ArrayList<Integer>(size);
		free = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++) {
			free.add(i);
		}
		atEnd = false;
		cutAt = -1;
	}
	
	public void reset()	{
		Iterator<Integer> it = found.iterator();
		while (it.hasNext()) {
			free.add(it.next());
			it.remove();
		}
		atEnd = false;
		cutAt = -1;
	}
	
	/**
	 * Skips every permutation that has the same prefix up to the 'index'-th element of the last returned permutation.
	 * 
	 * So, if the last returned permutation was [0,1,2,3,4], cut(1) will cause the following {@link #next()} to return [0,2,1,3,4] instead of [0,1,2,4,3].
	 * @param index
	 */
	public void cut(int index) {
		if (index < 0) {
			throw new IllegalArgumentException("index (== " + index + ") < 0");
		}
		if (atEnd) {
			return;
		}
		if (cutAt < 0 || cutAt > index) {
			cutAt = index;
		}
	}
	
	private void doCut(int index) {
		Integer last = null;
		while (found.size() > index) {
			last = found.remove(found.size() - 1);
			free.add(last);
		}
		if (last != null) {
			last++;
			last = next(last);
			if (last == null) {
				atEnd = true;
				return;
			}
			last = getLowestFree(last);
			free.remove(last);
			found.add(last);
		}
	}

	/**
	 * Get the next permutation or <code>null</code> if there is no more.
	 * 
	 * @return unmodifiable list containing indices.
	 */
	public List<Integer> next() {
		Integer current;
		if (cutAt >= 0) {
			doCut(cutAt);
		}
		if (atEnd) {
			return null;
		}
		//break down
		current = next(0);
		if (current == null) {
			atEnd = true;
			return null;
		}
		//build
		current = getLowestFree(current);
		while (current != null) {
			free.remove(current);
			found.add(current);
			current = getLowestFree(0);
		}
		return Collections.unmodifiableList(found);
	}
	
	/**
	 * Put the elements of 'original' in 'permutated' using the values of {@link #next()} as indices.
	 * 
	 * @param original a list of elements. This list may be unmodifiable and must have the same size as this Permutator.
	 * @param permutated an empty list that will contain the permutated elements of 'original'.
	 * @return <code>permutated</code> if there was a next permutation, <code>null</code> otherwise.
	 * @throws IllegalArgumentException when 'permutated' is not empty or the size of original is not the same as the size of this Permutator.
	 */
	public <X> List<X> next(List<X> original, List<X> permutated) {
		if (!permutated.isEmpty()) {
			throw new IllegalArgumentException("Argument 'permutated' is not empty.");
		}
		if (original.size() != size()) {
			throw new IllegalArgumentException("Size of argument 'original' does not match size of this Permutator.");
		}
		List<Integer> next = next();
		if (next == null) {
			return null;
		}
		for (Integer i : next) {
			permutated.add(original.get(i));
		}
		return permutated;
	}
	
	private Integer next(Integer lowerBound) {
		while (getLowestFree(lowerBound) == null) {
			if (found.isEmpty()) {
				return null;
			}
			lowerBound = found.remove(found.size() - 1);
			free.add(lowerBound);
			lowerBound++;
		}
		return lowerBound;
	}
	
	private Integer getLowestFree(Integer lowerBound) {
		Integer lowest = null;
		for (Integer e : free) {
			if (e >= lowerBound) {
				if (lowest == null || e < lowest) {
					lowest = e;
				}
			}
		}
		return lowest;
	}
	
	public int size() {
		return found.size() + free.size();
	}
}
