package rinde.sim.contractnets.messages;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.communication.CommunicationUser;
import rinde.sim.lab.common.packages.Package;

public class CallForProposals extends TransportMessage {
	private Package subject;
	private long deadline;

	public CallForProposals(long comID, CommunicationUser sender, Package p, long biddingDeadline) {
		super(sender, comID);
		this.subject = p;
		this.deadline = biddingDeadline;
	}
	
	public long getBiddingDeadline() {
		return deadline;
	}
	
	//public Package getPackage() {
	//	return subject;
	//}

	@Override
	public Point getPickupLocation() {
		return subject.getPickupLocation();
	}

	@Override
	public Point getDeliveryLocation() {
		return subject.getDeliveryLocation();
	}

	@Override
	public boolean isResponseTo(CNetMessage message) {
		return false;
	}
}
