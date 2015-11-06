package edu.utdallas.taskExecutor;

import edu.utdallas.blockingFIFO.BlockingTaskQueue;

public class TaskRunner implements Runnable
{
	/*
	 *  1) obtains a Task from the FIFO
	 *  2) executes the TASK by calling the execute() method 
	 *  
	 */
	
	private BlockingTaskQueue blockingFifoQueue;
	
	public TaskRunner(BlockingTaskQueue blockingFifoQueue) {
		this.blockingFifoQueue = blockingFifoQueue;
	}
	
	public void run() 
	{
	    while(true) {
	        // take() blocks if queue is empty
	        Task newTask;
			try {
				newTask = blockingFifoQueue.take();
	            newTask.execute();
			} 
			catch (Throwable e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
	        
	    }
	}
	


}
