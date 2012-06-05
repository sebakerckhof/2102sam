/**
 * 
 */
package rinde.sim.core.model;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import rinde.sim.core.graph.Graph;
import rinde.sim.core.graph.Point;
import rinde.sim.util.Tuple;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;

/**
 * @author Rinde van Lon (rinde.vanlon@cs.kuleuven.be)
 * 
 */
public class CachedRoadModel extends RoadModel {

	private Table<Point, Point, List<Point>> pathTable;

	private final Multimap<Class<?>, RoadUser> classObjectMap;
	
	private final Table<Point,Point, Long> distanceTable;
	private final Table<Point,Point, Long> timeTable;
	
	public CachedRoadModel(Graph<?> pGraph) {
		super(pGraph);
		pathTable = HashBasedTable.create();
		classObjectMap = LinkedHashMultimap.create();
		distanceTable = HashBasedTable.create();
		timeTable = HashBasedTable.create();
	}

	public void setPathCache(Table<Point, Point, List<Point>> pPathTable) {
		pathTable = pPathTable;
	}

	public Table<Point, Point, List<Point>> getPathCache() {
		return pathTable;
	}

	// overrides internal func to add caching
	@Override
	protected List<Point> doGetShortestPathTo(Point from, Point to) {
		if (pathTable.contains(from, to)) {
			return pathTable.get(from, to);
		} else {
			List<Point> path = super.doGetShortestPathTo(from, to);
			pathTable.put(from, to, path);
			return path;
		}
	}

	@Override
	public void addObjectAt(RoadUser newObj, Point pos) {
		super.addObjectAt(newObj, pos);
		classObjectMap.put(newObj.getClass(), newObj);
	}

	@Override
	public void addObjectAtSamePosition(RoadUser newObj, RoadUser existingObj) {
		super.addObjectAtSamePosition(newObj, existingObj);
		classObjectMap.put(newObj.getClass(), newObj);
	}

	@Override
	public void clear() {
		super.clear();
		classObjectMap.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Y extends RoadUser> Set<Y> getObjectsOfType(final Class<Y> type) {
		checkArgument(type != null, "type can not be null");
		Set<Y> set = new LinkedHashSet<Y>();
		set.addAll((Set<Y>) classObjectMap.get(type));
		return set;
	}

	@Override
	public void removeObject(RoadUser o) {
		super.removeObject(o);
		classObjectMap.remove(o.getClass(), o);
	}
	
	/**
	 * {@link RoadModel#getTravelData(double, Point, Point)}
	 * +caching support
	 */
	@Override
	public Tuple<Long,Long> getTravelData(double speed, Point from, Point to){
		if(distanceTable.contains(from, to) && timeTable.contains(from, to)){
			return new Tuple<Long,Long>(distanceTable.get(from, to), timeTable.get(from, to));
		}else{
			Tuple<Long,Long> data = super.getTravelData(speed, from, to);
			distanceTable.put(from, to, data.getKey());
			timeTable.put(from, to, data.getValue());
			return data;
		}
	}
	
	/**
	 * {@link RoadModel#getTravelDistance(Point, Point)}
	 * +caching support
	 */
	@Override
	public long getTravelDistance(Point from, Point to){
		if(distanceTable.contains(from, to)){
			return distanceTable.get(from, to);
		}else{
			long distance = super.getTravelDistance(from, to);
			distanceTable.put(from, to, distance);
			return distance;
		}
	}

}
