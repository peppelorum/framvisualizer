package junitTest;

import java.util.ArrayList;

import data.ConnectionInfo;
import data.FramNode;
import data.RelationInfo;
import data.FramNode.NodePort;


import junit.framework.TestCase;

public class TestFindRelations extends TestCase {
	public TestFindRelations(
			String name) {
		super(name);
	}
	
	public void testsearchConnectionsNotreallyaTest(){
		ArrayList<ConnectionInfo> list = TestFramNodeList.createFramList().getConnections();
		
		for(ConnectionInfo cInfo : list){
			System.out.println(cInfo.getFrom().getFunctionName() + ":" + cInfo.getFrom().getConnectionPort() +" - " + cInfo.getAspect() + " - " + cInfo.getTo().getFunctionName() +":"+ cInfo.getTo().getConnectionPort());
		}
		
	}
	
	public void testRelationCompare() {
		FramNode node = new FramNode("test1");
		RelationInfo relInfo = new RelationInfo(node, "Input");
		RelationInfo relInfo2 = new RelationInfo(node, "Input");
		
		assertTrue(relInfo.compareTo(relInfo2));
	}
	

	
	
	public void testStringToConnectionType() {
		
		assertEquals(
				NodePort.Input, 
				NodePort.valueOf("Input"));
		assertEquals(
				NodePort.Output, 
				NodePort.valueOf("Output"));
		assertEquals(
				NodePort.Resources, 
				NodePort.valueOf("Resources"));
		assertEquals(
				NodePort.Control, 
				NodePort.valueOf("Control"));
		assertEquals(
				NodePort.Time, 
				NodePort.valueOf("Time"));
		assertEquals(
				NodePort.Preconditions, 
				NodePort.valueOf("Preconditions"));
		
	}
	
	
}
