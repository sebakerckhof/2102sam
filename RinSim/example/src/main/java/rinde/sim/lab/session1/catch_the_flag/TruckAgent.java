package rinde.sim.lab.session1.catch_the_flag;

import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rinde.sim.core.Simulator;
import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Graphs;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.MovingRoadUser;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.communication.CommunicationAPI;
import rinde.sim.core.model.communication.CommunicationUser;
import rinde.sim.core.model.communication.Mailbox;
import rinde.sim.core.model.communication.Message;
import rinde.sim.lab.common.ConfirmationMessage;
import rinde.sim.lab.common.Flag;
import rinde.sim.lab.common.TransportRequest;

public class TruckAgent  implements TickListener, MovingRoadUser, SimulatorUser, CommunicationUser{

	protected static final Logger LOGGER = LoggerFactory.getLogger(Simulator.class); 
	
	private Point startingLocation;

	private RoadModel rm;
	private CommunicationAPI cm;
	private SimulatorAPI simulator;
	protected Queue<Point> path;
	private final double speed;
	private Mailbox mailbox;
	private long flag;
	private Point movingTowards;
	
	public TruckAgent(Point startingLocation, double speed){
		LOGGER.info("TruckAgent created.");
		this.startingLocation = startingLocation;
		this.speed = speed;
		this.mailbox = new Mailbox();
	}
	
	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
		this.rm.addObjectAt(this, startingLocation);
	}
	

	@Override
	public void tick(long currentTime, long timeStep) {
		//get all received messages (this empties the mailbox)

		Queue<Message> messages = mailbox.getMessages();
		for(Message message: messages){
			if(message instanceof TransportRequest){
				TransportRequest tr = (TransportRequest) message;
				this.movingTowards = tr.getLocation();
				this.flag = tr.getFlagID();
				
				this.path = new LinkedList<Point>(rm.getShortestPathTo(this, this.movingTowards));
				//path = new LinkedList<Point>(Graphs.shortestPathEuclidianDistance(rm.getGraph(), rm.getPosition(this), this.movingTowards));
			
				
			}
		}
		
		if(path != null){
			rm.followPath(this,path,timeStep);
			if(path.isEmpty()){
				cm.broadcast(new ConfirmationMessage(this, "test", flag));
				
				path = null;
			}
		}
		
	}
	


	@Override
	public void afterTick(long currentTime, long timeStep) {
		//not used
	}

	@Override
	public void setCommunicationAPI(CommunicationAPI api) {
		this.cm = api;
	}

	@Override
	public Point getPosition() {
		return this.rm.getPosition(this);
	}

	@Override
	public double getRadius() {
		//distances are ignored in the communication model
		return 0;
	}

	@Override
	public double getReliability() {
		//we do not consider reliability in this example
		return 1;
	}

	@Override
	public void receive(Message message) {
		this.mailbox.receive(message);
	}

	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}

	@Override
	public double getSpeed() {
		return speed;
	}
}
