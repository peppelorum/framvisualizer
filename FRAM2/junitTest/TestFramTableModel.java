package junitTest;

import table.TableModel;
import junit.framework.TestCase;

public class TestFramTableModel extends TestCase {

	TableModel model;
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestFramTableModel.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		model = new TableModel(TestFramNodeList.createFramList().get(0));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCreateNodeCells() {
		Object[] target = new Object[] {
				new Object[] { "Name", "Nyhetsbyr�" },
				new Object[] { "Input", "Nyheter, Nyheter2" },
				new Object[] { "Output", "Tidningar, Tidningar2" },
				new Object[] { "Resources", "Papper, Papper2" },
				new Object[] { "Control", "TT, TT2" },
				new Object[] { "Time", "Varje dag, Varje dag2" },
				new Object[] { "Preconditions", "" },
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
				new Object[] { "Name", "Nyhetsbyr�" },
				new Object[] { "Input", "Nyheter, Nyheter2" },
				new Object[] { "Output", "Tidningar, Tidningar2" },
				new Object[] { "Resources", "Papper, Papper2" },
				new Object[] { "Control", "TT, TT2" },
				new Object[] { "Time", "Varje dag, Varje dag2" },
				new Object[] { "Preconditions", "" },
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
		model.setValueAt("Nyhetsbyr��NDRAD", 0, 1);
		assertEquals(model.getList().get(0).getName(), "Nyhetsbyr��NDRAD");
		
		model.setValueAt("Nyhetsbyr��NDRAD2", 8, 1);
		assertEquals(model.getList().get(1).getName(), "Nyhetsbyr��NDRAD2");
		
		
		assertEquals(model.getList().get(2).getOutput().get(0), "Nyheter");
		
		model.setValueAt("D�liga nyheter", 18, 1);
		assertEquals(model.getList().get(2).getOutput().get(0), "D�liga nyheter");
	}
	
	public void testListChangedListener() {
		model.getList().get(0).setName("namn�ndratFr�nListan");		
		assertEquals("namn�ndratFr�nListan", model.getValueAt(0, 1));
		
		model.getList().get(1).setName("namn2�ndratFr�nListan");
		assertEquals("namn2�ndratFr�nListan", model.getValueAt(8, 1));
		
	}

}
