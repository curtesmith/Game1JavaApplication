package testgame1javaapplication.threadpool;


import game1javaapplication.*;
import org.junit.Before;


public class TestBase
{
    protected ThreadPool sut;
    protected Exception ex;


    public TestBase()
    {
    }


    @Before
    public void setUp() 
    {
        sut = new ThreadPool(createTaskQueue(), getNumThreads());
    }


    protected TaskQueue createTaskQueue()
    {
        return new TaskQueue();
    }


    protected int getNumThreads()
    {
        return 1;
    }
}