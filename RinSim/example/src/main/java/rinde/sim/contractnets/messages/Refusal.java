package rinde.sim.contractnets.messages;

import rinde.sim.core.model.communication.CommunicationUser;

public class Refusal extends CNetMessage {
	private CNetMessage origin;

	public Refusal(CommunicationUser sender, CallForProposals cfp) {
		super(sender, cfp.getCommunicationID());
		this.origin = cfp;
	}

	@Override
	public boolean isResponseTo(CNetMessage message) {
		return origin.equals(message);
	}

}
