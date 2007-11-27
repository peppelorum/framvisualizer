package junitTest;

import data.Aspect;
import data.FramFunction;
import junit.framework.TestCase;

public class TestAspect extends TestCase {

	Aspect aspect1, aspect2, aspect3;
	
	protected void setUp() throws Exception {
		aspect1 = new Aspect("testValue1", "testComment1");
		aspect2 = new Aspect("testValue2");
		aspect3 = new Aspect(aspect1.getValue(), aspect1.getComment());
		
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testContent() {
		
		assertEquals(aspect1.getValue(), "testValue1");
		assertEquals(aspect1.getComment(), "testComment1");
	
		assertEquals(aspect2.getValue(), "testValue2");
		assertEquals(aspect2.getComment(), "");
		
		aspect2.setValue("testValue3");
		aspect2.setComment("testComment3");
		assertEquals(aspect2.getValue(), "testValue3");
		assertEquals(aspect2.getComment(), "testComment3");
		
	}
	
	public void testEquals() {
		assertTrue(aspect1.equals(aspect1));
		assertFalse(aspect1.equals(aspect2));
		assertFalse(aspect1.equals(null));		
		assertTrue(aspect1.equals(aspect3));
		
	}
	
	public void testParent() {
		FramFunction node = new FramFunction("");
		aspect1.setParent(node);
		assertEquals(node, aspect1.getParent());
	}
}
