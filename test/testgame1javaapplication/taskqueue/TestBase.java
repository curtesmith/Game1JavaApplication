package testgame1javaapplication.taskqueue;

import game1javaapplication.TaskQueue;
import org.junit.Before;


public class TestBase {

    protected TaskQueue sut;
    protected Exception ex;

    public TestBase() {
    }

    @Before
    public void setUp() 
    {
        sut = new TaskQueue();
    }
}