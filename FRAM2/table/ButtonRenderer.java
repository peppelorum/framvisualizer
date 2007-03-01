package table;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import data.FramNode;
import data.FramNodeList;


public class ButtonRenderer extends JPanel implements TableCellRenderer, TableCellEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean focused;
	public ImageIcon PLUS_ICON; 
	private JButton customEditorButton = new JButton(new ImageIcon(getClass().getResource("/icons/plus.gif")));
	private JButton moveUp = new JButton(new ImageIcon(getClass().getResource("/icons/bigger.GIF")));
	private JButton moveDown = new JButton(new ImageIcon(getClass().getResource("/icons/broad.GIF")));

	private FramNodeList list;
	private FramNode node;

	public void ButtonRender(){
		// ui-tweaking 
		customEditorButton.setFocusable(false); 
		customEditorButton.setFocusPainted(false); 
		customEditorButton.setMargin(new Insets(0, 0, 0, 0));
		customEditorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("NEW NODE! NEW NODE READY!");
				list.add(new FramNode());
			}
		});
		customEditorButton.setSize(10, 10);

		moveUp.setFocusable(false); 
		moveUp.setFocusPainted(false); 
		moveUp.setMargin(new Insets(0, 0, 0, 0)); 
		moveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Move on up! And keep on wishing!");
				list.moveUpNode(node);
			}
		});
		moveUp.setSize(10, 10);


		moveDown.setFocusable(false); 
		moveDown.setFocusPainted(false); 
		moveDown.setMargin(new Insets(0, 0, 0, 0)); 
		moveDown.setSize(10, 10);
	}

	// Maybe it works ... it seems
	// (of course this is a cheat, but this isn't a real component anyway)
	public boolean hasFocus()
	{
		return focused;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column)
	{		
		this.removeAll();

		this.setLayout(new FlowLayout()); 

		this.add(customEditorButton);
		this.add(moveUp);
		this.add(moveDown);
		this.add(new JButton("hej"));

		return this; 
	}

	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		this.removeAll();

		this.setLayout(new FlowLayout()); 

		this.add(customEditorButton);
		this.add(moveUp);
		this.add(moveDown);
		this.add(new JButton("hej"));

		return this; 
	}

	public void addCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void cancelCellEditing() {
		// TODO Auto-generated method stub
		
	}

	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCellEditable(EventObject arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean shouldSelectCell(EventObject arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		return false;
	}



} 
