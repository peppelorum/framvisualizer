package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

import data.ConnectionInfo;
import data.FramNode;
import data.FramNode.connectionPoints;


public class GraphLine extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4452948217399228756L;
	private ConnectionInfo connection;
	private Visualizer parent;

	public GraphLine(ConnectionInfo connection, Visualizer parent) {
		this.connection = connection;
		this.parent = parent;
	}

	public boolean isSelected() {
		return parent.getSelectedLine() == connection;
	}
	
	public ConnectionInfo getConnection() {
		return connection;
	}

	public Point getCenter() {
		Line2D line = getLine();
				
		return new Point(
				(int)line.getBounds().getCenterX(), 
				(int)line.getBounds().getCenterY());
	}

	public Line2D getLine() {
		GraphNode nodeFrom = parent.getGuiNode(connection.getFrom().getNode());
		GraphNode nodeTo = parent.getGuiNode(connection.getTo().getNode());
		
		Point pointFrom = nodeFrom.getPort(connection.getFrom().getConnectionPort());
		Point pointTo = nodeTo.getPort(connection.getTo().getConnectionPort());		

		Line2D line = new Line2D.Double();
		line.setLine(pointFrom, pointTo);
	
		return line;
	}
	
	public void paintComponent(Graphics g) {
		if(!connection.getVisibility()) {
			return;
		}
		
		g.setColor(Color.getHSBColor(0.1F, 0.6F, 0.1F));
		if(isSelected()) {
			g.setColor(Color.getHSBColor(0.1F, 0.6F, 0.5F));				
		}

		Line2D line = getLine();
		
		g.drawLine(
				(int)line.getX1(), 
				(int)line.getY1(), 
				(int)line.getX2(), 
				(int)line.getY2());
	
	}
	
	
	public void paintNameBubble(Graphics g) {
		if(!connection.getVisibility()) {
			return;
		}
		
		int bubbleHeight = 10;
		int bubbleWidth = 40;
		int bubbleRounded = 5;
		
		// define coordinates for bubble
		Rectangle bubbleRect = new Rectangle(
				getCenter().x - bubbleWidth/2,
				getCenter().y - bubbleHeight/2,
				bubbleWidth,
				bubbleHeight);
		
		// fill bubble background
		g.setColor(Color.white);
		g.fillRoundRect(
				bubbleRect.x, 
				bubbleRect.y, 
				bubbleRect.width, 
				bubbleRect.height, 
				bubbleRounded, 
				bubbleRounded);
		
		// draw bubble outline
		g.setColor(Color.black);
		g.drawRoundRect(
				bubbleRect.x, 
				bubbleRect.y, 
				bubbleRect.width, 
				bubbleRect.height, 
				bubbleRounded, 
				bubbleRounded);
		
		// draw name
		g.setFont(new Font("Arial", 1, 9));
		g.drawString(
				connection.getAspect(), 
				bubbleRect.x+5,
				bubbleRect.y+8);
	}	
	
}