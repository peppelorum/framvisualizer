/**

 	A visualizer for FRAM (Functional Resonance Accident Model).
 	This tool helps modelling the the FRAM table and visualize it.
	Copyright (C) 2007  Peppe Bergqvist <peppe@peppesbodega.nu>, Fredrik Gustafsson <fregu808@student.liu.se>,
	Jonas Haraldsson <haraldsson@gmail.com>, Gustav Ladï¿½n <gusla438@student.liu.se>
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

package gui;

import graph.GraphLine;
import graph.Visualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import table.FramCPCTable;
import table.FramNodeEditorList;

import data.ConnectionInfo;
import data.FramNode;
import data.FramNodeList;

public class GUI extends JFrame implements ActionListener{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2294328777692036637L;
	
	//Filemanager
	private final JFileChooser fc = new JFileChooser();
    private FRAMfilter framFilter = new FRAMfilter();
    private XMLfilter xmlFilter = new XMLfilter();
    
    //GUI
    private FramNodeEditorList framNodeEditorList = new FramNodeEditorList();
    private Visualizer framVisualizer = new Visualizer();
    
    private FramCPCTable framCPCTable = new FramCPCTable();
    private JSplitPane tableAndGraph;
    private JSplitPane split2;
    private JPanel lineButtons = new JPanel();
    private JPanel nodeButtons = new JPanel();
    private Container tableContainer = new Container();
    private JButton toggleHideLine;
    private JButton newNode;
    
    private boolean showAllLabels = true;
    
	public GUI(){
		framNodeEditorList.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				FramNodeEditorList nodeList = (FramNodeEditorList)e.getSource();
				if(nodeList.getSelectedNode() != null) {
					framCPCTable.setCPC(nodeList.getSelectedNode().getCPC());
					framVisualizer.selectNode(nodeList.getSelectedNode(), null);
				}
				
			}
		});

		framVisualizer.addSelectedChangedListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Visualizer visualizer = (Visualizer)e.getSource();
				if(visualizer.getSelectedNode() != null) {
					framCPCTable.setCPC(visualizer.getSelectedNode().getCPC());
					lineButtons.setVisible(false);
					nodeButtons.setVisible(true);
				}
				else if(visualizer.getSelectedLine() != null) {
					lineButtons.setVisible(true);
					nodeButtons.setVisible(false);
				}
				else {
					lineButtons.setVisible(false);
					nodeButtons.setVisible(false);
				}
				framNodeEditorList.setSelectedNode(framVisualizer.getSelectedNode());
				
			}
		});
		
		this.setSize(900, 600);
		
		Container contentPane = getContentPane();
		
		
		contentPane.setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();	
		JTextField searchField = new JTextField(20);
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				JTextField textfield = (JTextField)e.getSource();
				String search = textfield.getText();
				framNodeEditorList.getList().setVisibilityFilter(search);

				validate();
				repaint();

			}
		});
		
		tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
				
		menuBar.add(createFileMenu());
		menuBar.add(createHelpMenu());
		setJMenuBar(menuBar);

		//The graphnodes
		framVisualizer.setList(framNodeEditorList.getList());
		
		tableContainer.add(framNodeEditorList);
				
		tableAndGraph = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
		tableAndGraph.setDividerLocation(450);
		tableAndGraph.setLeftComponent(new JScrollPane(tableContainer));
		
		Icon hide_icon = new ImageIcon(getClass().getResource("/icons/hide.GIF"));
		Icon show_icon = new ImageIcon(getClass().getResource("/icons/eye.GIF"));
		if ((framVisualizer.getSelectedLine() != null) && (framVisualizer.getSelectedLine().getVisibility())){
			toggleHideLine = new JButton(hide_icon);
			toggleHideLine.setText("hide line");
		} else {
			toggleHideLine = new JButton(show_icon);
			toggleHideLine.setText("show line");
		}
		toggleHideLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framVisualizer.getSelectedLine().setVisibility(!framVisualizer.getSelectedLine().getVisibility());
				framVisualizer.repaint();
			}
			
		});
		
		
		JButton redLine;
		Icon red_icon = new ImageIcon(getClass().getResource("/icons/color_red.GIF"));
		redLine = new JButton(red_icon);
		redLine.setSize(10,10);
		redLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framVisualizer.getSelectedLine().setLineColor(Color.RED);
				framVisualizer.repaint();
			}
		});
		JButton blackLine;
		Icon black_icon = new ImageIcon(getClass().getResource("/icons/color_black.GIF"));
		blackLine = new JButton(black_icon);
		blackLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framVisualizer.getSelectedLine().setLineColor(Color.BLACK);
				framVisualizer.repaint();
			}
		});
		JButton greenLine;
		Icon green_icon = new ImageIcon(getClass().getResource("/icons/color_green.GIF"));
		greenLine = new JButton(green_icon);
		greenLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framVisualizer.getSelectedLine().setLineColor(Color.GREEN);
				framVisualizer.repaint();
			}
		});
		JButton unlockLineButton;
		Icon unlock_icon = new ImageIcon(getClass().getResource("/icons/unlock.GIF"));
		unlockLineButton = new JButton(unlock_icon);
		unlockLineButton.setText("unlock");
		unlockLineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framVisualizer.getSelectedLine().setMoved(false);
				framVisualizer.repaint();
			}
		
		});
		JButton biggerNodeButton;
		Icon bigger_icon = new ImageIcon(getClass().getResource("/icons/bigger.GIF"));
		biggerNodeButton = new JButton(bigger_icon); 
		biggerNodeButton.setText("Bigger");
		biggerNodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framVisualizer.getSelectedNode().setSize(framVisualizer.getSelectedNode().getSize()+5);
				framVisualizer.repaint();
			}
		
		});
		lineButtons.add(unlockLineButton);
		lineButtons.add(toggleHideLine);
		lineButtons.add(blackLine);
		lineButtons.add(greenLine);
		lineButtons.add(redLine);
		lineButtons.setVisible(false);
		
// Fix JPanel for nodes.. aint workin
		nodeButtons.add(biggerNodeButton);
		nodeButtons.setVisible(false);
		JPanel CPCandLineButtons = new JPanel();
		
		CPCandLineButtons.setLayout(new BorderLayout());
		CPCandLineButtons.add(new JScrollPane(framCPCTable), BorderLayout.CENTER);
		CPCandLineButtons.add(nodeButtons, BorderLayout.SOUTH);
		CPCandLineButtons.add(lineButtons, BorderLayout.SOUTH);
		
		//Split 2 = graph and CPC
		split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split2.setDividerLocation(232);
		split2.setTopComponent(CPCandLineButtons);
		split2.setBottomComponent(framVisualizer);
		
		tableAndGraph.setRightComponent(split2);
		
		
		//tableAndGraph.setRightComponent(framVisualizer);		
		
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		
		buttonsPanel.add(searchField);
		buttonsPanel.add(createNewNodeButton());
		buttonsPanel.add(createDeleteButton());
		buttonsPanel.add(createShowLabelsButton());
		buttonsPanel.add(createShowHiddenLinesButton());
		
		contentPane.add(buttonsPanel, BorderLayout.PAGE_START);	
		contentPane.add(tableAndGraph, BorderLayout.CENTER);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void deleteSelectedNode() {
		framNodeEditorList.getList().remove(framVisualizer.getSelectedNode());
	}
	

	private JButton createDeleteButton() {
		JButton buttonDelete = new JButton();
		buttonDelete.setText("Delete node");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelectedNode();
			}
		});
		
		return buttonDelete;
	}
	
	private JButton createNewNodeButton(){
        newNode = new JButton();
        newNode.setText("New node");
        newNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				framNodeEditorList.add(new FramNode());
			}
        });
        return newNode;
	}
	private JButton createShowLabelsButton() {
		JButton buttonShowLabels = new JButton();
		buttonShowLabels.setText("Toggle all labels");
		buttonShowLabels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(GraphLine line : framVisualizer.getGuiLineList()){
					if(showAllLabels){
						line.setShowBubbles(true);
						showAllLabels = false;
					}
					else{
						line.setShowBubbles(false);
						showAllLabels = true;
					}
				}
				repaint();
			}
			
		});
		return buttonShowLabels;
	}
	private JButton createShowHiddenLinesButton() {
		JButton buttonShowHiddenLines = new JButton();
		buttonShowHiddenLines.setText("Toggle Hidden Lines");
		buttonShowHiddenLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ConnectionInfo.isShowAll()){
					ConnectionInfo.setShowAll(false);
				}			
				else{
					ConnectionInfo.setShowAll(true);
				}
				repaint();
			}
			
		});
		return buttonShowHiddenLines;
	}
//	private JButton createToggleSingleLabelButton() {
//		JButton buttonToggleSingleLabel = new JButton();
//		buttonToggleSingleLabel.setText("Toggle single label");
//		buttonToggleSingleLabel.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				ConnectionInfo cInfo = framVisualizer.getSelectedLine();
//				
//				cInfo.getGraphLine().setShowBubbles(false); 
//				
//				repaint();
//			}
//		});
//		
//		return buttonToggleSingleLabel;
//	}
	
	
	 /**
     * Captures the thrown actions from the menu
     *      
     */
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand()== "New"){
    		FramNodeList newList = new FramNodeList("");
    		framNodeEditorList.setList(newList);
    		framVisualizer.setList(newList);
    	}
    	else if(e.getActionCommand()== "Save"){
    		fc.setFileFilter(framFilter);
    		fc.setAcceptAllFileFilterUsed(false);
    		
    		int returnVal = fc.showSaveDialog(this);
    		if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            framNodeEditorList.getList().SaveFile(file.getPath());
	        }	
    	}else if(e.getActionCommand()== "Export to XML"){
    		fc.setFileFilter(xmlFilter);
    		fc.setAcceptAllFileFilterUsed(false);
    		
    		int returnVal = fc.showSaveDialog(this);
    		if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            framNodeEditorList.getList().SaveXMLFile(file.getPath());
	        }	
    	}else if(e.getActionCommand()=="Load"){
    		fc.setFileFilter(framFilter);
    		fc.setAcceptAllFileFilterUsed(false);
    		
    		int returnVal = fc.showOpenDialog(this);
    		if (returnVal == JFileChooser.APPROVE_OPTION) {
    			File file = fc.getSelectedFile();
    			framNodeEditorList.setList(FramNodeList.LoadFile(file.getPath()));
    			framVisualizer.setList(framNodeEditorList.getList());

    		}
    	}else if(e.getActionCommand()=="About"){
    		JFrame aboutFrame = new JFrame();
    		JPanel panel = new JPanel();
    		aboutFrame.add(panel);
    		JLabel text = new JLabel("<html><h3>FRAM Visualizer</h3>" +
    				"<p>FRAM Visualizer is an open-source tool to help model <br>system with FRAM (Functional Resonance Accident Model). </p>" +
    				"<p><br>First developed at Linköping University, Sweden by:</p><p>Peppe Bergqvist<br>Fredrik Gustafsson<br>Jonas Haraldsson<br>Gustav Ladén<br></p>" +
    				"<p><br>FRAM Visualizer is licensed under GNU General Public License (GPL)</p>"+
    				"<p>https://sourceforge.net/projects/framvisualizer/</p>" +
    				"<p><br>Copyright (C) 2007  Peppe Bergqvist peppe@peppesbodega.nu, <br>" +
    				"Fredrik Gustafsson fregu808@student.liu.se,<br>" +
    				"Jonas Haraldsson haraldsson@gmail.com, <br> " +
    				"Gustav Ladén gusla438@student.liu.se</p></html>");

    		panel.add(text);
    		aboutFrame.setSize(150, 200);
    		aboutFrame.pack();
    		aboutFrame.setVisible(true);
    		aboutFrame.setFocusable(true);
    	}
    }
    
    
    /**
     * Creates a filter to get .xml for the filechooser dialog
     *
     */
    class XMLfilter extends javax.swing.filechooser.FileFilter {
        public boolean accept(File f) {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
        }
        public String getDescription() {
            return "XML files (*.xml)";
        }
    }
    /**
     * Creates a filter to get .fram in the filechooser dialog
     *
     */
    class FRAMfilter extends javax.swing.filechooser.FileFilter {
        public boolean accept(File f) {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".fram");
        }
        public String getDescription() {
            return "FRAM Visualizer files (*.fram)";
        }
    }  
    
	/**
	 * Creates the Filemenu
	 * @return The File menu
	 */
	private JMenu createFileMenu(){
		JMenu menu;
		JMenuItem menuItem;				
		
		menu = new JMenu("File");		
		
		menuItem = new JMenuItem("New");
		menuItem.setMnemonic('N');
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Load");
		menuItem.setMnemonic('L');
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Export to XML");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		return menu;
	}
	private JMenu createHelpMenu(){
		JMenu menu;
		JMenuItem menuItem;	
		menu = new JMenu("Help");		
		menuItem = new JMenuItem("About");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		return menu;
	}
	
	public static void main(String[] args) {

		new GUI();
	}
}
