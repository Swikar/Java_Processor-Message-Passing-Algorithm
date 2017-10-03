package com.sjsu.hw3;

import java.util.Observable;

/**
 *
 * @author Swikar Patel
 *
 */
public class Buffer extends Observable {
	private Message message;
	private Processor sender;

	public Buffer() {
		// Create an empty Buffer
	}

	public Buffer(Message message) {
		this.message = message;
	}

	public Message getMessage() {
		return message;
	}

/**
 *
 * @param message
 * @param sender
 */
	public void setMessage(Message message, Processor sender) {
		this.message = message;
		this.sender = sender;
		setChanged();
		notifyObservers();
	}

	public Processor getSender() {
		return this.sender;
	}
}
