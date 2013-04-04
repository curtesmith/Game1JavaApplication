package testgame1javaapplication.taskqueue;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestAddWithNull extends TestBase {

    public TestAddWithNull() {
    }

    @Before
    @Override
    public void setUp() 
    {
        super.setUp();
        try
        {
            sut.add(null);
        }
        catch(Exception exc)
        {
            ex = exc;
        }
    }
   

    @Test
    public void shouldBeZero()
    {
        assertTrue(sut.size() == 0);
    }

}