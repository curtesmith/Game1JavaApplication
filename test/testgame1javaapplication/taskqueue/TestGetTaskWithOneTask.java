package testgame1javaapplication.taskqueue;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestGetTaskWithOneTask extends TestBase {

    private Runnable task;


    public TestGetTaskWithOneTask() {
    }

    @Before
    @Override
    public void setUp() 
    {
        super.setUp();
        sut.add(new Runnable(){ public void run(){}});
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
    public void shouldNotBeNull()
    {
        assertTrue(task != null);
    }

}