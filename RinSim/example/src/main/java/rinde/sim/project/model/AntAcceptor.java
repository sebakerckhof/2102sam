package rinde.sim.project.model;

/**
 * AntAcceptor
 * An Ant acceptor is an object which can be visited by an ant agent
 * This is part of the visitor design pattern
 */
public interface AntAcceptor extends DMASUser{
	
	/**
	 * Accept ant and visit by self (visitor pattern)
	 * @param a	Ant Agent
	 */
	public void accept(AntAgent a);
}
