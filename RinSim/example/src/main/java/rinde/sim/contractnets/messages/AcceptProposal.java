package rinde.sim.contractnets.messages;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.communication.CommunicationUser;

public class AcceptProposal extends TransportMessage {
	private Proposal proposal;
	private long deliveryDeadline;

	public AcceptProposal(CommunicationUser sender, Proposal p, long deliveryDeadline) {
		super(sender, p.getCommunicationID());
		this.proposal = p;
		this.deliveryDeadline = deliveryDeadline;
	}

	/*private Proposal getProposal() {
		return proposal;
	}*/
	
	public long getDeliveryDeadline() {
		return deliveryDeadline;
	}

	@Override
	public Point getDeliveryLocation() {
		return proposal.getDeliveryLocation();
	}

	@Override
	public Point getPickupLocation() {
		return proposal.getPickupLocation();
	}

	@Override
	public boolean isResponseTo(CNetMessage message) {
		return proposal.equals(message);
	}
	
	@Override
	public String toString() {
		return "AcceptProposal["+super.toString()+",pd="+proposal.getDeliveryTime()+",dd="+deliveryDeadline+"]";
	}
}
