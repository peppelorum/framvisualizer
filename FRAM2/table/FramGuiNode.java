package table;

import java.awt.Container;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import data.FramNode;

public class FramGuiNode extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 197489600332691308L;

	private FramNode node;
	private TableNode tableNode;
	
	public FramGuiNode(FramNode node) {
		
		this.setVisible(true);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.node = node;
		
		tableNode = new TableNode(node);
		
		this.add(tableNode);
		
		JTable table = new JTable();
		TableColumn column = new TableColumn();
		column.setHeaderValue("Attribute");
		table.addColumn(column);
		for(FramNode.connectionPoints conn : FramNode.connectionPoints.values()) {
			column = new TableColumn();
			column.setHeaderValue(conn.toString());
			table.addColumn(column);
		}
		
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		
		model.setColumnCount(table.getColumnCount());
		
		Vector<String> row = new Vector<String>();
		row.add("");
		
		for(FramNode.connectionPoints conn : FramNode.connectionPoints.values()) {
			row.add(conn.toString());
		}
		
		model.addRow(row);
		
		for(FramNode.stegTvaAttribut attrib : FramNode.stegTvaAttribut.values()) {
			
			row = new Vector<String>();
			row.add(attrib.toString());
			
			for(FramNode.connectionPoints conn : FramNode.connectionPoints.values()) {
				row.add("");
			}
			
			model.addRow(row);
		}
		
		this.add(table);
	
	}
	
	public FramNode getNode() {
		return node;
	}

	public void cleanUp() {
		tableNode.cleanUp();
	}
}
