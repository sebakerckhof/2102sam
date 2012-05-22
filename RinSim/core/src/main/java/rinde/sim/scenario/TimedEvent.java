package rinde.sim.scenario;

import java.io.Serializable;

import rinde.sim.event.Event;

/**
 * Simplest time event. The object is a value object.
 * @author Bartosz Michalik <bartosz.michalik@cs.kuleuven.be>
 * @since 2.0
 */
public class TimedEvent extends Event implements Serializable {
	private static final long serialVersionUID = 6832559417793517102L;
	public final long time;
	
	
	public TimedEvent(Enum<?> type, long timestamp) {
		super(type);
		if(timestamp < 0) throw new IllegalArgumentException("timestamp cannot be negative");
		this.time = timestamp;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + (int) (time ^ (time >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimedEvent other = (TimedEvent) obj;
		if (!eventType.equals(other.eventType))
			return false;
		return time == other.time;
	}

	@Override
	public String toString() {
		return eventType + "|" + time;
	}

}
