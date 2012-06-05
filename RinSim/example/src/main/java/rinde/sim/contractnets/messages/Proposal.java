package rinde.sim.contractnets.messages;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.communication.CommunicationUser;

public class Proposal extends TransportMessage {
	private CallForProposals request;
	private long deliveryTime;

	public Proposal(CommunicationUser sender, CallForProposals cfp, long deliveryTime) {
		super(sender, cfp.getCommunicationID());
		this.request = cfp;
		this.deliveryTime = deliveryTime;
	}

	/*public CallForProposals getCallForProposals() {
		return request;
	}*/
	
	public long getDeliveryTime() {
		return deliveryTime;
	}

	@Override
	public Point getDeliveryLocation() {
		return request.getDeliveryLocation();
	}

	@Override
	public Point getPickupLocation() {
		return request.getPickupLocation();
	}

	@Override
	public boolean isResponseTo(CNetMessage message) {
		return request.equals(message);
	}
	
	@Override
	public String toString() {
		return "Proposal["+super.toString()+",pd="+deliveryTime+"]";
	}
}
