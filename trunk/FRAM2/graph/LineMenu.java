package graph;

import java.awt.Color;
import java.awt.Component;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import data.FramFunctionList;

public class LineMenu extends Component implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Visualizer vis;
	private JMenuItem toggleHideLine;

	private final Icon ICON_HIDE = new ImageIcon(getClass().getResource("/icons/hide.GIF"));
	private final Icon ICON_SHOW = new ImageIcon(getClass().getResource("/icons/eye.GIF"));

	public class color {
		public String name;
		public Color color;

		public color(String name, Color color)
		{
			this.name = name;
			this.color = color;
		}		
	}

	public LineMenu(FramFunctionList list, Visualizer visa, JComponent comp){

		this.vis = visa;

		JMenuItem menuItem;
		JMenu colorizeMenu;

		
		colorizeMenu = new JMenu("Colorize");
		ArrayList<color> colors = new ArrayList<color>();

		
		
		colors.add(new color("Black", Color.black));
		colors.add(new color("Gray", Color.LIGHT_GRAY));
		colors.add(new color("Green", Color.green));
		colors.add(new color("Orange", Color.orange));
		colors.add(new color("Red", Color.red));
		colors.add(new color("Blue", Color.blue));
		
	
		
		for(color a : colors) {
			menuItem = new JMenuItem(a.name);
			menuItem.setBackground(a.color);
			menuItem.addActionListener(this);
			colorizeMenu.add(menuItem);
		}
		comp.add(colorizeMenu);
		
		toggleHideLine = new JMenuItem();
		updateHideLineButton();
		toggleHideLine.setText("Hide line");
		toggleHideLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis.getSelectedLine().setVisibility(!vis.getSelectedLine().getVisibility());
				updateHideLineButton();
				vis.repaint();
			}
		});
		comp.add(toggleHideLine);

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
	}

	public void updateHideLineButton() {
		if(vis.getSelectedLine() != null &&
				vis.getSelectedLine().getVisibility()) {
			toggleHideLine.setIcon(ICON_HIDE);
			toggleHideLine.setText("Hide line");
		}
		else {
			toggleHideLine.setIcon(ICON_SHOW);
			toggleHideLine.setText("Show line");
		}
	}


	public void actionPerformed(ActionEvent e) {

		JMenuItem a = (JMenuItem)e.getSource();

		vis.getSelectedLine().setLineColor(a.getBackground());
		vis.repaint();


	}
}
