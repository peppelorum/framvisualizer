package table;

import java.awt.Component;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomCellRenderer extends DefaultTableCellRenderer 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 565106872194092865L;

	public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column) 
    {
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);
//        System.out.println("CustomRender");
        if(value == "Network name"){
        	cell.setFont(new Font("Dialog",Font.PLAIN,14));
        }else if(value == "Name"){
        	cell.setFont(new Font("Dialog",Font.BOLD,12));
        	cell.setBackground(Color.lightGray);
        }else if(table.getModel().getValueAt(row, 0) == "Name"){
        	cell.setFont(new Font("Dialog",Font.BOLD,12));
        	cell.setBackground(Color.lightGray);
        }else{
        	cell.setBackground(Color.white);
        }
      
        return cell;
    }
}