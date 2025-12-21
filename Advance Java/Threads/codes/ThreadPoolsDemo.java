import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// We can use Custom Thread Factory to create threads with custom settings like name, priority, daemon etc
class CustomThreadFactory implements ThreadFactory{   
    private String name;
    CustomThreadFactory(String name){
        this.name = name;
    }

    @Override
    // Creates a new Thread
    public Thread newThread(Runnable r){
        Thread thread = new Thread(r);
        thread.setName(name + "-" + thread.threadId());
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.setDaemon(false);
        return thread;
    }
}

// We can use Custom Rejection Handler to handle rejected tasks in a custom way
class CustomRejectHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Task rejected from " + executor.toString());
    }
}

public class ThreadPoolsDemo {
    public static void main(String[] args) throws Exception  {
        
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            2, // corePoolSize
            4, // maximumPoolSize
            10, // keepAliveTime
            TimeUnit.SECONDS, // time unit for keepAliveTime
            new ArrayBlockingQueue<>(2), // workQueue
            new CustomThreadFactory("MyPoolThread"), // custom thread factory
            new CustomRejectHandler() // custom rejection handler
        );

        for(int i = 1; i <= 10; i++){
            final int taskId = i; // lambda requires variables to be final or effectively final
            poolExecutor.submit(() -> {
                try{
                    Thread.sleep(4000); // Simulate work
                } 
                catch(Exception e){
                    System.out.println(e);
                }
                System.out.println("Task " + taskId + " is being executed by " + Thread.currentThread().getName());
            });

        }
        poolExecutor.shutdown(); // Initiates an orderly shutdown
        
    }            
}

// What will happen when we run this code?

// 1. The first 2 tasks will start executing immediately using the core threads.
// 2. The next 2 tasks will be queued in the ArrayBlockingQueue.
// 3. The 5th task will cause the pool to create a new thread (up to maximumPoolSize) to handle the task.
// 4. If more tasks are submitted beyond the maximumPoolSize and the queue is full, the custom rejection handler will be invoked.   
// 5. Each task simulates work by sleeping for 4 seconds before printing its execution message.
// 6. Finally, the pool is shut down gracefully after all tasks are submitted.