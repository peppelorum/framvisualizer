package table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import data.FramNode;
import data.FramNodeList;


public class ButtonRenderer extends JPanel implements TableCellRenderer{
    private boolean focused;
	public final Icon PLUS_ICON = new ImageIcon(getClass().getResource("plus.gif")); 
	private JButton customEditorButton = new JButton(PLUS_ICON);
	private JButton moveUp = new JButton(new ImageIcon(getClass().getResource("bigger.gif")));
	private JButton moveDown = new JButton(new ImageIcon(getClass().getResource("broad.gif")));
	
	private FramNodeList list;
	private FramNode node;

//    {
//        setContentAreaFilled(true);
//    }
    
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
//		customEditorButton.
		
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
//        setEnabled(table.isEnabled());
//
//        setForeground(table.getForeground());
//
//        setText(value == null ? "" : value.toString());
//
//        this.focused = focused;
    	
    	this.removeAll();

		this.setLayout(new FlowLayout()); 

		this.add(customEditorButton);
		this.add(moveUp);
		this.add(moveDown);
		this.add(new JButton("hej"));

		return this; 
    }
    
    

} 
