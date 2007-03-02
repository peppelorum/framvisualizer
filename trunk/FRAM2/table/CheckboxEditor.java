package table;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;

import data.Aspect;
import data.FramNode;
import data.FramNodeList;

public class CheckboxEditor extends AbstractCellEditor implements TableCellEditor{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	Is it possible to avoid the permanent reference to the value???
//	(i.e. after editing has been stopped/cancelled?)
	private Object value;
	 
	private TableCellEditor editor; 
	private JButton addButton = new JButton(new ImageIcon(getClass().getResource("/icons/plus.GIF")));
	private JButton removeButton = new JButton(new ImageIcon(getClass().getResource("/icons/minus.GIF")));
	
	protected JTable table; 
	protected int row, column; 
	
	private FramNodeList list;
	private FramNode node;
	
	public CheckboxEditor(){ 


		Border empty;
		empty = BorderFactory.createEmptyBorder();
		
		// ui-tweaking 
		addButton.setFocusable(false); 
		addButton.setFocusPainted(false); 
		addButton.setMargin(new Insets(0, 0, 0, 0));
		addButton.setBorder(empty);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAspect(table, row, column);
			}
		});
	}
	
		
	public Component getTableCellEditorComponent(JTable table, Object value
			, boolean isSelected, int row, int column){ 
		JCheckBox panel = new JCheckBox(); 
//		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS)); 
//		editor.getTableCellEditorComponent(table, value, isSelected, row, column); 
//		panel.add(addButton);
//		panel.add(removeButton);
//
//		this.table = table; 
//		this.row = row; 
//		this.column = column; 
		return panel; 
	} 
	
	public Object getCellEditorValue()
	{
		return value;
	}
		
    protected void addAspect(JTable table, int row, int column){
    	
    	System.out.println("addAspect");
        JTextArea textArea = new JTextArea(10, 50);
        Object value = table.getValueAt(row, column);
       
        if(value!=null){
            textArea.setText((String)value);
            textArea.setCaretPosition(0);
        }
        
        FramAspectTable tablenode = (FramAspectTable)table;
    	
    	FramNode.NodePort conn = FramNode.NodePort.valueOf(table.getValueAt(row, 0).toString());
    	
    	FramNode node = tablenode.getNode();
    	ArrayList<Aspect> aspList = node.getAttributes(conn);
    	aspList.add(new Aspect(""));
    	
    	node.setAttributes(conn, aspList);
    }
    
}