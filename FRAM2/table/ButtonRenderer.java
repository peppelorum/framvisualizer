package table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import data.FramNode;
import data.FramNodeList;


public class ButtonRenderer extends Component implements TableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean focused;
	public ImageIcon PLUS_ICON; 
	private JButton customEditorButton = new JButton(new ImageIcon(getClass().getResource("/icons/plus.GIF")));
	private JButton moveUp = new JButton(new ImageIcon(getClass().getResource("/icons/bigger.GIF")));
	private JButton moveDown = new JButton(new ImageIcon(getClass().getResource("/icons/broad.GIF")));

	private FramNodeList list;
	private FramNode node;

	public void ButtonRender(){

	}

	// Maybe it works ... it seems
	// (of course this is a cheat, but this isn't a real component anyway)
	public boolean hasFocus()
	{
		return focused;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean selected, boolean focused, int row, int column) {	
		
		if (row == 0){
			return new JPanel();
		}

		JPanel panel = new JPanel(); 
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS)); 
		
		JLabel a = new JLabel(new ImageIcon(getClass().getResource("/icons/plus.GIF")));
		a.setVisible(true);
		panel.add(a);
		
		a = new JLabel(new ImageIcon(getClass().getResource("/icons/minus.GIF")));
		a.setVisible(true);
		panel.add(a);

 
		return panel; 
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
