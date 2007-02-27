package table;

import javax.swing.table.DefaultTableModel;

import data.CPC;

@SuppressWarnings("serial")
public class FramCPCTableModel extends DefaultTableModel {
	
	public FramCPCTableModel(CPC cpc){
		
		String[] colNames = {"Port", "Aspect", "Comment"};
		this.setColumnIdentifiers(colNames);
		this.setColumnCount(3);
		
		for(String[] row : cpc.getCPC()) {
			this.addRow((Object[])row);
		}
	}

}
