package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;

import data.FramNode;
import data.FramNode.connectionPoints;


public class GraphNode extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4452948217399228756L;
	private FramNode node;
	private Visualizer parent;

	public GraphNode(FramNode node, Visualizer parent) {
		this.node = node;
		this.parent = parent;
	}

	public boolean isSelected() {
		return parent.getSelectedNode() == node;
	}
	
	public FramNode getNode() {
		return node;
	}

	public Point getCenter() {
		Rectangle rect = node.getRectangle();
		
		return new Point(rect.x + rect.width/2,
				rect.y + rect.height / 2);
	}

	public Polygon getPolygon() {
		Rectangle rect = node.getRectangle();
		Polygon poly = new Polygon();
		
		poly.addPoint(rect.x + rect.width/4, rect.y);
		poly.addPoint(rect.x + rect.width/4*3, rect.y);
		poly.addPoint(rect.x + rect.width, rect.y + rect.height/2);
		poly.addPoint(rect.x + rect.width/4*3, rect.y + rect.height);
		poly.addPoint(rect.x + rect.width/4, rect.y + rect.height);
		poly.addPoint(rect.x, rect.y + rect.height/2);	
		
		return poly;
	}
	
	public Point getPort(FramNode.connectionPoints connPoint) {
		Point p = null;
		Polygon poly = getPolygon();
		
		for(int i = 0; i < FramNode.connectionPoints.values().length; i++) {
			if(connPoint == FramNode.connectionPoints.values()[i]) {
				p = new Point(
						poly.xpoints[i],
						poly.ypoints[i]);
				break;
			}
		}
		
		return p;
	}
	
	public Point getCloserToCenter(Point input, int distance) {
		Point modified = (Point)input.clone();
		Point center = getCenter();
		
		if(modified.x > center.x) {
			modified.x -= distance;
		}
		else if(modified.x < center.x) {
			modified.x += distance;
		}

		if(modified.y > center.y) {
			modified.y -= distance;
		}
		else if(modified.y < center.y) {
			modified.y += distance;
		}
		
		return modified;
	}
	
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.getHSBColor(0.1F, 0.6F, 0.8F));
		if(isSelected()) {
			g.setColor(Color.getHSBColor(0.1F, 0.6F, 0.5F));				
		}

		Polygon poly = getPolygon();
		
		g.fillPolygon(poly);
		
		for(int i = 0; i < poly.npoints; i++) {
			g.fillOval(poly.xpoints[i]-5, poly.ypoints[i]-5, 10, 10);
		}
	
		//if(isSelected()) {
			g.setColor(Color.white);
			g.setFont(new Font("Arial", 1, 10));
			for(connectionPoints conn : FramNode.connectionPoints.values()) {
				Point loc = getCloserToCenter(getPort(conn), 2);
				loc.x -= 4;
				loc.y += 4;
				g.drawString(conn.toString().substring(0, 1), loc.x, loc.y);
			}			
//		}
		
		//g.fillRoundRect(rect.x, rect.y, rect.width, rect.height, 5, 5);
		g.setColor(Color.black);
		g.setFont(new Font("Times New Roman", 1, 10));
		g.drawString(node.getName(), getCenter().x-(node.getName().length()*5)/2 , getCenter().y);
	
	}
}