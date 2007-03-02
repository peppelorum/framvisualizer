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
} 
