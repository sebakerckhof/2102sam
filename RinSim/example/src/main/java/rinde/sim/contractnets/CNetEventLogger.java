package rinde.sim.contractnets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rinde.sim.event.Event;
import rinde.sim.event.Listener;

public class CNetEventLogger implements Listener {

private static final Logger LOGGER = LoggerFactory.getLogger(CNetEventLogger.class);
	
	@Override
	public void handleEvent(Event e) {
		LOGGER.info(e.toString() + " received from " + e.getIssuer().toString() + ".");
	}
}
