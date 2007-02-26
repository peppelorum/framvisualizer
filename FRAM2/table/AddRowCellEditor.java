package table;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;

import data.Aspect;
import data.FramNode;

public class AddRowCellEditor extends ActionTableCellEditor{
	public AddRowCellEditor(TableCellEditor editor){ 
        super(editor); 
    } 
 
	
	protected void selectCell(JTable table, int row, int column) {
		if(column > 0) {
			editCell(table, row, column);
		}
	}
	
    protected void editCell(JTable table, int row, int column){ 
        JTextArea textArea = new JTextArea(10, 50); 
        Object value = table.getValueAt(row, column); 
       
        if(value!=null){ 
            textArea.setText((String)value); 
            textArea.setCaretPosition(0); 
        } 
        
        TableNode tablenode = (TableNode)table;
    	
    	FramNode.connectionPoints conn = FramNode.connectionPoints.valueOf(table.getValueAt(row, 0).toString());
    	
    	FramNode node = tablenode.getNode();
    	ArrayList<Aspect> aspList = node.getAttribute(conn);
    	aspList.add(new Aspect(""));
    	node.setAttribute(conn, aspList);
        
//        int result = JOptionPane.showOptionDialog(table 
//                , new JScrollPane(textArea), (String)table.getColumnName(column) 
//                , JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null); 
//        if(result==JOptionPane.OK_OPTION) {
////        	getRowModel().addRow(aRow);
////        	getColumnModel().addColumn(aColumn);
////        	table.addColumn();
//        	
//        	TableNode tablenode = (TableNode)table;
//        	
//        	FramNode.connectionPoints conn = FramNode.connectionPoints.valueOf(table.getValueAt(row, 0).toString());
//        	
//        	FramNode node = tablenode.getNode();
//        	ArrayList<Aspect> aspList = node.getAttribute(conn);
//        	aspList.add(new Aspect(""));
//        	node.setAttribute(conn, aspList);
//        }
    }
}
