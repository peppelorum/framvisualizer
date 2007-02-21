package prisjakt;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
 
 
public class Test {
	
	public Test() {
		JFrame frame = new JFrame();
		frame.addWindowListener(new WindowAdapter () {
			
			public void windowClosing (WindowEvent e) {
				System.exit(0);
			}
		});
		
		// Creates the shapes used to draw on the panel
		Shape[] shapes = {
			new Rectangle2D.Double(40, 50, 20, 20),
			new Rectangle2D.Double(80, 50, 20, 20),
			new Line2D.Double(70, 90, 150, 230)
		};
 
		// Creates the label to put in the panel's header 
		JLabel label = new JLabel("Test1");
 
		// Creates the panel with the shapes + the label
		JPanel shapeArea = new ShapesPanel(shapes);
		shapeArea.setBackground(Color.WHITE);
		shapeArea.setPreferredSize(new Dimension(500, 600));
		shapeArea.add(label);
		
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(shapeArea);
		frame.pack();
		frame.setVisible(true);
		
	}
 
	/**
	 * Custom extended JPanel class, to hold the shapes 
	 *
	 */
	private class ShapesPanel extends JPanel {
 
		Shape[] shapes;
		
		protected ShapesPanel (Shape[] shapes) {
			super();
			this.shapes = shapes;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			
			// draw the shapes
			for (int i = 0; i < shapes.length; i++) {
				g2.draw(shapes[i]);
			}
		}
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Test();
	}
}
