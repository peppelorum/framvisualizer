package junitTest;

import data.FramFunctionList;
import junit.framework.TestCase;

public class TestSave extends TestCase {
	public TestSave(
			String name) {
		super(name);
	}
	
	 public void testOpenedFileIsEqualToSaved(){
		 FramFunctionList sparad = TestFramNodeList.createFramList();
		 sparad.saveFile("", "Functions.xml");
		 FramFunctionList laddad = FramFunctionList.LoadFile("Functions.xml");
		 
		 assertTrue(sparad.equals(laddad));
		 
	 }
	 
	 public void checkAllNodes(FramFunctionList sparad, FramFunctionList laddad){
		 
		 assertEquals(laddad.size(), sparad.size());
			
			for(int i = 0; i< laddad.size(); i++){
				assertEquals(sparad.get(i).getName(),laddad.get(i).getName());
				assertEquals(sparad.get(i).getInput(),laddad.get(i).getInput());
				assertEquals(sparad.get(i).getOutput(),laddad.get(i).getOutput());
				assertEquals(sparad.get(i).getResources(),laddad.get(i).getResources());
				assertEquals(sparad.get(i).getControl(),laddad.get(i).getControl());
				assertEquals(sparad.get(i).getTime(),laddad.get(i).getTime());
				assertEquals(sparad.get(i).getPrecondition(),laddad.get(i).getPrecondition());
			}
	 }
	 
}
