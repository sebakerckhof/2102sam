package rinde.sim.contractnets.messages;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.communication.CommunicationUser;

public abstract class TransportMessage extends CNetMessage {

	public TransportMessage(CommunicationUser sender, long communicationID) {
		super(sender, communicationID);
	}

	public abstract Point getDeliveryLocation();

	public abstract Point getPickupLocation();

	@Override
	public String toString() {
		return "id="+getCommunicationID()+",from="+getPickupLocation()+",to="+getDeliveryLocation();
	}
}