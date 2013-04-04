package game1javaapplication;


import java.util.LinkedList;


public class TaskQueue
{
    private LinkedList taskList;
    private ThreadPool threadPool;


    public TaskQueue()
    {
        taskList = new LinkedList();
    }


    public void setThreadPool(ThreadPool threadPool)
            throws IllegalArgumentException
    {
        if(threadPool == null)
            throw new IllegalArgumentException("threadPool");

        this.threadPool = threadPool;
    }


    public void add(Runnable task)
    {
        if(task != null)
        {
            try
            {
                taskList.add(task);
                threadPool.notifyThreads();
            }
            catch(Exception ex) {}
        }
    }


    public Runnable getTask()
    {
        if(taskList.size() == 0)
            return null;

        return (Runnable)taskList.removeFirst();
    }


    public void clear()
    {
        taskList.clear();
    }


    public int size()
    {
        return taskList.size();
    }
}
