package table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import data.FramNode;

public class FramGuiNode extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 197489600332691308L;

	private FramNode node;
	private TableNode tableNode;
	private JTable tableStepTwo;
		

	public FramGuiNode(FramNode node) {
		
		this.setVisible(true);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.node = node;
		
		tableNode = new TableNode(node);
		
		this.add(tableNode);
		
		DefaultTableCellRenderer readOnlyRenderer = new DefaultTableCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1982231618663302618L;

			public Component getTableCellRendererComponent
		       (JTable table, Object value, boolean isSelected,
		       boolean hasFocus, int row, int column) 
		    {		        
		        if(row == 0 || column == 0) {
		        	setBackground(Color.getHSBColor(0F, 0F, 0.9F));
		        	setFont(new Font("Dialog",Font.BOLD,12));
		        	this.setForeground(Color.black);
		        }
		      		        
		        return this;
		    }
		};
		
		tableStepTwo = new JTable(){
			public TableCellEditor getCellEditor(int row, int col)
			{
				if(col == 0) {
					TableCellEditor editor = super.getCellEditor(row,col);
					return editor;
					
				}
				else {
					ComboBoxAutoComplete combo = new ComboBoxAutoComplete(FramNode.stepTwoDefaultValues);
					combo.setEditable(true);
					return new ComboBoxCellEditor(combo);
				}

			}
		};
				
		DefaultTableModel model = (DefaultTableModel)tableStepTwo.getModel();
		model.setColumnCount(FramNode.connectionPoints.values().length+2);
				
		for(FramNode.stegTvaAttribut attrib : FramNode.stegTvaAttribut.values()) {

			Vector<String> row = new Vector<String>();
			row = new Vector<String>();
			row.add(attrib.toString());
			
			for(FramNode.connectionPoints conn : FramNode.connectionPoints.values()) {
				row.add("");
			}
			
			model.addRow(row);
		}
		
		TableColumn column;
		
	
        for(int i = 0;i<tableStepTwo.getColumnCount();i++){
        	if(i==0) {
        		tableStepTwo.getColumnModel().getColumn(i).setHeaderValue("");
        		//TableCellRenderer headerRenderer = table.getColumnModel().getColumn(i).getHeaderRenderer();
        		//table.getColumnModel().getColumn(i).setCellRenderer(readOnlyRenderer);
        	}
        	else if(i > FramNode.connectionPoints.values().length) {
        		tableStepTwo.getColumnModel().getColumn(i).setHeaderValue(
            			"Misc");
        	}
        	else {
        		// set header
        		tableStepTwo.getColumnModel().getColumn(i).setHeaderValue(
        			FramNode.connectionPoints.values()[i-1].toString());
        	}
        }
		
        
		//table.getColumnModel().getColumn(0).setHeaderValue("Attribute");
		
//		for(FramNode.connectionPoints conn : FramNode.connectionPoints.values()) {
//			column = new TableColumn();
//			column.setHeaderValue(conn.toString());
//			column.setCellRenderer(customRenderer);
//			table.addColumn(column);
//			table.getColumnModel().getColumn(arg0)
//		}
		
        this.add(tableStepTwo.getTableHeader());
		this.add(tableStepTwo);
	
	}
	
	public FramNode getNode() {
		return node;
	}

	public void cleanUp() {
		tableNode.cleanUp();
	}
	
	public void setStepTwoVisible (boolean value) {		
		tableStepTwo.setVisible(value);
		tableStepTwo.getTableHeader().setVisible(value);
	}
	
	public boolean getStepTwoVisible() {
		return tableStepTwo.isVisible();
	}
}
