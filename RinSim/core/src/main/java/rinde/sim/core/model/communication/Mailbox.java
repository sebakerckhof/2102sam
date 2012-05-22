package rinde.sim.core.model.communication;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Simple mailbox with infinite capacity that can be use to serve
 * {@link CommunicationUser#receive(Message)} method.
 * 
 * @author Bartosz Michalik <bartosz.michalik@cs.kuleuven.be>
 * @since 2.0
 */
public class Mailbox {
	protected Queue<Message> box;

	public Mailbox() {
		box = new LinkedList<Message>();
	}

	/**
	 * Insert a msg in the mailbox. If the message is 
	 * already present in the box it is not inserted again. 
	 * @param msg
	 */
	public void receive(Message msg) {
		if(msg == null) throw new NullPointerException();
		if(box.contains(msg)) return;
		box.add(msg);
	}

	/**
	 * Getting messages empties the mailbox
	 * 
	 * @return
	 */
	public Queue<Message> getMessages() {
		Queue<Message> messages = box;
		box = new LinkedList<Message>();
		return messages;
	}
}
