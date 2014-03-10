/** FCFSSchedulingAlgorithm.java
 * 
 * A first-come first-served scheduling algorithm.
 *
 * @author: Steven Zhang
 * Steven Zhang
 * Steven Zhang
 * and Special Guest:
 * Steven Zhang
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class FCFSSchedulingAlgorithm extends BaseSchedulingAlgorithm {

	LinkedList<Process> processes = new LinkedList<Process>();
	Process currentProcess;
	
    FCFSSchedulingAlgorithm(){
    	
    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
    	processes.add(p);
    	if(currentProcess == null){
    		currentProcess = p;
    	}
    	super.addJob(p);
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
    		otherAlg.addJob(p);
    	}
    	processes.clear();
    }


    public boolean shouldPreempt(long currentTime){
    	return true;
    }

    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime){
    	if(processes.isEmpty()){
    		return null;
    	}
    	return processes.peek();
    }
    
    public String getName(){
	return "First-come first-served";
    }
}