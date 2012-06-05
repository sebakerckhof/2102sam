package rinde.sim.contractnets.messages;

import rinde.sim.core.model.communication.CommunicationUser;

public class InformResult extends CNetMessage {
	private CNetMessage origin;

	public InformResult(CommunicationUser sender, AcceptProposal ap) {
		super(sender, ap.getCommunicationID());
		this.origin = ap;
	}

	@Override
	public boolean isResponseTo(CNetMessage message) {
		return origin.equals(message);
	}
}
