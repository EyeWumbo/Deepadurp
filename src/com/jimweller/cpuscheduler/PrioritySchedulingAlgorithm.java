/** PrioritySchedulingAlgorithm.java
 * 
 * A single-queue priority scheduling algorithm.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.ArrayList;
import java.util.Comparator;

public class PrioritySchedulingAlgorithm extends BaseSchedulingAlgorithm implements
		OptionallyPreemptiveSchedulingAlgorithm {
	private boolean preemptive;

	private ArrayList<Process> processes = new ArrayList<Process>();
	private Process currentProcess;
	private ProcessPriorityComparator comparator = new ProcessPriorityComparator();
	PrioritySchedulingAlgorithm() {

	}

	private class ProcessPriorityComparator implements Comparator<Process> {

		@Override
		public int compare(Process a, Process b) {
			// TODO Auto-generated method stub
			if (a.priority > b.priority) {
				return 1;
			}
			else if (a.priority == b.priority) {
				return 0;
			}
			return -1;
		}

	}

	/** Add the new job to the correct queue. */
	public void addJob(Process p) {
		processes.add(p);
		super.addJob(p);
//		if (currentProcess == null) {
//			currentProcess = processes.peek();
//		}
	}

	/** Returns true if the job was present and was removed. */
	public boolean removeJob(Process p) {
		super.removeJob(p);
		return processes.remove(p);
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
	 * Returns the next process that should be run by the CPU, null if none
	 * available.
	 */
	public Process getNextJob(long currentTime) {
		Process p1 = null;
		Process p2 = null;
		if(!isJobFinished() && !isPreemptive())
			return activeJob;
		p1 = processes.get(0);
		for(int i = 0; i < processes.size(); ++i)
		{
			p2 = processes.get(i);
			if(comparator.compare(p1, p2) < 0)
			{
				p1 = p2;
			}
		}
		//
		this.activeJob = p1;
		return activeJob;
//		if (preemptive) {
//			currentProcess = processes.peek();
//		}
//		if (!preemptive && currentProcess.isFinished()) {
//			currentProcess = processes.peek();
//		}
//		if (currentProcess.isFinished()) {
//			currentProcess = null;
//		}
//		return currentProcess;
	}

	public String getName() {
		return "Single-queue Priority";
	}

	/**
	 * @return Value of preemptive.
	 */
	public boolean isPreemptive() {
		return preemptive;
	}

	/**
	 * @param v
	 *            Value to assign to preemptive.
	 */
	public void setPreemptive(boolean v) {
		preemptive = v;
	}
}