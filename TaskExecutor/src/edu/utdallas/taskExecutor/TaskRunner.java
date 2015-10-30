package edu.utdallas.taskExecutor;
import java.util.concurrent.BlockingQueue;   // Make sure to delete it after Daren add in his part
import java.util.concurrent.PriorityBlockingQueue;

public class TaskRunner implements Runnable
{
	/*
	 *  1) obtains a Task from the FIFO
	 *  2) executes the TASK by calling the execute() method 
	 *  
	 */
	
	public final static int QUEUE_SIZE = 20;
	
	private BlockingQueue <Task>  blockingFifoQueue = new PriorityBlockingQueue <Task>(QUEUE_SIZE) ;
	
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
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    }
	}
	


}
