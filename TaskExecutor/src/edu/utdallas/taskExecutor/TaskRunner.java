package edu.utdallas.taskExecutor;
import java.util.concurrent.BlockingQueue;   // Make sure to delete it after Daren add in his part
import java.util.concurrent.PriorityBlockingQueue;

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
				
				try {
		            newTask.execute();
		        }
		        catch(Throwable th) {
		           // Log (e.g. print exception’s message to console) 
		           // and drop any exceptions thrown by the task’s
		           // execution.
		        }
			} 
			catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    }
	}
	


}
