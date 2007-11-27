package graph;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import data.FramFunction;
import data.FramFunctionList;

public class GraphMenu extends Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Visualizer vis;
	FramFunctionList list;
	
	public GraphMenu(FramFunctionList lista, Visualizer visa, JComponent comp){
		this.list = lista;
		this.vis = visa;
				
		JMenuItem menuItem;
		
		menuItem = new JMenuItem("Zoom to 100%");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis.setZoomFactor(1, new Point(100, 100));
				vis.repaint();
			}
		});
		comp.add(menuItem);
		
		menuItem = new JMenuItem("New node");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				list.add(new FramFunction());
				vis.repaint();
			}
		});
		
		comp.add(menuItem);

	
		
	}
}
