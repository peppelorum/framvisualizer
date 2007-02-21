package prisjakt;

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;

public class LayeredPaneDemo extends JFrame {
	
	String[] layerStrings = { "Default Layer", "Palette Layer", "Modal Layer",
			"Popup Layer", "Drag Layer" };
	
	Integer[] layerValues = { JLayeredPane.DEFAULT_LAYER,
			JLayeredPane.PALETTE_LAYER,
			JLayeredPane.MODAL_LAYER,
			JLayeredPane.POPUP_LAYER,
			JLayeredPane.DRAG_LAYER };
	
	Color[] layerColors = { Color.yellow, Color.magenta, Color.cyan,
			Color.red, Color.green };
	
	JLayeredPane layeredPane;
	
	public LayeredPaneDemo()    {
		
		//Create the dragging area at the bottom of the window
		Dimension layeredPaneSize = new Dimension(300, 310);
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(layeredPaneSize);
		
		//Add dragging area and control pane to demo frame
		Container contentPane = getContentPane();
		contentPane.add(layeredPane);
		
		
		//Use a layout manager that respects preferred sizes
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		
		
		//Layered panes don't have a layout manager so
		//we have to use absolute positioning.
		//This is the origin of the default layer's label
		//and the size of all the layers' labels.
		Point origin = new Point(10, 20);
		Dimension size = new Dimension(140, 140);
		
		//For each layer, add a colored label overlapping the last
		for (int i = 0 ; i < layerValues.length; i++) {
			
			//Create and set up colored label
			JLabel label = new JLabel(layerStrings[i]);
			label.setVerticalAlignment(JLabel.TOP);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setOpaque(true);
			//label.setBackground(layerColors[i]);
			label.setForeground(Color.black);
			label.setBorder(BorderFactory.createLineBorder(Color.black));
			label.setBounds(origin.x, origin.y, size.width, size.height);
			
			//Add it to the layered pane
			layeredPane.add(label, layerValues[i]);  
			
			//Adjust origin for next layer
			origin.x += 35;
			origin.y += 35;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new LayeredPaneDemo();
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		frame.pack();
		frame.setVisible(true);
	}
}
