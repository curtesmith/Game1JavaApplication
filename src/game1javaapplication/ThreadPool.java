package game1javaapplication;


public class ThreadPool extends ThreadGroup
{
    private boolean isAlive;
    private int threadID;
    private static int threadPoolID;
    private TaskQueue taskQueue;
    
    public ThreadPool(TaskQueue taskQueue, int numThreads)
    {
        super("ThreadPool-" + (threadPoolID++));
        setDaemon(true);
        isAlive = true;
        this.taskQueue = taskQueue;
        this.taskQueue.setThreadPool(this);
        createThreads(numThreads);
    }
    
    
    private void createThreads(int numThreads)
    {
        for(int i=0;i<numThreads;i++)
            new PooledThread(this, threadID++).start();        
    }

    
    public void notifyThreads()
    {
        synchronized(this)
        {
            if(!isAlive)
                throw new IllegalStateException();

            notify();
        }
    }
    
    
    public Runnable getTask()
            throws InterruptedException
    {
        synchronized(this)
        {
            while(taskQueue.size() == 0)
            {
                if(!isAlive)
                    return null;
                
                wait();
            }

            return taskQueue.getTask();
        }
    }
    
    
    protected void close()
    {
        synchronized(this)
        {
            if(isAlive)
            {
                isAlive = false;
                taskQueue.clear();
                interrupt();
            }
        }
    }
    
    
    public void join()
    {        
        synchronized(this)
        {
            isAlive = false;
            notifyAll();
        }
        
        Thread[] threads = new Thread[activeCount()];
        int count = enumerate(threads);

        for(int i=0;i<count; i++)
        {
            try
            {
                threads[i].join();
            }
            catch(InterruptedException ex) { }
        }
    }
}
