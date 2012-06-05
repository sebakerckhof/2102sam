package rinde.sim.contractnets;

import java.util.List;

import org.apache.commons.math.random.MersenneTwister;
import org.eclipse.swt.graphics.RGB;

import rinde.sim.core.Simulator;
import rinde.sim.core.graph.Connection;
import rinde.sim.core.graph.Graph;
import rinde.sim.core.graph.MultiAttributeEdgeData;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.communication.CommunicationModel;
import rinde.sim.event.Event;
import rinde.sim.event.Listener;
import rinde.sim.lab.common.packages.DeliveryLocation;
import rinde.sim.lab.common.packages.Package;
import rinde.sim.lab.common.trucks.Truck;
import rinde.sim.scenario.ConfigurationException;
import rinde.sim.scenario.Scenario;
import rinde.sim.scenario.ScenarioController;
import rinde.sim.serializers.DotGraphSerializer;
import rinde.sim.serializers.SelfCycleFilter;
import rinde.sim.ui.View;
import rinde.sim.ui.renderers.ObjectRenderer;
import rinde.sim.ui.renderers.UiSchema;

public class SimpleController extends ScenarioController {

	private static double	truckRadius = Double.MAX_VALUE;
	private static double	packageRadius = 10000;
	
	private static int		queueSize = 3;

	private static long cfpDeadline = 1000;

	String map;

	private RoadModel roadModel;
	private CommunicationModel communicationModel;
	
	private int truckID = 0;
	private int packageID = 0;
	private Graph<MultiAttributeEdgeData> graph;
	private Listener cnetLogger;
	
	public SimpleController(Scenario scen, int numberOfTicks, String map)
			throws ConfigurationException {
		super(scen, numberOfTicks);
		this.map = map;
		cnetLogger = new CNetEventLogger();
		initialize();
	}

	@Override
	protected Simulator createSimulator() throws Exception {
		try {
			graph = DotGraphSerializer.getMultiAttributeGraphSerializer(
					new SelfCycleFilter()).read(map);
			setDefaultMaxSpeed(graph, 50000);
			fixConnectionLengths(graph);
		} catch (Exception e) {
			throw new ConfigurationException("e:", e);
		}
		roadModel = new RoadModel(graph);
		MersenneTwister rand = new MersenneTwister(123);
		communicationModel = new CommunicationModel(rand, false);
		Simulator s = new Simulator(rand, 100);
		s.register(roadModel);
		s.register(communicationModel);
		return s;
	}

	@Override
	protected boolean createUserInterface() {
		UiSchema schema = new UiSchema();
		schema.add(Truck.class, new RGB(0, 0, 255));
		schema.add(Package.class, new RGB(255, 0, 0));
		schema.add(DeliveryLocation.class, new RGB(0, 255, 0));

		View.startGui(getSimulator(), 3, new ObjectRenderer(roadModel, schema,
				false));

		return true;
	}

	@Override
	protected boolean handleAddTruck(Event e) {
		Truck truck = new Truck("Truck-" + truckID++,
				graph.getRandomNode(getSimulator().getRandomGenerator()), 70000);
		getSimulator().register(truck);
		TruckAgent agent = new TruckAgent(truck, truckRadius, 1, queueSize);
		agent.addListener(cnetLogger, TruckAgent.EventType.values());
		getSimulator().register(agent);
		return true;
	}

	@Override
	protected boolean handleAddPackage(Event e) {
		Point pl = graph.getRandomNode(getSimulator().getRandomGenerator());
		DeliveryLocation dl = new DeliveryLocation(
				graph.getRandomNode(getSimulator().getRandomGenerator()));
		// getSimulator().register(pl);
		getSimulator().register(dl);
		Package p = new Package("Package-" + packageID++, pl, dl);
		getSimulator().register(p);
		PackageAgent agent = new PackageAgent(p, packageRadius, 1, cfpDeadline);
		agent.addListener(cnetLogger, PackageAgent.EventType.values());
		getSimulator().register(agent);
		return true;
	}

	private static void setDefaultMaxSpeed(Graph<MultiAttributeEdgeData> graph,
			double maxSpeed) {
		List<Connection<MultiAttributeEdgeData>> connections = graph
				.getConnections();
		for (Connection<MultiAttributeEdgeData> c : connections) {
			MultiAttributeEdgeData ed = c.getEdgeData();
			double speed = ed.getMaxSpeed();
			if (Double.isNaN(speed)) {
				ed.setMaxSpeed(maxSpeed);
				c.setEdgeData(ed);
			}
		}
	}

	private static void fixConnectionLengths(Graph<MultiAttributeEdgeData> graph) {
		List<Connection<MultiAttributeEdgeData>> connections = graph
				.getConnections();
		for (Connection<MultiAttributeEdgeData> c : connections) {
			double dist = Point.distance(c.from, c.to);
			c.setEdgeData(new MultiAttributeEdgeData(dist, c.getEdgeData()
					.getMaxSpeed()));
		}
	}
}
