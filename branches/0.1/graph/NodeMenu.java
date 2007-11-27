package graph;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import data.FramFunctionList;

public class NodeMenu extends Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Visualizer vis;
	
	public NodeMenu(FramFunctionList list, Visualizer visa, JComponent comp){
		
		this.vis = visa;
				
		JMenuItem menuItem;
		
		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis.deleteSelectedNode();
				vis.repaint();
			}
		});
		comp.add(menuItem);
		
		menuItem = new JMenuItem("Bigger");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis.getSelectedNode().setSize(vis.getSelectedNode().getSize()+5);
				vis.repaint();
			}
		});
		comp.add(menuItem);
		
		menuItem = new JMenuItem("Smaller");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis.getSelectedNode().setSize(vis.getSelectedNode().getSize()-5);
				vis.repaint();
			}
		});
		comp.add(menuItem);
		
		menuItem = new JMenuItem("Flag");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis.getSelectedNode().setFlagged(!vis.getSelectedNode().isFlagged());
//				updateFlagNodeButton();
				vis.repaint();
			}
		});
		comp.add(menuItem);
		
//		return comp;
		
	}
	
//	public JComponent create(JComponent component) {
//		
//		
//		
////		JMenuBar menuBar = new JMenuBar();
//
//		//...where the GUI is constructed:
//		//Create the popup menu.
////		popup = new JPopupMenu();
//		
//		JMenuItem menuItem;
//		
//		menuItem = new JMenuItem("Delete");
//		menuItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				vis.deleteSelectedNode();
//				vis.repaint();
//			}
//		});
//		component.add(menuItem);
//		
//		menuItem = new JMenuItem("Bigger");
//		menuItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				vis.getSelectedNode().setSize(vis.getSelectedNode().getSize()+5);
//				vis.repaint();
//			}
//		});
//		component.add(menuItem);
//		
//		menuItem = new JMenuItem("Smaller");
//		menuItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				vis.getSelectedNode().setSize(vis.getSelectedNode().getSize()-5);
//				vis.repaint();
//			}
//		});
//		component.add(menuItem);
//		
//		menuItem = new JMenuItem("Flag");
//		menuItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				vis.getSelectedNode().setFlagged(!vis.getSelectedNode().isFlagged());
////				updateFlagNodeButton();
//				vis.repaint();
//			}
//		});
//		component.add(menuItem);
//		
//		return component;
//	}

	

}
