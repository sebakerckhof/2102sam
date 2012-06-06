package rinde.sim.contractnets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import rinde.sim.contractnets.messages.AcceptProposal;
import rinde.sim.contractnets.messages.CNetMessage;
import rinde.sim.contractnets.messages.CallForProposals;
import rinde.sim.contractnets.messages.Proposal;
import rinde.sim.contractnets.messages.Refusal;
import rinde.sim.contractnets.messages.TransportMessage;
import rinde.sim.contractnets.road.RouteProcessor;
import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Connection;
import rinde.sim.core.graph.EdgeData;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.communication.CommunicationAPI;
import rinde.sim.core.model.communication.CommunicationUser;
import rinde.sim.core.model.communication.Mailbox;
import rinde.sim.core.model.communication.Message;
import rinde.sim.event.Event;
import rinde.sim.event.EventDispatcher;
import rinde.sim.event.Events;
import rinde.sim.event.Listener;
import rinde.sim.lab.common.trucks.Truck;

public class TruckAgent implements TickListener, SimulatorUser,
		CommunicationUser, Events {

	private SimulatorAPI simulator;
	private LinkedList<Point> path;
	private Truck truck;
	private CommunicationAPI communicationAPI;
	private double reliability, radius;
	private Mailbox mailbox;
	private Proposal proposal;
	private List<AcceptProposal> tasks;
	private AcceptProposal	currentTask;
	private int queueSize;
	private EventDispatcher eDisp;
	
	public enum EventType {
		CFPReceived,
		ProposalSent,
		RefusalSent,
		AcceptProposalReceived
	}

	public TruckAgent(Truck truck, double radius, double reliability, int maxPlannedTasks) {
		this.truck = truck;
		this.radius = radius;
		this.reliability = reliability;
		this.mailbox = new Mailbox();
		this.proposal = null;
		this.tasks = new LinkedList<AcceptProposal>();
		this.currentTask = null;
		this.queueSize = maxPlannedTasks;
		this.eDisp = new EventDispatcher(EventType.values());
	}

	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}

	/**
	 * Very dumb agent, that chooses paths randomly and tries to pickup stuff
	 * and deliver stuff at the end of his paths
	 */
	@Override
	public void tick(long currentTime, long timeStep) {
		// TODO exercise
		if (path == null || path.isEmpty()) {
			path = getPath();
		}
		handleMessages(currentTime);
		if (path == null || path.isEmpty()) {
			path = getPath();
		}
		if (path != null && !path.isEmpty()) {
			truck.drive(path, timeStep);
		}
	}
	
	private LinkedList<Point> getPath() {
		LinkedList<Point> path = null;
		RoadModel rm = truck.getRoadModel();
		if (currentTask != null) {
			Point pos = truck.getPosition();
			if (pos.equals(currentTask.getPickupLocation()) && !truck.hasLoad()) {
				truck.tryPickup();
				path = new LinkedList<Point>(rm.getShortestPathTo(truck,
								currentTask.getDeliveryLocation()));
			} else if (pos.equals(currentTask.getDeliveryLocation())) {
				truck.tryDelivery();
				currentTask = null;
			}
		}
		if (currentTask == null) {
			Point destination = null;
			if (!tasks.isEmpty()) {
				currentTask = tasks.remove(0);
				destination = currentTask.getPickupLocation();
			} else if (proposal == null) {
				List<Point> p = null;
				while (p == null || p.size() < 2) {
					destination = rm.getGraph()
						.getRandomNode(simulator.getRandomGenerator());
					p = rm.getShortestPathTo(truck, destination);
				}
				if (truck.getPosition().equals(p.get(0))) {
					destination = p.get(1);
				} else {
					destination = p.get(0);
				}
			}
			if (destination != null) {
				path = new LinkedList<Point>(rm.getShortestPathTo(truck, destination));
			}
		}
		return path;
	}

	private void handleMessages(long currentTime) {
		Queue<Message> messages = mailbox.getMessages();
		for (Message message : messages) {
			if (message instanceof CNetMessage) {
				handleCNet((CNetMessage) message, currentTime);
			}
		}
	}

	private void handleCNet(CNetMessage message, long currentTime) {
		if (message instanceof CallForProposals) {
			eDisp.dispatchEvent(new Event(EventType.CFPReceived, this));
			CallForProposals cfp = (CallForProposals) message;
			Proposal p = null;
			if (proposal == null) {
				p = createBestProposal(cfp, currentTime);
			} else if (currentTime > proposal.getDeliveryTime()) {
				proposal = null;
			}
			if (p != null) {
				proposal = p;
				eDisp.dispatchEvent(new Event(EventType.ProposalSent, this));
				communicationAPI.send(cfp.getSender(), p);
			} else {
				Refusal ref = new Refusal(this, cfp);
				eDisp.dispatchEvent(new Event(EventType.RefusalSent, this));
				communicationAPI.send(cfp.getSender(), ref);
				
			}
		}
		if (message instanceof AcceptProposal) {
			eDisp.dispatchEvent(new Event(EventType.AcceptProposalReceived, this));
			AcceptProposal ap = (AcceptProposal) message;
			reorderPlannedTasks(ap, currentTime);
		}
		if (message.isResponseTo(proposal)) {
			proposal = null;
		}
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCommunicationAPI(CommunicationAPI api) {
		this.communicationAPI = api;
	}

	@Override
	public Point getPosition() {
		return this.truck.getPosition();
	}

	@Override
	public double getRadius() {
		return this.radius;
	}

	@Override
	public double getReliability() {
		return this.reliability;
	}

	@Override
	public void receive(Message message) {
		this.mailbox.receive(message);
	}

	/**
	 * Creates a proposal that has the greatest chance of being accepted,
	 *  while still respecting previously made contracts and other constraints.
	 * 
	 * @param cfp
	 * @param currentTime 
	 * @return a proposal, or <code>null</code> if constraints can't be met.
	 */
	private Proposal createBestProposal(CallForProposals cfp, long currentTime) {
		if (tasks.size() >= queueSize) {
			return null;
		}
		currentTime = getCurrentTaskDeliveryTime(currentTime);
		Permutator p = new Permutator(tasks.size());
		ArrayList<AcceptProposal> current = new ArrayList<AcceptProposal>(tasks.size());
		List<Long> endTimes = new ArrayList<Long>(tasks.size());
		long deliveryTime = getDeliveryTime(cfp, getCurrentTaskEndPoint(), Math.max(currentTime, cfp.getBiddingDeadline()));
		outer:
		for (int i = 0; i <= tasks.size(); i++) {
			p.reset();
			for (current.clear(); p.next(tasks, current) != null; current.clear()) {
				Point from = getCurrentTaskEndPoint();
				long time = getCurrentTaskDeliveryTime(currentTime);
				endTimes.clear();
				int cut = checkValid(current.subList(0, i), from, time, endTimes);
				if (cut >= 0) {
					p.cut(cut);
					continue;
				}
				if (i > 0) {
					from = current.get(i - 1).getDeliveryLocation();
					time = endTimes.get(endTimes.size() - 1);
				}
				if (time < cfp.getBiddingDeadline()) {
					time = cfp.getBiddingDeadline();
				}
				deliveryTime = getDeliveryTime(cfp, from, time);
				cut = checkValid(current.subList(i, tasks.size()), from, time, endTimes);
				if (cut >= 0) {
					p.cut(i + cut);
					continue;
				} else {
					break outer;
				}
			}
		}
		return new Proposal(this, cfp, deliveryTime);
	}
	
	/**
	 * Put the planned tasks in optimal order after adding the new task.
	 * 
	 * @param ap new task.
	 */
	private void reorderPlannedTasks(AcceptProposal ap, long currentTime) {
		tasks.add(ap);
		Permutator p = new Permutator(tasks.size());
		long bestEnd = 0;
		List<AcceptProposal> best = null;
		List<AcceptProposal> current = null;
		List<Long> endTimes = new ArrayList<Long>(tasks.size());
		List<Long> overTime = new ArrayList<Long>(); //TODO: remove
		while (true) {
			current = p.next(tasks, new ArrayList<AcceptProposal>(tasks.size()));
			if (current == null) {
				break;
			}
			endTimes.clear();
			int cutIndex = checkValid(current, getCurrentTaskEndPoint(), getCurrentTaskDeliveryTime(currentTime), endTimes, overTime);
			if (cutIndex >= 0) {
				p.cut(cutIndex);
			} else {
				long end = endTimes.get(endTimes.size() - 1);
				if (best == null) {
					bestEnd = end;
					best = current;
				} else if (end < bestEnd) {
					bestEnd = end;
					best = current;
				}
			}
		}
		if (best == null) {
			//Collections.sort(overTime);
			System.err.println(overTime);
			throw new IllegalStateException("Unable to find suitable task order.");
		}
		tasks.clear();
		for (AcceptProposal pr : best) {
			tasks.add(pr);
		}
	}
	
	// TODO: remove
	private int checkValid(List<AcceptProposal> current, Point from, long time, List<Long> endTimes) {
		return checkValid(current, from, time, endTimes, new ArrayList<Long>());
	}
	
	private int checkValid(List<AcceptProposal> current, Point from, long time, List<Long> endTimes, List<Long> overtime) {
		Iterator<AcceptProposal> it = current.iterator();
		RouteProcessor rp = getRouteProcessor();
		Point end = from;
		for (int i = 0; it.hasNext(); i++) {
			AcceptProposal m = it.next();
			time += rp.getTravelTime(end, m.getPickupLocation());
			end = m.getDeliveryLocation();
			time += rp.getTravelTime(m.getPickupLocation(), end);
			if (time > m.getDeliveryDeadline()) {
				overtime.add(time - m.getDeliveryDeadline());
				return i;
			}
			endTimes.add(time);
		}
		return -1;
	}

	private long getCurrentTaskDeliveryTime(long currentTime) {
		RouteProcessor rp = getRouteProcessor();
		Connection<? extends EdgeData> conn = truck.getRoadModel().getConnection(truck);
		Point start;
		if (conn != null) {
			currentTime += rp.getTravelTime(conn.from, conn.to);
			start = conn.to;
		} else {
			start = truck.getPosition();
		}
		if (currentTask != null) {
			if (!truck.hasLoad()) {
				currentTime += getDeliveryTime(currentTask, start, 0);
			} else {
				currentTime += rp.getTravelTime(start, currentTask.getDeliveryLocation());
			}
		} else if (path != null) {
			currentTime += rp.getTravelTime(start, path);
		}
		return currentTime;
	}
	
	private long getDeliveryTime(TransportMessage task, Point start, long startTime) {
		RouteProcessor rp = getRouteProcessor();
		Point pickup = task.getPickupLocation();
		startTime += rp.getTravelTime(start, pickup);
		startTime += rp.getTravelTime(pickup, task.getDeliveryLocation());
		return startTime;
	}
	
	private Point getCurrentTaskEndPoint() {
		if (currentTask != null) {
			return currentTask.getDeliveryLocation();
		} else if (path!= null && !path.isEmpty()) {
			return path.getLast();
		} else {
			return truck.getPosition();
		}
	}
	
	private RouteProcessor getRouteProcessor() {
		return new RouteProcessor(truck.getRoadModel(), truck.getSpeed());
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
		return "TruckAgent[" + truck.getTruckID() + "]";
	}
}
