package com.jimweller.cpuscheduler;

public class MultiLevelPriorityAlgorithm extends RoundRobinSchedulingAlgorithm{
	
	private BaseSchedulingAlgorithm[] algorithms = new BaseSchedulingAlgorithm[3];
	private BaseSchedulingAlgorithm current;
	private long lastTime;
	
	public MultiLevelPriorityAlgorithm(){		
		algorithms[0] = new RoundRobinSchedulingAlgorithm();
		algorithms[1] = new RoundRobinSchedulingAlgorithm();
		algorithms[2] = new FCFSSchedulingAlgorithm();
	}
	
	@Override
	public void setQuantum(int quantum){
		((RoundRobinSchedulingAlgorithm)(algorithms[0])).setQuantum(quantum);
		((RoundRobinSchedulingAlgorithm)(algorithms[1])).setQuantum(quantum * 2);
	}
	
	@Override
	public void addJob(Process p){
		if(p.priority < 4){
			algorithms[0].addJob(p);
		}
		else if(p.priority < 7){
			algorithms[1].addJob(p);
		}
		else{
			algorithms[2].addJob(p);	
		}		
		super.addJob(p);
	}
	
	@Override
	public boolean removeJob(Process p){
		super.removeJob(p);
		if(p.priority < 4){
			return algorithms[0].removeJob(p);
		}
		else if(p.priority < 7){
			return algorithms[1].removeJob(p);
		}
		return algorithms[2].removeJob(p);		
	}
	
	@Override
	public Process getNextJob(long currentTime){
		for(int i = 0; i < 3; i ++){
			
			if(algorithms[i].getNextJob(currentTime) != null){			
				return algorithms[i].getNextJob(currentTime);
			}
		}
		return null;
	}

	@Override
	public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		// TODO Auto-generated method stub
		algorithms[0].transferJobsTo(otherAlg);
		algorithms[1].transferJobsTo(otherAlg);
		algorithms[2].transferJobsTo(otherAlg);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Multi-Level Priority";
	}
	
}
