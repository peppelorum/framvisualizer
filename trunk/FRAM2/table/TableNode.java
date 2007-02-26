package table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import data.FramNode;

public class TableNode extends JTableX {

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
		setRowEditorModel(new RowEditorModel());
		setCellEditorModel(new CellEditorModel());
		
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
        addAutoComplete(); 
        
        for(int i = 0;i<this.getColumnCount();i++){
        	//Sets the cell renderers
        	this.getColumnModel().getColumn( i ).setCellRenderer(new CustomCellRenderer ()); 
        }
	}
	
	private void addAutoComplete() {        
		ComboBoxAutoComplete auto = new ComboBoxAutoComplete(addStartingSpaceToArray(node.getList().getAllAspects()));
		auto.setEditable(true);
		ComboBoxCellEditor editor = new ComboBoxCellEditor(auto);
		
		
		for(int i = 0; i < this.getRowCount(); i++){
			if(getModel().getValueAt(i,0) != "Name"){
				getRowEditorModel().addEditorForRow(i, editor);
			}
		}
//		for(int i = 0; i < this.getRowCount(); i++){
//			if(getModel().getValueAt(i,0) != "Name"){
//				getCellEditorModel().addEditor(i,1, editor);
//			}
//		}
		
	
		
	}
	
	public void cleanUp() {
		if(node != null) {
			node.removeNodeChangedListener(nodeChangedListener);
			TableNodeModel model = (TableNodeModel)getModel();
			model.cleanUp();
		}
	}
	
	/**
	 * Takes a String[] and adds an empty slot in the very beginning. 
	 * This is a temporary(?)fix for the combobox, to make it possible to enter nothing in the combobox
	 * 
	 * @param listAllEntities A list over all used attributes
	 * @return String[]
	 */
	private String[] addStartingSpaceToArray(String[] listAllEntities){
        
        int listAllEntitiesLength = listAllEntities.length;
        String[] listAllEntitiesWithStartingSpace = new String[listAllEntitiesLength+1];
        listAllEntitiesWithStartingSpace[0] = "";
        System.arraycopy(listAllEntities, 0, listAllEntitiesWithStartingSpace,1,listAllEntitiesLength);
        
        return listAllEntitiesWithStartingSpace;
	}
	
	public TableCellEditor getCellEditor(int row, int col)
	{
		if(col == 0) {
			TableCellEditor editor = super.getCellEditor(row,col);
			return editor;
			
		}
		if(this.getValueAt(row, 0).toString() == "Name" || col != 1) {
			return new AddRowCellEditor(super.getCellEditor(row,col));			
		}
		else {
			ComboBoxAutoComplete combo = new ComboBoxAutoComplete(node.getList().getAllAspects(true));
			combo.setEditable(true);
			return new AddRowCellEditor(new ComboBoxCellEditor(combo));
		}

	}
	
}
