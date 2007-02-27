package table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import data.FramNode;

public class TableNode extends JTable {

	private FramNode node;
	private ActionListener nodeChangedListener;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3518691723356609315L;

	public TableNode() {
		
		this(new FramNode(""));
	}
	
	public TableNode(FramNode node) {
		
		nodeChangedListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		};
		
		setNode(node);
	}
	
	public FramNode getNode() {
		return node;
	}
	
	public void setNode(FramNode newNode) {
		cleanUp();
		
		node = newNode;

		setModel(new TableNodeModel(node));
		node.addNodeChangedListener(nodeChangedListener);
		
		updateTable();
	}
	
	private void updateTable() {     
        
        for(int i = 0;i<this.getColumnCount();i++){
        	//Sets the cell renderers
        	this.getColumnModel().getColumn( i ).setCellRenderer(new CustomCellRenderer ()); 
        }
	}
	
	public void cleanUp() {
		if(node != null) {
			node.removeNodeChangedListener(nodeChangedListener);
			TableNodeModel model = (TableNodeModel)getModel();
			model.cleanUp();
		}
	}
	
	
	public TableCellEditor getCellEditor(int row, int col)
	{
		if(col == 0) {
			TableCellEditor editor = super.getCellEditor(row,col);
			return editor;
			
		}
		if(this.getValueAt(row, 0).toString() == "Name" || col != 1) {
			return new ButtonInTableCell(super.getCellEditor(row,col));			
		}
		else {
			ComboBoxAutoComplete combo = new ComboBoxAutoComplete(node.getList().getAllAspects(true));
			combo.setEditable(true);
			return new ButtonInTableCell(new ComboBoxCellEditor(combo));
		}

	}
	
}
