package hello_world;
import static org.junit.Assert.*;
import org.junit.Test;


public class helloTest {

    @Test
    public void testMethodThing() {
        assertEquals(0,0);
    }

    @Test
    public void testMethodThing2() {
	hello thing = new hello();
	assertEquals(thing.method(0), 0);
	assertEquals(thing.method(2), 4);
        assertEquals(thing.notAnExcuseForUnitTests("not"), "not stuff");
    }



}
