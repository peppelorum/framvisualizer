package table;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;

import data.FramNode;
import data.FramNodeList;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor{
	private JButton editora;
	
//	Is it possible to avoid the permanent reference to the value???
//	(i.e. after editing has been stopped/cancelled?)
	private Object value;
	
//	public ButtonEditor(JButton b)
//	{
//		editora = b;
//		
//		editora.setContentAreaFilled(true);
//	}
	
	public final Icon PLUS_ICON = new ImageIcon(getClass().getResource("plus.gif")); 
	private TableCellEditor editor; 
	private JButton customEditorButton = new JButton(PLUS_ICON);
	private JButton moveUp = new JButton(PLUS_ICON);
	private JButton moveDown = new JButton(PLUS_ICON);
	protected JTable table; 
	protected int row, column; 
	private JPanel panel = new JPanel();
	
	private FramNodeList list;
	private FramNode node;
	
	public ButtonEditor(TableCellEditor editor, FramNodeList lista, FramNode nodea){ 
		this.editor = editor; 
		this.list = lista;
		this.node = nodea;
		//customEditorButton.addActionListener(this); 
		
		// ui-tweaking 
		customEditorButton.setFocusable(false); 
		customEditorButton.setFocusPainted(false); 
		customEditorButton.setMargin(new Insets(0, 0, 0, 0));
		customEditorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("NEW NODE! NEW NODE READY!");
				addFunction(table, row, column);
				list.add(new FramNode());
			}
		});
		
		moveUp.setFocusable(false); 
		moveUp.setFocusPainted(false); 
		moveUp.setMargin(new Insets(0, 0, 0, 0)); 
		moveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Move on up! And keep on wishing!");
				list.moveUpNode(node);
			}
		});
		
		moveDown.setFocusable(false); 
		moveDown.setFocusPainted(false); 
		moveDown.setMargin(new Insets(0, 0, 0, 0)); 
	} 
	
	public JButton editor()
	{
		return editora;
	}
	
//	public JPanel getTableCellEditorComponent(JTable table, Object value, boolean selected, int row, int column) {
//		//this.value = value;
//		
//		//editora.setText(value == null ? "" : value.toString());
//		
//		// possibly adjust more colors/properties
//		
//		//editora.setForeground(table.getForeground());
//		
//		return panel;
//	}
	
	public Component getTableCellEditorComponent(JTable table, Object value
			, boolean isSelected, int row, int column){ 
		JPanel panel = new JPanel(new FlowLayout()); 
		editor.getTableCellEditorComponent(table, value, isSelected, row, column); 
		panel.add(customEditorButton);
		panel.add(moveUp);
		panel.add(moveDown);
		this.table = table; 
		this.row = row; 
		this.column = column; 
		return panel; 
	} 
	
	public Object getCellEditorValue()
	{
		return value;
	}
	
	protected void addFunction(JTable table, int row, int column){ 
		JTextArea textArea = new JTextArea(10, 50); 
		Object value = table.getValueAt(row, column); 
		
		if(value!=null){ 
			textArea.setText((String)value); 
			textArea.setCaretPosition(0); 
		} 
		
		
		
		FramAspectTable tablenode = (FramAspectTable)table;
		System.out.println(tablenode.getName());
		
		System.out.println(row);
		
		
	}
	
}