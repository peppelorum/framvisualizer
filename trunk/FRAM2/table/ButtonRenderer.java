package table;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


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
