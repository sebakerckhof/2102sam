package rinde.sim.contractnets.messages;

import rinde.sim.core.model.communication.CommunicationUser;
import rinde.sim.core.model.communication.Message;

public abstract class CNetMessage extends Message {
	private long communicationID;
	private static long nextID = 0;

	public CNetMessage(CommunicationUser sender, long communicationID) {
		super(sender);
		this.communicationID = communicationID;
	}

	public long getCommunicationID() {
		return communicationID;
	}
	
	public static long generateCommunicationID() {
		return nextID++;
	}
	
	public abstract boolean isResponseTo(CNetMessage message);
	
	/**
	 * Overwrite this method when {@link #equals(Object)} is redefined.
	 * This method is necessary for a non final class to maintain the rules of equals:
	 * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
	 * @param obj
	 * @return
	 */
	protected boolean canEqual(Object obj) {
		return obj instanceof CNetMessage;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!getClass().equals(obj.getClass())) return false;
		CNetMessage m = (CNetMessage) obj;
		if (!m.canEqual(this)) return false;
		return getCommunicationID() == m.getCommunicationID() && getSender().equals(m.getSender());
	}
}
