package junitTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import data.Aspect;
import data.FramFunction;
import junit.framework.TestCase;

public class TestFramNode extends TestCase {

	public TestFramNode(
			String name) {
		super(name);
	}
	
	/**
	 * Testar att l�gga till ett v�rde i varje kopplinsgpunkt
	 *
	 */
    public void testInput1values() {
        FramFunction test = new FramFunction("Testnamn");
        assertEquals("Testnamn", test.getName());
        
        test.addInput("testar");
        test.addOutput("testar1");
        test.addResources("testar2");
        test.addControl("testar3");
        test.addPrecondition("testar4");
        test.addTime("testar5");
        test.setName("testar6");
        
        assertEquals("testar", test.getInput().get(0).getValue());       
        assertEquals("testar1", test.getOutput().get(0).getValue());
        assertEquals("testar2", test.getResources().get(0).getValue());
        assertEquals("testar3", test.getControl().get(0).getValue());
        assertEquals("testar4", test.getPrecondition().get(0).getValue());
        assertEquals("testar5", test.getTime().get(0).getValue());
        
        assertEquals("testar6", test.getName());

    }
    
    /**
     * Testar att lägga till två värden i varje kopplingspunkt
     *
     */
    public void testInput2values() {
        FramFunction test = new FramFunction("Testnamn");
        assertEquals("Testnamn", test.getName());
        
        test.addInput("testar");
        test.addOutput("testar");
        test.addResources("testar");
        test.addControl("testar");
        test.addPrecondition("testar");
        test.addTime("testar");
        test.setName("testar");
        
        assertEquals("testar", test.getInput().get(0).getValue());       
        assertEquals("testar", test.getOutput().get(0).getValue());
        assertEquals("testar", test.getResources().get(0).getValue());
        assertEquals("testar", test.getControl().get(0).getValue());
        assertEquals("testar", test.getPrecondition().get(0).getValue());
        assertEquals("testar", test.getTime().get(0).getValue());
        
        test.addInput("testar2");
        test.addOutput("testar2");
        test.addResources("testar2");
        test.addControl("testar2");
        test.addPrecondition("testar2");
        test.addTime("testar2");
        test.setName("testar2");
        
        assertEquals("testar2", test.getInput().get(1).getValue());       
        assertEquals("testar2", test.getOutput().get(1).getValue());
        assertEquals("testar2", test.getResources().get(1).getValue());
        assertEquals("testar2", test.getControl().get(1).getValue());
        assertEquals("testar2", test.getPrecondition().get(1).getValue());
        assertEquals("testar2", test.getTime().get(1).getValue());
        
        assertEquals("testar2", test.getName());

    }
    
    public void testGetAllAttributes(){
    	 FramFunction test = new FramFunction("Testnamn");
         
         test.addInput("testarInput");
         test.addOutput("testarOutput");
         test.addResources("testarResources");
         test.addControl("testarControl");
         test.addPrecondition("testarPrecondition");
         test.addTime("testarTime");
         test.setName("testarName");
         
         ArrayList<String[]> list = test.getAllAspects();
         
         assertEquals(list.get(0)[0], "Input");
         assertEquals(list.get(0)[1], "testarInput");

         assertEquals(list.get(1)[0], "Output");
         assertEquals(list.get(1)[1], "testarOutput");
   
         assertEquals(list.get(2)[0], "Resources");
         assertEquals(list.get(2)[1], "testarResources");
         assertEquals(list.get(3)[0], "Control");
         assertEquals(list.get(3)[1], "testarControl");
         assertEquals(list.get(4)[0], "Time");
         assertEquals(list.get(4)[1], "testarTime");
         assertEquals(list.get(5)[0], "Preconditions");
         assertEquals(list.get(5)[1], "testarPrecondition");

         
    }
    
    public static void main(String[] args) {
        junit.textui.TestRunner.run(
        		TestFramNode.class);
    }

    public void testActionListener() {
    	FramFunction node = new FramFunction("gammaltNamn");
    	
    	node.addNodeChangedListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				FramFunction source = (FramFunction)e.getSource();
				if(e.getActionCommand() != "NameChanged") {
					source.setName(e.getActionCommand());
				}
			}
    	});
    	
    	node.setName("gammaltNamn");
    	assertEquals(node.getName(), "gammaltNamn");
    	
    	node.addControl("test");
    	assertEquals(node.getName(), "ControlAdded");
    	
    	node.addInput("test");
    	assertEquals(node.getName(), "InputAdded");
    	
    	node.addResources("test");
    	assertEquals(node.getName(), "ResourcesAdded");
    	
    	node.addTime("test");
    	assertEquals(node.getName(), "TimeAdded");
    	
    	node.addOutput("test");
    	assertEquals(node.getName(), "OutputAdded");
    	
    	node.addPrecondition("test");
    	assertEquals(node.getName(), "PreconditionsAdded");
    	
    	for(FramFunction.NodePort point : FramFunction.NodePort.values()) {
    		ArrayList<Aspect> testList = new ArrayList<Aspect>();
    		testList.add(new Aspect("test2"));
    		
    		node.setAttributes(point, testList);
    		assertEquals(node.getName(), point.toString() + "Changed");
    	}
    }
}
