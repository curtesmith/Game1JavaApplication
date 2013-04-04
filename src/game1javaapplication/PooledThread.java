package game1javaapplication;

public class PooledThread extends Thread
{    
    ThreadPool threadPool;


    public PooledThread(ThreadPool threadPool, int threadID)
    {
        super(threadPool, "PooledThread-" + threadID);
        this.threadPool = threadPool;
    }


    @Override
    public void run()
    {
        while(!isInterrupted())
        {
            Runnable task = null;

            try
            {
                task = threadPool.getTask();
            }
            catch(InterruptedException ex){}

            if(task == null)
                return;

            try
            {
                task.run();
            }
            catch(Throwable t)
            {
                threadPool.uncaughtException(this, t);
            }
        }
    }
}