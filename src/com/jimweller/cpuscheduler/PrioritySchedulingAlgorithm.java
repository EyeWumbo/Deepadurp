/** PrioritySchedulingAlgorithm.java
 * 
 * A single-queue priority scheduling algorithm.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PrioritySchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
    private boolean preemptive;

    private PriorityQueue<Process> processes = new PriorityQueue<Process>(16, new ProcessPriorityComparator());
    private Process currentProcess;
    
    PrioritySchedulingAlgorithm(){

    }

    
    private class ProcessPriorityComparator implements Comparator<Process>{

		@Override
		public int compare(Process a, Process b) {
			// TODO Auto-generated method stub
			if(a.priority > b.priority){
				return 1;
			}
			else if(a.priority == b.priority){
				return 0;
			}
			return -1;
		}
    	
    }
    
    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
    	processes.add(p);
    	super.addJob(p);
    	if(currentProcess == null){
    		currentProcess = processes.peek();
    	}
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
    	super.removeJob(p);
    	return processes.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
    	for(Process p : processes){
    		this.removeJob(p);
    		otherAlg.addJob(p);
    		
    	}
    	processes.clear();
    }


    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime){
    	if(preemptive){
    		currentProcess = processes.peek();
    	}
    	if(!preemptive && currentProcess.isFinished()){
    		currentProcess = processes.peek();
    	}
    	if(currentProcess.isFinished()){
    		currentProcess = null;
    	}
    	return currentProcess;
    }
    
    public String getName(){
	return "Single-queue Priority";
    }

    /**
     * @return Value of preemptive.
     */
    public boolean isPreemptive(){
	return preemptive;
    }
    
    /**
     * @param v  Value to assign to preemptive.
     */
    public void setPreemptive(boolean  v){
	preemptive = v;
    }
}