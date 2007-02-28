package table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import data.Aspect;
import data.FramNode;

public class FramAspectTable extends JTable {

	private FramNode node;
	private ActionListener nodeChangedListener;
	private Aspect selectedAspect;
	private ArrayList<ActionListener> selectedChangedRecipients;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3518691723356609315L;

	public FramAspectTable() {
		
		this(new FramNode(""));
	}
	
	public FramAspectTable(FramNode node) {
		selectedChangedRecipients = new ArrayList<ActionListener>(); 
		
		nodeChangedListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		};
		
		setNode(node);
		
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				selectedChanged();
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public FramNode getNode() {
		return node;
	}
	
	public void setNode(FramNode newNode) {
		cleanUp();
		
		node = newNode;

		setModel(new FramAspectTableModel(node));
		node.addNodeChangedListener(nodeChangedListener);
		
		updateTable();
		

	}
	
	private void updateTable() {     
        
        for(int i = 0;i<this.getColumnCount();i++){
        	//Sets the cell renderers
        	this.getColumnModel().getColumn( i ).setCellRenderer(new CustomCellRenderer ()); 
        }
        
        System.out.println(this.getColumnModel().getColumn(0).getHeaderValue().toString());
        
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn col = this.getColumnModel().getColumn(0);
        int width = 90;
        col.setMinWidth(width);
        col.setMaxWidth(width);
        col.setPreferredWidth(width);
        

	}
	
	public void cleanUp() {
		if(node != null) {
			node.removeNodeChangedListener(nodeChangedListener);
			FramAspectTableModel model = (FramAspectTableModel)getModel();
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
	
	public void addSelectedChangedListener(ActionListener listener) {
		this.selectedChangedRecipients.add(listener);
	}
	
	public void removeSelectedChangedListener(ActionListener listener) {
		this.selectedChangedRecipients.remove(listener);
	}
	
	private void selectedChanged() {
		String type = (String)this.getValueAt(this.getSelectedRow(), 0);
		String name = (String)this.getValueAt(this.getSelectedRow(), 1);
		
		Aspect aspect = node.getAspect(type, name);
		setSelectedAspect(aspect);
		
		ActionEvent event = new ActionEvent(this, 0, "");
		
		for(ActionListener listener : this.selectedChangedRecipients) {
			listener.actionPerformed(event);
		}
	}
	
	private void setSelectedAspect(Aspect value) {
		this.selectedAspect = value;
	}
	
	public Aspect getSelectedAspect() {
		return selectedAspect;
	}
	
}
