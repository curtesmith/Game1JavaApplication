package testgame1javaapplication.taskqueue;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestGetTaskWithEmptyTaskList extends TestBase{

    private Runnable task;

    public TestGetTaskWithEmptyTaskList() {
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
    public void shouldReturnNull()
    {
        assertTrue(task == null);
    }

}