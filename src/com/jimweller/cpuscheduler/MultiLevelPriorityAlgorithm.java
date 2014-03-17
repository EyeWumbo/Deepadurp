package com.jimweller.cpuscheduler;

public class MultiLevelPriorityAlgorithm extends RoundRobinSchedulingAlgorithm implements
		OptionallyPreemptiveSchedulingAlgorithm {

	private BaseSchedulingAlgorithm[] algorithms = new BaseSchedulingAlgorithm[3];
	private int index = -1;
	private boolean preemptive = false;

	public MultiLevelPriorityAlgorithm() {
		algorithms[0] = new RoundRobinSchedulingAlgorithm();
		algorithms[1] = new RoundRobinSchedulingAlgorithm();
		algorithms[2] = new FCFSSchedulingAlgorithm();
	}

	@Override
	public void setQuantum(int quantum) {
		((RoundRobinSchedulingAlgorithm) (algorithms[0])).setQuantum(quantum);
		((RoundRobinSchedulingAlgorithm) (algorithms[1])).setQuantum(quantum * 2);
	}

	@Override
	public void addJob(Process p) {
		if (p.priority < 4) {
			algorithms[0].addJob(p);
		}
		else if (p.priority < 7) {
			algorithms[1].addJob(p);
		}
		else {
			algorithms[2].addJob(p);
		}
		super.addJob(p);
	}

	@Override
	public boolean removeJob(Process p) {
		super.removeJob(p);
		if (p.priority < 4) {
			return algorithms[0].removeJob(p);
		}
		else if (p.priority < 7) {
			return algorithms[1].removeJob(p);
		}
		return algorithms[2].removeJob(p);
	}

	@Override
<<<<<<< HEAD
	public Process getNextJob(long currentTime) {

		boolean empty = true;
		if (isJobFinished()) {
			for (int i = 0; i < 3; i++)
			{
				if (algorithms[i].procList.size() == 0)
				{
					empty = false;
					break;
				}
=======
	public Process getNextJob(long currentTime){
		if(currentJob != null && !currentJob.isFinished() && !preemptive){
			if(algorithms[2].contains(currentJob)){
				return currentJob;
			}
		}
//		if(!isPreemptive()){
//			if(currentJob != null){
//				return currentJob;
//			}
//		}
		for(int i = 0; i < 3; i ++){			
			if(algorithms[i].getNextJob(currentTime) != null){			
				currentJob = algorithms[i].getNextJob(currentTime);
				return currentJob;
>>>>>>> refs/remotes/origin/master
			}
		}
		else
		{
			empty = false;
		}
		if (empty)
		{
			index = -1;
			activeJob = null;
			return null;
		}
		// at this point, sure it is not empty
		if (index < 0 && !isPreemptive())
		{
			for (int i = 0; i < 3; i++) {
				if (algorithms[i].getNextJob(currentTime) != null) {
					activeJob = algorithms[i].getNextJob(currentTime);
					index = i;
					return activeJob;
				}
			}
		}
		else if (index >= 0 && !isPreemptive()) {
			if (index > 1)
				return activeJob;
			else
			{
				activeJob = algorithms[index].getNextJob(currentTime);
				return activeJob;
			}
		}

		if (isPreemptive()) {
			for (int i = 0; i < 3; i++) {
				if (algorithms[i].getNextJob(currentTime) != null) {
					activeJob = algorithms[i].getNextJob(currentTime);
					index = i;
					return activeJob;
				}
			}
		}
		return activeJob;
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

	@Override
	public boolean isPreemptive() {
		// TODO Auto-generated method stub
		return preemptive;
	}

	@Override
	public void setPreemptive(boolean v) {
		// TODO Auto-generated method stub
		preemptive = v;
	}

}
