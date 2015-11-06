package edu.utdallas.taskExecutorImpl;
import edu.utdallas.blockingFIFO.BlockingTaskQueue;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;
import edu.utdallas.taskExecutor.TaskRunner;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;  //delete later
import java.util.concurrent.PriorityBlockingQueue;

public class TaskExecutorImpl implements TaskExecutor{
	
	private BlockingTaskQueue blockingFifoQueue;
	private ArrayList<TaskRunner> RunnerList;
	
	public TaskExecutorImpl(int N)
	{
		blockingFifoQueue = new BlockingTaskQueue(N);
		RunnerList = new ArrayList<TaskRunner>();
		
		for(TaskRunner Runner : RunnerList)
		{
			Runner = new TaskRunner(blockingFifoQueue);			
			Thread tmp = new Thread(Runner);
			tmp.start();
			
		}
		
	}
		

	@Override
	public void addTask(Task task) {
		
		try {
			blockingFifoQueue.put(task);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}

}
