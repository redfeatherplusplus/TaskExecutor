package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public class BlockingTaskQueue {
	//defined constants
	public static final int DEFAULT_QUEUE_SIZE = 20;
	
	private Task[] queue;                 //FIFO queue of tasks
	private static Object notFull;        //monitors if queue is full
	private static Object notEmpty;       //monitors if queue is empty
	private static int population;        //number of elements in queue
	private static int in;                //index to add at 
	private static int out;               //index to take from
	
	public BlockingTaskQueue(int size) {
		//create a BlockingTaskQueue of default size
		queue = new Task[size];
		notFull = new Object();
		notEmpty = new Object();
		in = 0;
		out = 0;
	}
	
	public BlockingTaskQueue() {
		//create a BlockingTaskQueue of default size
		queue = new Task[DEFAULT_QUEUE_SIZE];
		notFull = new Object();
		notEmpty = new Object();
		in = 0;
		out = 0;
	}
	
	//check if see if throws throwable is correct
	public void put(Task task) throws Throwable {
		while (true) {
			//block until notFull
			if (population == queue.length) { notFull.wait(); }
			
			//in a critical section, put a task into the queue if possible
			synchronized(this) {
				if (population != queue.length) {
					queue[in] = task;
					in = (in + 1) % queue.length;
					
					//notify a thread that the queue is no longer empty
					notEmpty.notify();
				}
			}
		}
	}
	
	public Task take() throws Throwable {
		while (true) {
			//block until notEmpty
			if (population == 0) { notEmpty.wait(); }
			
			//in a critical section, take a task from the queue if possible
			synchronized(this) {
				if (population != 0) {
					Task lastTask = queue[out];
					out = (out + 1) % queue.length;
					
					//notify a thread that the queue is no longer full
					notFull.notify();
					return(lastTask);
				}
			}
		}
	}
	
}
