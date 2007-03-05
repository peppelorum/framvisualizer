package junitTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import data.FramNode;
import data.FramNodeList;

import junit.framework.TestCase;

public class TestFramNodeList extends TestCase {
	public TestFramNodeList(
			String name) {
		super(name);
	}
	
	
	public void testSparaEnNod() {
		FramNodeList testList = new FramNodeList("TestList");
		FramNode testNode = new FramNode("testar");
		
		testNode.setName("Namn"); 
		testList.add(testNode);
		
		assertEquals(testList.get(0),testNode); 
	}
	
	public void testGetAllaNamn(){
		FramNodeList testList = new FramNodeList("TestList");
		FramNode testNode = new FramNode("NamnNod1");
		FramNode testNode2 = new FramNode("NamnNod2");
		
		testList.add(testNode);
		testList.add(testNode2);
		
		String[] allaNamn = new String[2];
		allaNamn[0] = "NamnNod1";
		allaNamn[1] = "NamnNod2";
		
		assertEquals(testList.getAllNames().get(0),allaNamn[0]);
		assertEquals(testList.getAllNames().get(1),allaNamn[1]);
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(
				TestFramNode.class);
	}
	
	public static FramNodeList createFramList(){
		FramNodeList lista = new FramNodeList("TestList");
		
		FramNode nod1 = new FramNode("Nyhetsbyrå");
		FramNode nod2 = new FramNode("Skogen");
		FramNode nod3 = new FramNode("Världen");
		
		lista.add(nod1);
		lista.add(nod2);
		lista.add(nod3);
		
		nod1.addOutput("Tidningar");
		nod1.addOutput("Tidningar2");
		nod1.addInput("Nyheter");
		nod1.addInput("Nyheter2");
		nod1.addControl("TT");
		nod1.addControl("TT2");
		nod1.addTime("Varje dag");
		nod1.addTime("Varje dag2");
		nod1.addResources("Papper");
		nod1.addResources("Papper2");
		
		nod2.addOutput("Papper");
		nod2.addInput("Träd");
		nod2.addPrecondition("Skogen växer");
		nod2.addControl("Växthuseffekten");
		nod2.addTime("Varje dag");
		nod2.addResources("Väder");
		
		nod3.addOutput("Nyheter");
		nod3.addInput("Växthuseffekten");
		nod3.addPrecondition("Nåt har hänt");
		nod3.addControl("TT");
		nod3.addTime("Varje dag");
		nod3.addResources("Tidningar");
		
		lista.addListChangedListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("something happened");
				
			}
			
			
		});
		
		return lista;
	}
	
	
	
	
	public void testActionListener() {
		FramNodeList lista = createFramList();
		lista.addListChangedListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				FramNodeList source = (FramNodeList)e.getSource();
				if(e.getActionCommand() != "NameChanged") {
					source.setName(e.getActionCommand());
				}
			}
		});
		
		FramNode nod = new FramNode();
		lista.setName("gammaltNamn");
		assertEquals(lista.getName(), "gammaltNamn");
		lista.add(nod);
		assertEquals(lista.getName(), "NodeAdded");
		
		nod.setName("test");
		assertEquals(lista.getName(), "NodeChanged");
		
		lista.remove(nod);
		assertEquals(lista.getName(), "NodeRemoved");
		
		nod.setName("test");
		
		assertEquals(lista.getName(), "NodeRemoved");
	}
	
	
	
	public void testConnections() {
		FramNodeList list = new FramNodeList("");
		
		FramNode node1 = new FramNode("test1");
		node1.addOutput("a1");
		list.add(node1);
		
		FramNode node2 = new FramNode("test2");
		node2.addInput("a1");
		list.add(node2);
		
		assertEquals(1, node1.getConnectedNodes().size());
		assertEquals(node2, node1.getConnectedNodes().get(0));
		
		FramNode node3 = new FramNode("test3");
		node3.addInput("a1");
		list.add(node3);
		
		assertEquals(2, node1.getConnectedNodes().size());
		assertEquals(node2, node1.getConnectedNodes().get(0));
		assertEquals(node3, node1.getConnectedNodes().get(1));
		
		FramNode node4 = new FramNode("test4");
		node4.addPrecondition("a1");
		list.add(node4);
			
		assertEquals(3, node1.getConnectedNodes().size());
		assertEquals(node2, node1.getConnectedNodes().get(0));
		assertEquals(node3, node1.getConnectedNodes().get(1));
		assertEquals(node4, node1.getConnectedNodes().get(2));
	}
}
