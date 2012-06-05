package rinde.sim.contractnets;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import rinde.sim.contractnets.messages.AcceptProposal;
import rinde.sim.contractnets.messages.CNetMessage;
import rinde.sim.contractnets.messages.CallForProposals;
import rinde.sim.contractnets.messages.Proposal;
import rinde.sim.contractnets.messages.RejectProposal;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.communication.CommunicationAPI;
import rinde.sim.core.model.communication.CommunicationUser;
import rinde.sim.core.model.communication.Mailbox;
import rinde.sim.core.model.communication.Message;
import rinde.sim.event.Event;
import rinde.sim.event.EventDispatcher;
import rinde.sim.event.Events;
import rinde.sim.event.Listener;
import rinde.sim.lab.common.packages.Package;

public class PackageAgent implements TickListener, //SimulatorUser,
		CommunicationUser, Events {

	//private SimulatorAPI simulator = null;
	private CommunicationAPI communicationAPI = null;
	private Package myPackage;
	private Mailbox mailbox;
	private double radius;
	private double reliability;
	private long deadline;
	private EventDispatcher eDisp;

	private CallForProposals currentRequest = null;
	private Collection<Proposal> proposals = null;
	private AcceptProposal accepted = null;
	
	public enum EventType {
		CFPSent,
		ProposalReceived,
		AcceptProposalSent,
		RejectProposalSent
	}

	public PackageAgent(Package myPackage, double radius, double reliability,
			long deadline) {
		this.myPackage = myPackage;
		this.radius = radius;
		this.reliability = reliability;
		this.deadline = deadline;
		this.mailbox = new Mailbox();
		this.eDisp = new EventDispatcher(EventType.values());
	}

	/*@Override
	public void setSimulator(SimulatorAPI api) {api.
		this.simulator = api;
	}*/

	@Override
	public void tick(long currentTime, long timeStep) {
		// TODO exercise
		handleMessages(currentTime);
		if (!myPackage.needsPickUp()) {
			return;
		}
		if (accepted == null) {
			if (currentRequest == null) {
				broadcastCFP(currentTime);
			} else if (currentRequest.getBiddingDeadline() < currentTime) {
				currentRequest = null;
				if (!proposals.isEmpty()) {
					accepted = chooseBestProposal(proposals, currentTime);
				}
				proposals = null;
			}
		} else {
			if (currentTime > accepted.getDeliveryDeadline()) {
				accepted = null;
			}
		}
	}
	
	private void broadcastCFP(long currentTime) {
		proposals = new LinkedList<Proposal>();
		currentRequest = new CallForProposals(
				CNetMessage.generateCommunicationID(), this, myPackage,
				currentTime + deadline);
		eDisp.dispatchEvent(new Event(EventType.CFPSent, this));
		communicationAPI.broadcast(currentRequest, TruckAgent.class);
	}
	
	private AcceptProposal chooseBestProposal(Collection<Proposal> proposals, long currentTime) {
		AcceptProposal accepted = null;
		Iterator<Proposal> it = proposals.iterator();
		Proposal best = it.next();
		Proposal second = null;
		if (it.hasNext()) {
			second = it.next();
			if (second.getDeliveryTime() < best.getDeliveryTime()) {
				Proposal p = best;
				best = second;
				second = p;
			}
			while (it.hasNext()) {
				Proposal p = it.next();
				if (p.getDeliveryTime() < best.getDeliveryTime()) {
					second = best;
					best = p;
				} else if (p.getDeliveryTime() < second.getDeliveryTime()) {
					second = p;
				}
			}
		}
		long deliveryDeadline = currentTime + (best.getDeliveryTime() - currentTime) * 2;
		if (second != null) {
			deliveryDeadline = second.getDeliveryTime();
		}
		for (Proposal p : proposals) {
			if (p==best) {
				accepted = new AcceptProposal(this, p, deliveryDeadline);
				eDisp.dispatchEvent(new Event(EventType.AcceptProposalSent, this));
				communicationAPI.send(p.getSender(), accepted);
			} else {
				eDisp.dispatchEvent(new Event(EventType.RejectProposalSent, this));
				communicationAPI.send(p.getSender(), new RejectProposal(this, p));
			}
		}
		return accepted;
	}

	private void handleMessages(long currentTime) {
		Queue<Message> messages = mailbox.getMessages();
		for (Message message : messages) {
			if (message instanceof Proposal) {
				Proposal p = (Proposal) message;
				if (p.isResponseTo(currentRequest)) {
					proposals.add(p);
					eDisp.dispatchEvent(new Event(EventType.ProposalReceived, this));
				}
			}
		}
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {
	}

	@Override
	public void setCommunicationAPI(CommunicationAPI api) {
		this.communicationAPI = api;
	}

	@Override
	public Point getPosition() {
		// TODO
		return this.myPackage.getPickupLocation();
	}

	@Override
	public double getRadius() {
		return radius;
	}

	@Override
	public double getReliability() {
		return reliability;
	}

	@Override
	public void receive(Message message) {
		this.mailbox.receive(message);
	}

	@Override
	public void addListener(Listener l, Enum<?>... eventTypes) {
		eDisp.addListener(l, eventTypes);
	}

	@Override
	public void removeListener(Listener l, Enum<?>... eventTypes) {
		eDisp.removeListener(l, eventTypes);
	}

	@Override
	public boolean containsListener(Listener l, Enum<?> eventType) {
		return eDisp.containsListener(l, eventType);
	}
	
	@Override
	public String toString() {
		return "PackageAgent[" + myPackage + "]";
	}
}
