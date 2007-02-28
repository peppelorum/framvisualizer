package junitTest;

import table.FramAspectTableModel;
import junit.framework.TestCase;

public class TestFramTableModel extends TestCase {

	FramAspectTableModel model;
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestFramTableModel.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		model = new FramAspectTableModel(TestFramNodeList.createFramList().get(0));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCreateNodeCells() {
		Object[] target = new Object[] {
				new Object[] { "Name", "Nyhetsbyrå" },
				new Object[] { "Input", "Nyheter" },
				new Object[] { "Input", "Nyheter2" },
				new Object[] { "Output", "Tidningar" },
				new Object[] { "Output", "Tidningar2" },
				new Object[] { "Preconditions", "" },
				new Object[] { "Time", "Varje dag" },
				new Object[] { "Time", "Varje dag2" },
				new Object[] { "Resources", "Papper" },
				new Object[] { "Resources", "Papper2" },
				new Object[] { "Control", "TT" },
				new Object[] { "Control", "TT2" },
				new Object[] { "", "" },
		};
				
		Object[] result = model.createNodeCells(model.getList().get(0));
		
		for(int i = 0; i < result.length; i++) {
			Object[] targetRow = (Object[])target[i];
			Object[] resultRow = (Object[])result[i];
			for(int j = 0; j < targetRow.length; j++) {
				assertEquals((String)targetRow[j], (String)resultRow[j]);
			}
			
		}
		
	}
	
	public void testConstructor() {
		
		Object[] target = new Object[] {
				new Object[] { "Name", "Nyhetsbyrå" },
				new Object[] { "Input", "Nyheter" },
				new Object[] { "Input", "Nyheter2" },
				new Object[] { "Output", "Tidningar" },
				new Object[] { "Output", "Tidningar2" },
				new Object[] { "Preconditions", "" },
				new Object[] { "Time", "Varje dag" },
				new Object[] { "Time", "Varje dag2" },
				new Object[] { "Resources", "Papper" },
				new Object[] { "Resources", "Papper2" },
				new Object[] { "Control", "TT" },
				new Object[] { "Control", "TT2" },
				new Object[] { "", "" },
		};
		
		for(int i = 0; i < target.length; i++) {
			Object[] targetRow = (Object[])target[i];
			for(int j = 0; j < targetRow.length; j++) {
				assertEquals((String)targetRow[j], model.getValueAt(i, j));
			}
			
		}
		
	}
	
	public void testModelChangedListener() {
		model.setValueAt("NyhetsbyråÄNDRAD", 0, 1);
		assertEquals(model.getList().get(0).getName(), "NyhetsbyråÄNDRAD");	
		
		assertEquals(model.getList().get(0).getOutput().get(0).getValue(), "Tidningar");
		
		model.setValueAt("Dåliga nyheter", 3, 1);
		assertEquals(model.getList().get(0).getOutput().get(0).getValue(), "Dåliga nyheter");
	}
	
	public void testListChangedListener() {
		model.getList().get(0).setName("namnÄndratFrånListan");		
		assertEquals("namnÄndratFrånListan", model.getValueAt(0, 1));
		
	}

}
