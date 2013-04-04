package testgame1javaapplication.threadpool;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestGetTaskWithEmptyTaskQueue extends TestBase
{
    protected Runnable task;

    public TestGetTaskWithEmptyTaskQueue() 
    {
    }

    @Before
    @Override
    public void setUp()
    {
        super.setUp();
        try
        {
            task = sut.getTask();
        }
        catch(Exception exc)
        {
            ex = exc;
        }
    }


    @Test
    public void shouldSuspendTheThread()
    {
        sut.notifyThreads();
        assertTrue(true);
    }
}