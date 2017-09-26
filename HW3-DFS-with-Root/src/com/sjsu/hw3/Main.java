package com.sjsu.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author Swikar Patel
 *
 */
public class Main {
	private Map<Processor, List<Processor>> graphOfConnectedProcessors;
	private List<Processor> allProcessors;

	private Processor p0, p1, p2, p3, p4, p5;

	public Main() {

		init();

	}

	/**
	 * This method initiates the algorithm to construct a spanning tree using Depth
	 * First Search Algorithm for a graph of connected processors.
	 * 
	 * @param args
	 *            the first and only argument will be an integer denoting the index
	 *            of processor that needs to be selected as root
	 */
	public static void main(String args[]) {
		Main m = new Main(); // The graph and processor list will be initialized here

		// Reading user input
		System.out.println("\nEnter the id of processor to be selected as root");
		Scanner scanner = new Scanner(System.in);
		int selectedProcessorId = scanner.nextInt();
		scanner.close(); // Always close the scanner

		// Choosing a processor as a Root Processor based on user input
		Processor rootProcessor = null;
		for (Processor processor : m.allProcessors) {
			if (processor.getId() == selectedProcessorId) {
				rootProcessor = processor;
				System.out.println("\nProcessor " + processor.getId() + " selected as root processor.");
				break;
			}
		}

		// Root processor wakes up, sets itself as parent and starts the spanning tree
		// construction
		rootProcessor.setParent(rootProcessor);
		rootProcessor.explore();

		// Once the explore process completes, it is assumed that all the processors
		// have been visited and the algorithm has been completed successfully.
		// At this point, we just print the newly formed spanning tree.
		System.out.println("\nSpanning tree has been constructed. It is as follows:");
		for (Processor p : m.allProcessors) {
			System.out.println("Processor " + p.getId() + " -----> " + p.getChildren());
		}
	}

	/**
	 * This method initializes the processor instances and created an in-memory
	 * graph of all the connected processors.
	 */
	private void init() {

		System.out.println("Initializing processors...");

		allProcessors = new ArrayList<Processor>();

		// Getting all the processors up and running
		p0 = new Processor(0);
		allProcessors.add(p0);
		System.out.println("Processor " + p0.getId() + " added.");
		p1 = new Processor(1);
		allProcessors.add(p1);
		System.out.println("Processor " + p1.getId() + " added.");
		p2 = new Processor(2);
		allProcessors.add(p2);
		System.out.println("Processor " + p2.getId() + " added.");
		p3 = new Processor(3);
		allProcessors.add(p3);
		System.out.println("Processor " + p3.getId() + " added.");
		p4 = new Processor(4);
		allProcessors.add(p4);
		System.out.println("Processor " + p4.getId() + " added.");
		p5 = new Processor(5);
		allProcessors.add(p5);
		System.out.println("Processor " + p5.getId() + " added.");

		System.out.println("\nTotal number of available processor nodes are " + allProcessors.size() + ".");

		// Populating the graph with all the connected processors
		graphOfConnectedProcessors = new HashMap<Processor, List<Processor>>();

		System.out.println("\nProcessor associations are:");

		p0.unexplored = new ArrayList<Processor>();
		p0.unexplored.add(p1);
		p0.unexplored.add(p2);
		p0.unexplored.add(p3);
		graphOfConnectedProcessors.put(p0, p0.unexplored);
		System.out.println(p0 + " -----> " + p0.unexplored.toString());

		p1.unexplored = new ArrayList<Processor>();
		p1.unexplored.add(p0);
		p1.unexplored.add(p2);
		p1.unexplored.add(p4);
		graphOfConnectedProcessors.put(p1, p1.unexplored);
		System.out.println(p1 + " -----> " + p1.unexplored.toString());

		p2.unexplored = new ArrayList<Processor>();
		p2.unexplored.add(p0);
		p2.unexplored.add(p1);
		p2.unexplored.add(p5);
		graphOfConnectedProcessors.put(p2, p2.unexplored);
		System.out.println(p2 + " -----> " + p2.unexplored.toString());

		p3.unexplored = new ArrayList<Processor>();
		p3.unexplored.add(p0);
		graphOfConnectedProcessors.put(p3, p3.unexplored);
		System.out.println(p3 + " -----> " + p3.unexplored.toString());

		p4.unexplored = new ArrayList<Processor>();
		p4.unexplored.add(p1);
		p4.unexplored.add(p5);
		graphOfConnectedProcessors.put(p4, p4.unexplored);
		System.out.println(p4 + " -----> " + p4.unexplored.toString());

		p5.unexplored = new ArrayList<Processor>();
		p5.unexplored.add(p2);
		p5.unexplored.add(p4);
		graphOfConnectedProcessors.put(p5, p5.unexplored);
		System.out.println(p5 + " -----> " + p5.unexplored.toString());
	}
}
