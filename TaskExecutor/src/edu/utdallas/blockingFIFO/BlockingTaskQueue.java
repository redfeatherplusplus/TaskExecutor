package edu.utdallas.blockingFIFO;

import java.util.concurrent.Semaphore;

import edu.utdallas.taskExecutor.Task;

public class BlockingTaskQueue {
	//defined constants
	public static final int DEFAULT_QUEUE_SIZE = 20;
	
	private Task[] queue;                 //FIFO queue of tasks
	private static Semaphore queueLocks;  //ensures queue bounds maintained
	private static Semaphore modifying;   //indicates if we are currently modifying the queue
	private static int in;                //index to add at 
	private static int out;               //index to take from
	
	public BlockingTaskQueue(int size) {
		//create a BlockingTaskQueue of default size
		queue = new Task[size];
		queueLocks  = new Semaphore(size, true);
		modifying = new Semaphore(1);
		in = 0;
		out = 0;
	}
	
	public BlockingTaskQueue() {
		//create a BlockingTaskQueue of default size
		queue = new Task[DEFAULT_QUEUE_SIZE];
		queueLocks  = new Semaphore(DEFAULT_QUEUE_SIZE, true);
		modifying = new Semaphore(1);
		in = 0;
		out = 0;
	}
	
	//check if see if throws throwable is correct
	public void add(Task task) throws Throwable {
		//ensure that we are not currently modifying the queue
		modifying.acquire();  
		
		//check if there are any available queueLocks, if not release modifying lock
		//note that having queueLocks.acquire() throw an exception is undesirable
		//as modifying.release() would not be executed
		if (queueLocks.availablePermits() > 0) {
			//acquire a lock from the queueLocks and add to the last index
			queueLocks.acquire();
			queue[in] = task;
			in = (in + 1) % queue.length;
		}
		
		//notify that we are done adding
		modifying.release();
	}
	
	public Task take() throws Throwable {
		//ensure that we are not currently modifying the queue
		modifying.acquire();  

		//check if at least one permit has been acquired, else release modifying lock
		if (queueLocks.availablePermits() < queue.length) {
			//release a lock from the queueLocks and remove the last index
			queueLocks.release();
			Task lastTask = queue[out];
			out = (out + 1) % queue.length;
			
			//notify that we are done taking
			modifying.release();
			return (lastTask);
		}
		else {
			//notify that we are done taking
			modifying.release();
			throw new Exception("Queue currently empty");
		}
	}
}
