package graph;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;

import data.ConnectionInfo;
import data.FramNode;
import data.FramNodeList;


public class Visualizer extends JComponent {



	private FramNodeList list;
	private FramNode selectedNode;
	private ConnectionInfo selectedLine;
	private ArrayList<GraphNode> guiNodeList;
	private ArrayList<GraphLine> guiLineList;
	private Point mouseDownPoint;
	private Point nodeOriginalPoint;

	private Point offset = new Point(50, 50);
	private Point originalOffset;
	
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
	
	public ConnectionInfo getSelectedLine() {
		return selectedLine;
	}
		
	private void init() {
		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {
				FramNode node = getSelectedNode();
				Point newLocation = addOffset(arg0.getPoint());
				
				int xDiff = newLocation.x - mouseDownPoint.x;
				int yDiff = newLocation.y - mouseDownPoint.y;
				
				if(node != null) {
					
					node.setPosition(new Point(
							nodeOriginalPoint.x + xDiff,
							nodeOriginalPoint.y + yDiff));
					
					repaint();
					
				}
				else {
										
					mouseDownPoint.x -= offset.x;
					mouseDownPoint.y -= offset.y;
					
					offset.x = originalOffset.x + xDiff;
					offset.y = originalOffset.y + yDiff;
					
					mouseDownPoint.x += offset.x;
					mouseDownPoint.y += offset.y;
					
					repaint();
				}
				
			}

			public void mouseMoved(MouseEvent arg0) {
				
				
			}
			
		});
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				
				
			}

			public void mouseEntered(MouseEvent arg0) {
				
				
			}

			public void mouseExited(MouseEvent arg0) {
				
				
			}

			public void mousePressed(MouseEvent arg0) {
				mouseDownPoint = addOffset(arg0.getPoint());
				FramNode node = getNodeAt(removeOffset(removeOffset(mouseDownPoint)));
								
				originalOffset = (Point)offset.clone();
				
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
		guiNodeList = new ArrayList<GraphNode>();
		guiLineList = new ArrayList<GraphLine>();
		
		int spacer = 100;
		
		Point p = new Point();
		p.x = 0;
		p.y = 0;
		int maxX = (int)Math.floor(Math.sqrt(list.size())) * spacer;
		for(FramNode node : list) {
			guiNodeList.add(new GraphNode(node, this));
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
		
		for(ConnectionInfo connection : list.searchConnections()) {
			guiLineList.add(new GraphLine(connection, this));
		}
		
		repaint();
	}

	public void paintComponent(Graphics g) {
		g.translate(offset.x, offset.y);
		
		if(list.size() > 0) {
			
			for(GraphNode guinode : guiNodeList) {
				guinode.paintComponent(g);
			}
			
			for(GraphLine guiline : guiLineList) {
				guiline.paintComponent(g);
			}			

			for(GraphLine guiline : guiLineList) {
				guiline.paintNameBubble(g);
			}
			
			for(GraphNode guinode : guiNodeList) {
				guinode.paintNameBubble(g);
			}
		
		}
	}

	public GraphNode getGuiNode(FramNode node) {
		for(GraphNode guinode : guiNodeList) {
			if(node == guinode.getNode()) {
				return guinode;
			}
		}

		return null;
	}
	
	public Point addOffset(Point input) {
		Point changed = (Point)input.clone();
		
		changed.x += offset.x;
		changed.y += offset.y;
		
		return changed;
	}
	
	public Point removeOffset(Point input) {
		Point changed = (Point)input.clone();
		
		changed.x -= offset.x;
		changed.y -= offset.y;
		
		return changed;
	}
}
