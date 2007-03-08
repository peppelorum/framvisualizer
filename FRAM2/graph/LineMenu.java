package graph;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import data.FramFunctionList;

public class LineMenu extends Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Visualizer vis;
	private JMenuItem toggleHideLine;
	
	private final Icon ICON_HIDE = new ImageIcon(getClass().getResource("/icons/hide.GIF"));
	private final Icon ICON_SHOW = new ImageIcon(getClass().getResource("/icons/eye.GIF"));
	
	public LineMenu(FramFunctionList list, Visualizer visa, JComponent comp){
		
		this.vis = visa;
				
		JMenuItem menuItem;
		JMenu colorizeMenu;
		
		toggleHideLine = new JMenuItem();
//		updateHideLineButton();
		toggleHideLine.setText("Hide line");
		toggleHideLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis.getSelectedLine().setVisibility(!vis.getSelectedLine().getVisibility());
				updateHideLineButton();
				vis.repaint();
			}
		});
		comp.add(toggleHideLine);
		
		
		colorizeMenu = new JMenu("Colorize");
		
				
		Icon red_icon = new ImageIcon(getClass().getResource("/icons/color_red.GIF"));
		menuItem = new JMenuItem(red_icon);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis.getSelectedLine().setLineColor(Color.RED);
				vis.repaint();
			}
		});
		comp.add(menuItem);
		
		Icon black_icon = new ImageIcon(getClass().getResource("/icons/color_black.GIF"));
		menuItem = new JMenuItem(black_icon);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis.getSelectedLine().setLineColor(Color.BLACK);
				vis.repaint();
			}
		});
		comp.add(menuItem);
		
		Icon green_icon = new ImageIcon(getClass().getResource("/icons/color_green.GIF"));
		menuItem = new JMenuItem(green_icon);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis.getSelectedLine().setLineColor(Color.GREEN);
				vis.repaint();
			}
		});
		comp.add(menuItem);
		
		Icon unlock_icon = new ImageIcon(getClass().getResource("/icons/unlock.GIF"));
		menuItem = new JMenuItem(unlock_icon);
		menuItem.setText("unlock");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis.getSelectedLine().setMoved(false);
				vis.repaint();
			}

		});
		
		comp.add(menuItem);

//		this.add(toggleHideLine);

		
	}
	
	public void updateHideLineButton() {
		if(vis.getSelectedLine() != null &&
				vis.getSelectedLine().getVisibility()) {
//			toggleHideLine.setIcon(ICON_HIDE);
			toggleHideLine.setText("Hide line");
		}
		else {
//			toggleHideLine.setIcon(ICON_SHOW);
			toggleHideLine.setText("Show line");
		}
	}
//	

	

}
