/** SJFSchedulingAlgorithm.java
 * 
 * A shortest job first scheduling algorithm.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;


public class SJFSchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
    private boolean preemptive;
    
    private PriorityQueue<Process> processes = new PriorityQueue<Process>(16, new JobLengthPriorityComparator());
    private Process currentProcess;
    
    SJFSchedulingAlgorithm(){
    }

    
    private class JobLengthPriorityComparator implements Comparator<Process>{

		@Override
		public int compare(Process a, Process b) {
			// TODO Auto-generated method stub
			if(a.burst > b.burst){
				return 1;
			}
			else if(a.burst == b.burst){
				return 0;
			}
			return -1;
		}
    	
    }
    
    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
    	super.addJob(p);
    	processes.add(p);
    	if(currentProcess == null){
    		currentProcess = processes.peek();
    	}
//    	if(processes.peek().equals(p)){
//    		if(currentProcess == null){
//    			currentProcess = p;
//    		}
//    	}
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
    	if(currentProcess == null){
    		return currentProcess;
    	}
    	if(!preemptive && currentProcess.isFinished()){
    		currentProcess = processes.peek();
    	}
    	else if(preemptive){
    		currentProcess = processes.peek();
    	}
    	return currentProcess;
    }

    public int memUsage(){
    	int usage = 0;
    	for(Process p : processes){
    		usage += p.memory;
    	}
    	return usage;
    }
    
    public String getName(){
	return "Shortest job first";
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