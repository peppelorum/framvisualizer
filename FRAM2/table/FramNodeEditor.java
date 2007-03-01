/**

 	A visualizer for FRAM (Functional Resonance Accident Model).
 	This tool helps modelling the the FRAM table and visualize it.
	Copyright (C) 2007  Peppe Bergqvist <peppe@peppesbodega.nu>, Fredrik Gustafsson <fregu808@student.liu.se>,
	Jonas Haraldsson <haraldsson@gmail.com>, Gustav Ladén <gusla438@student.liu.se>
	http://sourceforge.net/projects/framvisualizer/

	This program is free software; you can redistribute it and/or
	modify it under the terms of the GNU General Public License
	as published by the Free Software Foundation; either version 2
	of the License, or (at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program; if not, write to the Free Software
	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

 **/

package table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import data.FramNode;
import data.FramNodeList;

public class FramNodeEditor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 197489600332691308L;

	private FramNode node;
	private FramAspectTable tableNode;

	//private boolean visible = true;

	private FramNodeEditorList editorParent;
	private Container buttonsTop = new JPanel();
	private Container buttonsBottom = new JPanel();
	
	private void moveBox(int xRelative, int yRelative) {
		int x = xRelative + getX();
		int y = yRelative + getY();
		
		if(getParent().getComponentAt(x, y)
				!= this) {
			if(y <= getY()) {
				moveBox(-1);
			}
			else {
				moveBox(1);
			}
		}
	}
	
	public void moveBox(int direction) {
		
		int position = 0;
		for(int i = 0; i < getParent().getComponentCount(); i++) {
			if(getParent().getComponent(i) == this) {
				position = i;
				break;
			}
		}

		int newPosition = position + direction;
		
		if(newPosition < getParent().getComponentCount() && newPosition >= 0) {
		
			getParent().add(this, newPosition);
			getParent().validate();
			getParent().repaint();
		}
	}

	public FramNodeEditor(FramNode node, FramNodeEditorList editorList) {

		this.editorParent = editorList;

		this.setVisible(true);
		//this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(200, 100);

		this.node = node;
		tableNode = new FramAspectTable(node, editorList.getList(), this);

		JButton buttonMove = new JButton(new ImageIcon(getClass().getResource("/icons/bigger.gif")));
		buttonMove.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {
				moveBox(arg0.getX(), arg0.getY());
			}

			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}


		});


		buttonsTop.add(buttonMove);

		JButton addNewNodeBefore = new JButton(new ImageIcon(getClass().getResource("/icons/plus.gif")));
		//addNewNodeBefore.setText("Add new before");
		addNewNodeBefore.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		JButton addNewNodeAfter = new JButton(new ImageIcon(getClass().getResource("/icons/plus.gif")));
		//addNewNodeAfter.setText("Add new after");
		addNewNodeAfter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		buttonsTop.add(addNewNodeBefore);
		buttonsBottom.add(addNewNodeAfter);

		buttonsTop.setLayout(new BoxLayout(buttonsTop, BoxLayout.X_AXIS));
		buttonsBottom.setLayout(new BoxLayout(buttonsBottom, BoxLayout.X_AXIS));
		this.add(buttonsTop);
		this.add(tableNode);
		this.add(buttonsBottom);

		deSelected();
	}

	public FramNode getNode() {
		return node;
	}


	public boolean getVisibility(){
		return this.isVisible();	
	}

	public void cleanUp() {
		tableNode.cleanUp();
	}

	public FramAspectTable getTableNode() {
		return tableNode;
	}

	public boolean isSelected() {
		return editorParent.getSelectedNodeEditor() == this;
	}

	public void select() {
		editorParent.setSelectedNodeEditor(this);
	}

	public void selected() {
		buttonsTop.setVisible(true);
		buttonsBottom.setVisible(true);
		//this.setBorder(BorderFactory.createLineBorder (Color.blue, 2));
	}

	public void deSelected() {
		buttonsTop.setVisible(false);
		buttonsBottom.setVisible(false);
		//this.setEnabled(false);
		//this.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(0f,0f,0.9f), 2));
	}

	public void paint(Graphics g) {
		super.paint(g);
	}
}
