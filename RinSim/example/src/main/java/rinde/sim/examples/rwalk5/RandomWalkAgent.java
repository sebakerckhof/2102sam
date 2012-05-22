package rinde.sim.examples.rwalk5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.math.random.RandomGenerator;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Graphs;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.MovingRoadUser;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.core.model.communication.CommunicationAPI;
import rinde.sim.core.model.communication.CommunicationUser;
import rinde.sim.core.model.communication.Mailbox;
import rinde.sim.core.model.communication.Message;
import rinde.sim.event.Event;
import rinde.sim.event.EventDispatcher;
import rinde.sim.event.Events;
import rinde.sim.event.Listener;
import rinde.sim.example.rwalk.common.Package;

/**
 * Example of the simple random agent with the use of simulation facilities.
 * @author Bartosz Michalik <bartosz.michalik@cs.kuleuven.be>
 */
class RandomWalkAgent implements TickListener, MovingRoadUser, SimulatorUser, CommunicationUser, Events {

	enum Type {
		START_SERVICE, FINISHED_SERVICE;
	}

	private final EventDispatcher disp;

	public static final String C_BLACK = "color.black";
	public static final String C_YELLOW = "color.yellow";
	public static final String C_GREEN = "color.green";

	private static final int MAX_MSGs = 100;
	private static final int COMMUNICATION_PERIOD = 10000; //10s
	protected RoadModel rs;
	protected RoadUser currentPackage;
	protected Queue<Point> path;
	protected RandomGenerator rnd;
	private SimulatorAPI simulator;
	private final double speed;
	private CommunicationAPI cm;
	private final int radius;

	HashMap<RandomWalkAgent, Long> lastCommunicationTime;

	private Set<RandomWalkAgent> communicatedWith;
	private final Mailbox mailbox;

	private final ReentrantLock lock;

	private int communications;

	private long lastCommunication;
	private final double reliability;

	private int pickedUp;

	/**
	 * Create simple agent.
	 * @param speed default speed of object in graph units per millisecond
	 * @param radius in which it can communicate
	 */
	public RandomWalkAgent(double speed, int radius, double reliability) {
		disp = new EventDispatcher(Type.values());
		communicatedWith = new HashSet<RandomWalkAgent>();
		lastCommunicationTime = new HashMap<RandomWalkAgent, Long>();
		lock = new ReentrantLock();
		mailbox = new Mailbox();
		communications = 0;
		pickedUp = 0;
		this.speed = speed;
		this.radius = radius;
		this.reliability = reliability;

	}

	@Override
	public void tick(long currentTime, long timeStep) {
		checkMsgs(currentTime);
		refreshList(currentTime);

		if (path == null || path.isEmpty()) {
			if (rs.containsObject(currentPackage)) {
				simulator.unregister(currentPackage);

				pickedUp++;
			}
			if (communications > MAX_MSGs || pickedUp > MAX_MSGs) {
				simulator.unregister(this);
				disp.dispatchEvent(new ServiceEndEvent(pickedUp, communications, this));
				return;
			}
			Point destination = rs.getGraph().getRandomNode(rnd);
			currentPackage = new Package("dummy package", destination);
			simulator.register(currentPackage);
			path = new LinkedList<Point>(Graphs.shortestPathEuclidianDistance(rs.getGraph(), rs.getPosition(this), destination));
		} else {
			rs.followPath(this, path, timeStep);
		}

		sendMsgs(currentTime);
	}

	private void refreshList(long currentTime) {
		if (lastCommunication + COMMUNICATION_PERIOD < currentTime) {
			lock.lock();
			communicatedWith = new HashSet<RandomWalkAgent>();
			for (Entry<RandomWalkAgent, Long> e : lastCommunicationTime.entrySet()) {
				if (e.getValue() + COMMUNICATION_PERIOD * 100 >= currentTime) {
					communicatedWith.add(e.getKey());
				}
			}
			lock.unlock();
		}
	}

	private void sendMsgs(long currentTime) {
		if (lastCommunication + COMMUNICATION_PERIOD < currentTime) {
			lastCommunication = currentTime;
			if (cm != null) {
				cm.broadcast(new Message(this) {
				});
			}
		}
	}

	private void checkMsgs(long currentTime) {
		Queue<Message> messages = mailbox.getMessages();

		for (Message m : messages) {
			lastCommunicationTime.put((RandomWalkAgent) m.getSender(), currentTime);
			communications++;
		}
	}

	public Set<RandomWalkAgent> getCommunicatedWith() {
		lock.lock();
		HashSet<RandomWalkAgent> result = new HashSet<RandomWalkAgent>(communicatedWith);
		lock.unlock();
		return result;
	}

	@Override
	public void initRoadUser(RoadModel model) {
		rs = model;
		Point pos = rs.getGraph().getRandomNode(rnd);
		rs.addObjectAt(this, pos);

		disp.dispatchEvent(new Event(Type.START_SERVICE, this));
	}

	@Override
	public void setSimulator(SimulatorAPI api) {
		simulator = api;
		rnd = api.getRandomGenerator();
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {
		// empty by default
	}

	@Override
	public void setCommunicationAPI(CommunicationAPI api) {
		cm = api;
	}

	@Override
	public Point getPosition() {
		return rs.containsObject(this) ? rs.getPosition(this) : null;
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
		mailbox.receive(message);
	}

	public int getNoReceived() {
		return communications;
	}

	@Override
	public void addListener(Listener l, Enum<?>... eventTypes) {
		disp.addListener(l, eventTypes);
	}

	@Override
	public void removeListener(Listener l, Enum<?>... eventTypes) {
		disp.removeListener(l, eventTypes);

	}

	@Override
	public boolean containsListener(Listener l, Enum<?> eventType) {
		return disp.containsListener(l, eventType);
	}

}
