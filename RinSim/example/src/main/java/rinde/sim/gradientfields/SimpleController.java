package rinde.sim.gradientfields;

import org.apache.commons.math.random.MersenneTwister;
import org.eclipse.swt.graphics.RGB;

import rinde.sim.core.Simulator;
import rinde.sim.core.graph.Graph;
import rinde.sim.core.graph.MultiAttributeEdgeData;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.event.Event;
import rinde.sim.gradientfields.packages.DeliveryLocation;
import rinde.sim.gradientfields.packages.Passenger;
import rinde.sim.gradientfields.taxi.*;
import rinde.sim.gradientfields.packages.*;
import rinde.sim.scenario.ConfigurationException;
import rinde.sim.scenario.Scenario;
import rinde.sim.scenario.ScenarioController;
import rinde.sim.serializers.DotGraphSerializer;
import rinde.sim.serializers.SelfCycleFilter;
import rinde.sim.ui.View;
import rinde.sim.ui.renderers.ObjectRenderer;
import rinde.sim.ui.renderers.UiSchema;

public class SimpleController extends ScenarioController{

	String map;
	
	private RoadModel roadModel;
	
	private int truckID = 0;
	private int packageID = 0;
	private Graph<MultiAttributeEdgeData> graph;
	
	public SimpleController(Scenario scen, int numberOfTicks, String map) throws ConfigurationException {
		super(scen, numberOfTicks);
		this.map = map;
		
		initialize();
	}

	@Override
	protected Simulator createSimulator() throws Exception {
		try {
			graph = DotGraphSerializer.getMultiAttributeGraphSerializer(new SelfCycleFilter()).read(map);
		} catch (Exception e) {
			throw new ConfigurationException("e:", e);
		}
		roadModel = new RoadModel(graph);

		MersenneTwister rand = new MersenneTwister(123);
		Simulator s = new Simulator(rand, 10000);
		s.register(roadModel);
		return s;	
	}
	
	@Override
	protected boolean createUserInterface() {
		UiSchema schema = new UiSchema();
		schema.add(Taxi.class, new RGB(0,0,255));
		schema.add(Passenger.class, new RGB(255,0,0));
		schema.add(DeliveryLocation.class, new RGB(0,255,0));

		View.startGui(getSimulator(), 3, new ObjectRenderer(roadModel, schema, false));

		return true;
	}

	@Override
	protected boolean handleAddTruck(Event e) {
		Taxi taxi = new Taxi("Truck-"+truckID++, graph.getRandomNode(getSimulator().getRandomGenerator()), 7);
		getSimulator().register(taxi);
		TaxiAgent agent = new TaxiAgent(taxi, 5);
		getSimulator().register(agent);
		return true;
	}	

	@Override
	protected boolean handleAddPackage(Event e){
		Point pl = graph.getRandomNode(getSimulator().getRandomGenerator());
		DeliveryLocation dl = new DeliveryLocation(graph.getRandomNode(getSimulator().getRandomGenerator()));
		getSimulator().register(pl);
		getSimulator().register(dl);
		
		Passenger p = new Passenger("Package-"+packageID++, pl, dl);
		getSimulator().register(p);
		PassengerAgent agent = new PassengerAgent(p);
		getSimulator().register(agent);
		return true;
	}

}
