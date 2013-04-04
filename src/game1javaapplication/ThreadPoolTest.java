package game1javaapplication;

public class ThreadPoolTest
{
    public static void main(String[] args)
    {
       if(args.length != 2)
       {
            System.out.println("Tests the ThreadPool task.");
            System.out.println("Usage: java ThreadPoolTest numtasks numthreads");
            System.out.println(" numtasks - integer: number of tasks to run");
            System.out.println(" numthreads - integer: number of threads in the thread pool");
            return;
       }

       int numTasks = Integer.parseInt(args[0]);
       int numThreads = Integer.parseInt(args[1]);

       
       TaskQueue taskQueue = new TaskQueue();
       ThreadPool threadPool = new ThreadPool(taskQueue, numThreads);
       
       for(int i=0; i<numTasks; i++)
       {
           Logger.write("threadPool runtask #" + i);
           taskQueue.add(createTask(i));
       }

       threadPool.join();
    }


    private static Runnable createTask(final int taskID)
    {
        return new Runnable() {
            public void run() {
                Logger.write("Task " + taskID + ": start");

                try
                {
                    Thread.sleep(500);
                }
                catch(InterruptedException ex) { }

                Logger.write("Task " + taskID + ": end");
            }
        };
    }
}
