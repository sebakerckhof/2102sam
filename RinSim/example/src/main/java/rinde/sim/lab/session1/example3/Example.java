package rinde.sim.lab.session1.example3;


import org.apache.commons.math.random.MersenneTwister;
import org.eclipse.swt.graphics.RGB;

import rinde.sim.core.Simulator;
import rinde.sim.core.graph.Graph;
import rinde.sim.core.graph.MultiAttributeEdgeData;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.communication.CommunicationModel;
import rinde.sim.serializers.DotGraphSerializer;
import rinde.sim.serializers.SelfCycleFilter;
import rinde.sim.ui.View;
import rinde.sim.ui.renderers.ObjectRenderer;
import rinde.sim.ui.renderers.UiSchema;

/**
 * 
 */
public class Example {

	public static void main(String[] args) throws Exception {
		final String MAP_DIR = "../core/files/maps/";
		MersenneTwister rand = new MersenneTwister(123);
		Simulator simulator = new Simulator(rand, 1000);
		Graph<MultiAttributeEdgeData> graph = DotGraphSerializer.getMultiAttributeGraphSerializer(new SelfCycleFilter()).read(MAP_DIR + "leuven-simple.dot");
		
		RoadModel roadModel = new RoadModel(graph);
		simulator.register(roadModel);	
	
		//Create and register a new communication model
		CommunicationModel communicationModel = new CommunicationModel(rand, true);
		simulator.register(communicationModel);
		
		simulator.configure();
		
		for (int i = 0; i < 5; i++) {
			RandomWalkAgent agent = new RandomWalkAgent("Truck" +i, 20, graph.getRandomNode(rand));
			simulator.register(agent);
		}
		
		DepotAgent depotAgent = new DepotAgent(graph.getRandomNode(rand));
		simulator.register(depotAgent);
		
		UiSchema schema = new UiSchema();
		schema.add(RandomWalkAgent.class, new RGB(255,0,0));
		schema.add(DepotAgent.class, new RGB(0,0,255));
		
		View.startGui(simulator, 5, new ObjectRenderer(roadModel, schema, false));
	}
}
