package testgame1javaapplication.taskqueue;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestAddWithRunnableTask extends TestBase {

    public TestAddWithRunnableTask() {
    }


    @Before
    @Override
    public void setUp() 
    {
        super.setUp();
        try
        {
            sut.add(new Runnable(){ public void run(){}});
        }
        catch(Exception exc)
        {
            ex = exc;
        }
    }


    @Test
    public void shouldBeOne()
    {
        assertTrue(sut.size() == 1);
    }

}