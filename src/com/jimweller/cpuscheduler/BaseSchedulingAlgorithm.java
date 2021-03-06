/** BaseSchedulingAlgorithm.java
 * 
 * An abstract scheduling algorithm for others to inherit from.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public abstract class BaseSchedulingAlgorithm implements SchedulingAlgorithm {
	/** The currently running process, null if none. */
	protected Process activeJob;
	protected int currentMemory;
	int memoryCapacity;
	HashSet<Process> procList = new HashSet<Process>();

	/** Add the new job to the correct queue. */
	public void addJob(Process p) {
		System.out.println(this.getName() + "adding process with memory " + p.memory + " currentMemory: " + currentMemory);
		procList.add(p);
		this.currentMemory += p.memory;
		System.out.println("memory is now " + currentMemory);
	}

	/** Returns true if the job was present and was removed. */
	public boolean removeJob(Process p) {
		System.out.println(this.getName() + "removing process with memory " + p.memory + " currentMemory: " + currentMemory);
		this.currentMemory -= p.memory;
		System.out.println("memory is now " + currentMemory);
		procList.remove(p);
		return true;
	}

	/**
	 * Transfer all the jobs in the queue of a SchedulingAlgorithm to another,
	 * such as when switching to another algorithm in the GUI
	 */
	public abstract void transferJobsTo(SchedulingAlgorithm otherAlg);

	/**
	 * Returns the next process that should be run by the CPU, null if none
	 * available.
	 */
	public abstract Process getNextJob(long currentTime);

	/** Return a human-readable name for the algorithm. */
	public abstract String getName();

	/** Returns true if the current job is finished or there is no such job. */
	public boolean isJobFinished() {
		if (activeJob != null)
			return activeJob.isFinished();
		else
			return true;
	}

	/**  */
	public boolean meetsMemoryConstraints(Process p) {
		return !((currentMemory + p.memory) > memoryCapacity);
			
	}

	public void setMemoryConstraint(int newConstraint) {
		memoryCapacity = newConstraint;
	}

	public int memUsage() {
		return currentMemory;
	}

	public boolean contains(Process p) {
		return procList.contains(p);
	}
}
