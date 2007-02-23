package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import data.ConnectionInfo;
import data.FramNode;
import data.FramNodeList;


public class Visualizer extends JComponent {



	private FramNodeList list;
	private FramNode selectedNode;
	private ArrayList<GraphNode> guiList;
	private Point mouseDownPoint;
	private Point nodeOriginalPoint;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3214964902776275054L;

	public Visualizer() {
		setList(new FramNodeList("empty"));
		
		init();
	}
	
	public Visualizer(FramNodeList list) {
		setList(list);
		
		init();
	}
	
	public FramNode getNodeAt(Point position) {
		FramNode node = null;
		
		for(FramNode n : list) {
			if(n.getRectangle().contains(position)) {
				node = n;
				break;
			}
		}
		
		return node;
	}
	
	private void selectNode(FramNode node) {
		if(node == null) {
			selectedNode = null;
			nodeOriginalPoint = null;
		}
		else {
			selectedNode = node;
			nodeOriginalPoint = (Point)node.getPosition().clone();
		}
	}
	public FramNode getSelectedNode() {
		return selectedNode;
	}
		
	private void init() {
		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {
				FramNode node = getSelectedNode();
				if(node != null) {
					int xDiff = arg0.getX() - mouseDownPoint.x;
					int yDiff = arg0.getY() - mouseDownPoint.y;
					
					node.setPosition(new Point(
							nodeOriginalPoint.x + xDiff,
							nodeOriginalPoint.y + yDiff));
					
					repaint();
					
				}
				
			}

			public void mouseMoved(MouseEvent arg0) {
				
				
			}
			
		});
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0) {
				mouseDownPoint = (Point)arg0.getPoint().clone();
				FramNode node = getNodeAt(mouseDownPoint);
				
				selectNode(node);
				repaint();
				
			}

			public void mouseReleased(MouseEvent arg0) {
				mouseDownPoint = null;
				
			}
			
		});
	}
	

	public void setList(FramNodeList list) {
		this.list = list;
		
		list.addListChangedListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				generateGuiNodes();

			}
		});

		generateGuiNodes();
		 
	}

	private void generateGuiNodes() {
		guiList = new ArrayList<GraphNode>();
		
		int spacer = 100;
		
		Point p = new Point();
		p.x = 0;
		p.y = 0;
		int maxX = (int)Math.floor(Math.sqrt(list.size())) * spacer;
		for(FramNode node : list) {
			guiList.add(new GraphNode(node, this));
			if(node.getPosition().x == 0 &&
					node.getPosition().y == 0) {
				node.setPosition(p);
			}
			if(p.x >= maxX) {
				p.x = 0;
				p.y+= spacer;
			}
			else {
				p.x+= spacer;
			}
		}
		repaint();
	}

	public void paintComponent(Graphics g) {
		
		//g.translate(20, 20);
		
		//System.out.println("paint");
		g.setColor(Color.black);

		GraphNode nodeFrom;
		GraphNode nodeTo;
		Point pointFrom;
		Point pointTo;
		if(list.size() > 0) {
			
			for(ConnectionInfo conInfo : list.searchConnections()) {

				nodeFrom = getGuiNode(conInfo.getFrom().getNode());
				nodeTo = getGuiNode(conInfo.getTo().getNode());
	
				pointFrom = nodeFrom.getPort(conInfo.getFrom().getConnectionPort());
				pointTo = nodeTo.getPort(conInfo.getTo().getConnectionPort());		
	
				if(conInfo.getVisibility()){
					g.drawLine(pointFrom.x, pointFrom.y, pointTo.x, pointTo.y);
					
					Line2D line = new Line2D.Double();
					line.setLine(pointFrom, pointTo);
					
					g.drawString(
							conInfo.getAspect(), 
							(int)line.getBounds().getCenterX(), 
							(int)line.getBounds().getCenterY());
					
				}
			}
			
			
			for(GraphNode guinode : guiList) {
				guinode.paintComponent(g);
			}
		
		}
	}

	private GraphNode getGuiNode(FramNode node) {
		for(GraphNode guinode : guiList) {
			if(node == guinode.getNode()) {
				return guinode;
			}
		}

		return null;
	}
}
