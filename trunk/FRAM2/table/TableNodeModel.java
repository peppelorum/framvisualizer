package table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import data.Aspect;
import data.FramNode;
import data.FramNodeList;
import data.FramNode.connectionPoints;


public class TableNodeModel extends DefaultTableModel {
	
	private ActionListener nodeChangedListener;
	
	public boolean isCellEditable(int row, int col)
	{
		if (col==0 || this.getValueAt(row,0)=="")
			return false;
		return true;
	}
	
	/**
	 * Listener for changes in a node table, this listener propagates changes from the jtable back to the framnode and framnodelist
	 */
	private TableModelListener currentTableModelListener = new TableModelListener() {

		public void tableChanged(TableModelEvent e) {
			if(node != null) {
			
				int changedRow = e.getFirstRow();
				String newValue = getValueAt(changedRow, 1).toString();
				
				String newComment = getValueAt(changedRow, 2).toString();
				
				String changedLabel = getValueAt(changedRow, 0).toString();
				if(changedLabel == "Name") {
					node.setName(newValue);
					node.setComment(newComment);
				}
				else {
					FramNode.connectionPoints conn = connectionPoints.valueOf(changedLabel);
					ArrayList<Aspect> newValList = new ArrayList<Aspect>();
					
					for(int i = 0; i < getRowCount(); i++) {
						if(getValueAt(i, 0).toString().equals(conn.toString())) {
							newValList.add(new Aspect(
									getValueAt(i, 1).toString(), 
									getValueAt(i,2).toString()));
						}
					}					
					node.setAttribute(conn, newValList);
				}
			}
		}
		
	};
	private static final long serialVersionUID = 9008215613909069521L;
	FramNode node;
	
	public TableNodeModel() {
		this(new FramNode(""));
	}
	
	public TableNodeModel(FramNode node) {
		nodeChangedListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == getNode()) {
				
					generateCells();
				}
			}
			
		};
		
		String[] colNames = {"Port", "Aspect", "Comment"};
		 this.setColumnIdentifiers(colNames);
		
		setNode(node);
		
		generateCells();
	}
	
		
	public void setNode(FramNode newNode) {
		// TODO: remove list changed listener
		
		cleanUp();
		
		node = newNode;
		
		node.addNodeChangedListener(nodeChangedListener);
	}
		
	private void generateCells() {
		// ta bort lyssnaren s� att inte b�da lyssnarna �r ig�ng samtidigt
		this.removeTableModelListener(currentTableModelListener);
		
		this.setColumnCount(3);
		
		for(int i = 0; i < this.getRowCount(); i++) {
			this.dataVector.clear();
		}
		
		Object[] rows = createNodeCells(node);
		for(Object row : rows) {
			this.addRow((Object[])row);
		}
		
		this.addTableModelListener(currentTableModelListener);
	}

	
	public Object[] createNodeCells(FramNode node) {
	
		ArrayList<Object> rows = new ArrayList<Object>();
		rows.add(new Object[] { "Name", node.getName(), node.getComment()});
		
		connectionPoints[] cPoints = { 
				connectionPoints.Input, 
				connectionPoints.Output,
				connectionPoints.Preconditions,
				connectionPoints.Time,
				connectionPoints.Resources,
				connectionPoints.Control };
		
		for(connectionPoints cPoint : cPoints) {
			ArrayList<Aspect> aspects = node.getAttribute(cPoint);
			for(Aspect aspect : aspects) {
				String attr = aspect.getValue();
				String comment = aspect.getComment();
				rows.add(new Object[] { cPoint.toString(), attr, comment});
			}
			if(aspects.size() == 0) {
				rows.add(new Object[] { cPoint.toString(), "", ""});
			}
		}
		
		//for(FramNode.connectionPoints point : FramNode.connectionPoints.values()) {
//			rows.add(new Object[] { point.toString(), node.getAttribute(point)});
		//}
		
		rows.add(new Object[] { "", ""});
		
		return rows.toArray();
		
	}
	
	
	public FramNode getNode() {
		return node;
	}
	
	public FramNodeList getList() {
		if(node==null) {
			return null;
		}
		else {
			return node.getList();
		}
	}
	
	public void cleanUp() {
		if(node != null) {
			node.removeNodeChangedListener(nodeChangedListener);
		}
	}
	
}