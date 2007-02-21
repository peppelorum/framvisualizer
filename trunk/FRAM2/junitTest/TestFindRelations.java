package junitTest;

import java.util.ArrayList;

import data.ConnectionInfo;
import data.FramNode;
import data.RelationInfo;
import data.FramNode.connectionPoints;


import junit.framework.TestCase;

public class TestFindRelations extends TestCase {
	public TestFindRelations(
			String name) {
		super(name);
	}
	
	public void testsearchConnectionsNotreallyaTest(){
		ArrayList<ConnectionInfo> list = TestFramNodeList.createFramList().searchConnections();
		
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
				connectionPoints.Input, 
				connectionPoints.valueOf("Input"));
		assertEquals(
				connectionPoints.Output, 
				connectionPoints.valueOf("Output"));
		assertEquals(
				connectionPoints.Resources, 
				connectionPoints.valueOf("Resources"));
		assertEquals(
				connectionPoints.Control, 
				connectionPoints.valueOf("Control"));
		assertEquals(
				connectionPoints.Time, 
				connectionPoints.valueOf("Time"));
		assertEquals(
				connectionPoints.Preconditions, 
				connectionPoints.valueOf("Preconditions"));
		
	}
	
	
}
