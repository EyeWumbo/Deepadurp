/** RoundRobinSchedulingAlgorithm.java
 * 
 * A scheduling algorithm that randomly picks the next job to go.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class RoundRobinSchedulingAlgorithm extends BaseSchedulingAlgorithm {

	/** the timeslice each process gets */
	private int quantum, index, currentQuantum;
	private ArrayList<Process> processes = new ArrayList<Process>();
	// private LinkedList<Process> processes = new LinkedList<Process>();
	private long previousTime, timeSinceLast;

	RoundRobinSchedulingAlgorithm() {
		index = 0;
		currentQuantum = 0;
	}

	/** Add the new job to the correct queue. */
	public void addJob(Process p) {
		processes.add(p);
		super.addJob(p);
	}

	/** Returns true if the job was present and was removed. */
	public boolean removeJob(Process p) {
		super.removeJob(p);
		if(processes.remove(p) && processes.size() > 0)
		{
			currentQuantum = 0;
			//index = ++index % processes.size();
			return true;
		}
		return false;
	}

	/**
	 * Transfer all the jobs in the queue of a SchedulingAlgorithm to another,
	 * such as when switching to another algorithm in the GUI
	 */
	public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		for (Process p : processes) {
			this.removeJob(p);
			otherAlg.addJob(p);
		}
		processes.clear();
	}

	/**
	 * Get the value of quantum.
	 * 
	 * @return Value of quantum.
	 */
	public int getQuantum() {
		return quantum;
	}

	/**
	 * Set the value of quantum.
	 * 
	 * @param v
	 *            Value to assign to quantum.
	 */
	public void setQuantum(int v) {
		this.quantum = v;
	}

	/**
	 * Returns the next process that should be run by the CPU, null if none
	 * available.
	 */
	public Process getNextJob(long currentTime) {
		if (processes.isEmpty()) {
			index = 0;
			currentQuantum = 0;
			return null;
		}

		if (++currentQuantum == quantum)
		{
			index++;
			if(index == processes.size()) index = 0;
			//index = ++index % processes.size();
			currentQuantum = 0;
		}
		if (isJobFinished())
		{
			currentQuantum = 0;
		}
		if(index == processes.size()) index = 0;
		this.activeJob = processes.get(index);
		return activeJob;

		// timeSinceLast += (currentTime - previousTime) % quantum;
		// if (timeSinceLast > quantum) {
		// index++;
		// timeSinceLast -= quantum;
		// }
		// if (index >= processes.size()) {
		// index = 0;
		// }
		// previousTime = currentTime;
		// return processes.get(index);
	}

	public String getName() {
		return "Round Robin";
	}
}