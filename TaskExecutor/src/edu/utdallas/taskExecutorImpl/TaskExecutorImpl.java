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
		
		for(int i = 0; i < N; i++)
		{
			TaskRunner Runner = new TaskRunner(blockingFifoQueue);			
			Runner.run();
			//Thread tmp = new Thread(Runner);
			//tmp.start();
			RunnerList.add(Runner);
		}
		
	}
		

	@Override
	public void addTask(Task task) {
		
		try {
			blockingFifoQueue.put(task);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}

}
