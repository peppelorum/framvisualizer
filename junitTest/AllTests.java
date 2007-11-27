package junitTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for junitTest");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestFramTableModel.class);
		suite.addTestSuite(TestSave.class);
		suite.addTestSuite(TestFindRelations.class);
		suite.addTestSuite(TestFramNode.class);
		suite.addTestSuite(TestFramNodeList.class);
		//$JUnit-END$
		return suite;
	}

}
