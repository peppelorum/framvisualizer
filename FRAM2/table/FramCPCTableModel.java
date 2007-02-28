package table;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import data.CPC;
import data.CPCAttribute;

@SuppressWarnings("serial")
public class FramCPCTableModel extends DefaultTableModel {
	
	private CPC cpc;
	
	public FramCPCTableModel(CPC cpc){
	
		this.cpc = cpc;
		
		String title = "CPC attributes for '" + 
			cpc.getParent().getValue() + 
			"' on node '" + cpc.getParent().getParent().getName() + "'";
		
		String[] colNames = {title, "Value", "Comment"};
		this.setColumnIdentifiers(colNames);
		this.setColumnCount(3);
		
		CPCAttribute cpcAttrib;		
		String[] row;
		for(String cpcType : CPC.CPC_TYPES) {
			cpcAttrib = cpc.getAttribute(cpcType);
			if(cpcAttrib != null) {
				row = cpcAttrib.toArray();
			}
			else {
				row = new String[] { cpcType, "", "" };
			}
			
			this.addRow(row);
		}
	
		this.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent arg0) {
				int row = arg0.getFirstRow();
				
				String type = (String)getValueAt(row, 0);
				String value = (String)getValueAt(row, 1);
				String comment = (String)getValueAt(row, 2);
				
				getCPC().setAttribute(type, value, comment);
			}
			
		});
	}
	
	public CPC getCPC() {
		return cpc;
	}

}
