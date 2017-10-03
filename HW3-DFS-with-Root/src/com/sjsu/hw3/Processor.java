package com.sjsu.hw3;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Swikar Patel
 *
 */

public class Processor implements Observer {
	// Each processor has a message buffer to store messages
	private Buffer messageBuffer;
	private Integer id;
	private List<Processor> children; // List of children finalized for the processor
	private Processor parent;

	// Initially it will be all the neighbors of a Processor. When a graph is
	// created this list is populated
	List<Processor> unexplored;

	public Processor(int processorId) {
		messageBuffer = new Buffer();
		id = processorId; // Assigning a user defined id for each processor
		children = new ArrayList<>();
		unexplored = new ArrayList<>();
		// Each processor is observing itself
		messageBuffer.addObserver(this);
	}

	/**
	 * This method removes a processor from the connected graph once it is done
	 * sending message to it.
	 *
	 * @param toBeRemoved
	 *            the processor object reference to be removed from graph
	 */
	private void removeFromUnexplored(Processor toBeRemoved) {
		this.unexplored.remove(toBeRemoved);
	}

	/**
	 * This method will add a message to this processors buffer. Other processors
	 * will invoke this method to send a message to this Processor.
	 *
	 * @param message
	 *            The type to be sent as the message
	 * @param sender
	 *            Reference to the sender processor
	 */
	public void sendMessgeToMyBuffer(Message message, Processor sender) {
		messageBuffer.setMessage(message, sender);
	}

	/**
	 * This is analogous to receive method. It receives the message when the message
	 * is published by some processor. Based on the type of message, it takes
	 * appropriate action.
	 *
	 * @param observable
	 * @param arg
	 */
	public void update(Observable observable, Object arg) {

		Processor sender = messageBuffer.getSender();

		System.out.println("Processor " + this.getId() + " received message " + this.messageBuffer.getMessage()
				+ " from Processor " + sender.getId());

		switch (this.messageBuffer.getMessage()) {
		case M:
			// Received a message from a processor. If no parent is set for this, set the
			// processor as current processor's parent. Else. just send an already message.
			if (this.getParent() == null) {
				System.out.println(
						"Processor " + this.getId() + " setting Processor " + sender.getId() + " as its parent");
				this.setParent(sender);
				removeFromUnexplored(sender);
				explore();
			} else {
				removeFromUnexplored(sender);
				sender.sendMessgeToMyBuffer(Message.ALREADY, this);

			}
			break;

		case ALREADY:
			// Received already message from a processor. Moving on and exploring other
			// processors.
			this.explore();
			break;

		case PARENT:
			// A processor acknowledged this processor as its parent. Adding it to children
			// list and moving on.
			this.children.add(sender);
			this.explore();
			break;

		default:
			break;
		}
	}

	/**
	 * This method sends the message to all it's connected processors. This also
	 * serves as a terminating condition in case all the processors have been
	 * visited.
	 */
	public void explore() {

		System.out.println("\nExploring processors for Processor " + this.getId());

		if (!this.unexplored.isEmpty()) {
			System.out.println(
					"The unexplored processors for Processor " + this.getId() + " are " + unexplored.toString());
			// Taking the first processor from the list of connected processors, removing it
			// from connected processor list and sending the message to it.
			Processor ptx = unexplored.get(0);
			removeFromUnexplored(ptx);
			System.out.println("Sending message " + Message.M + " to Processor " + ptx.getId());
			ptx.sendMessgeToMyBuffer(Message.M, this);  // this == who call explorer()
		} else {
			System.out.println("Processor " + this.getId() + " has no unexplored processors left.");
			if (this.getParent() != this) {
				this.getParent().sendMessgeToMyBuffer(Message.PARENT, this);
			}
		}

	}

	public Processor getParent() {
		return parent;
	}

	public void setParent(Processor parent) {
		this.parent = parent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Processor " + id;
	}

	public List<Processor> getChildren() {
		return children;
	}

	public void setChildren(List<Processor> children) {
		this.children = children;
	}

}
