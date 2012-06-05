package rinde.sim.contractnets.messages;

import rinde.sim.core.model.communication.CommunicationUser;

public class RejectProposal extends CNetMessage {
	private Proposal p;

	public RejectProposal(CommunicationUser sender, Proposal p) {
		super(sender, p.getCommunicationID());
		this.p = p;
	}

	/*public Proposal getProposal() {
		return p;
	}*/

	@Override
	public boolean isResponseTo(CNetMessage message) {
		return p.equals(message);
	}
}
