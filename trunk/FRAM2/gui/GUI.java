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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BorderFactory;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import table.FramCPCTable;
import table.FramNodeEditorList;

import data.ConnectionInfo;
import data.FramFunction;
import data.FramFunctionList;

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
    private JToggleButton toggleFlagNode;
    
	private final Icon ICON_HIDE = new ImageIcon(getClass().getResource("/icons/hide.GIF"));
	private final Icon ICON_SHOW = new ImageIcon(getClass().getResource("/icons/eye.GIF"));
    
    private boolean showAllLabels = true;
    
    
    int key;
    JTextField searchField;
    
	public GUI(){
		/*
		 * Sensitive programmers be advised.
		 * 
		 * The code in this constructor is not tidy...
		 * 
		 *  
		 * 
		 * 
		 * */
		
		this.setSize(900, 600);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		
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
					updateFlagNodeButton();
				}
				else if(visualizer.getSelectedLine() != null) {
					lineButtons.setVisible(true);
					nodeButtons.setVisible(false);
					
					updateHideLineButton();
				}
				else {
					lineButtons.setVisible(false);
					nodeButtons.setVisible(false);
				}
				framNodeEditorList.setSelectedNode(framVisualizer.getSelectedNode());
				
			}
		});
				

		
		tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		menuBar.add(createNodeMenu());
		menuBar.add(createHelpMenu());
		setJMenuBar(menuBar);

		//The graphnodes
		framVisualizer.setList(framNodeEditorList.getList());
		
		tableContainer.add(framNodeEditorList);
				
		tableAndGraph = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
		tableAndGraph.setDividerLocation(450);
		tableAndGraph.setLeftComponent(new JScrollPane(tableContainer));
		
		toggleHideLine = new JButton();
		updateHideLineButton();
		toggleHideLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framVisualizer.getSelectedLine().setVisibility(!framVisualizer.getSelectedLine().getVisibility());
				framVisualizer.repaint();
				updateHideLineButton();
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

		lineButtons.add(unlockLineButton);
		lineButtons.add(toggleHideLine);
		lineButtons.add(blackLine);
		lineButtons.add(greenLine);
		lineButtons.add(redLine);
		lineButtons.setVisible(false);
		
// Fix JPanel for nodes.. aint workin
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
		JButton smallerNodeButton;
		Icon smaller_icon = new ImageIcon(getClass().getResource("/icons/smaller.GIF"));
		smallerNodeButton = new JButton(smaller_icon); 
		smallerNodeButton.setText("Smaller");
		smallerNodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framVisualizer.getSelectedNode().setSize(framVisualizer.getSelectedNode().getSize()-5);
				framVisualizer.repaint();
			}
		
		});
		
		toggleFlagNode = new JToggleButton();
		toggleFlagNode.setText("Flag");
		toggleFlagNode.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				framVisualizer.getSelectedNode().setFlagged(!framVisualizer.getSelectedNode().isFlagged());
				updateFlagNodeButton();
				repaint();
			}
			
		});
		
		nodeButtons.add(smallerNodeButton);
		nodeButtons.add(biggerNodeButton);
		nodeButtons.add(toggleFlagNode);
		
		nodeButtons.setVisible(false);

		JPanel generalButtons = new JPanel();
		generalButtons.add(createShowLabelsButton());
		generalButtons.add(createShowHiddenLinesButton());
		JPanel metaGeneralButtons = new JPanel();
		metaGeneralButtons.add(generalButtons);
		
		JPanel nodeAndLineButtons = new JPanel();
		nodeAndLineButtons.add(nodeButtons);
		nodeAndLineButtons.add(lineButtons);
		
		JPanel vButtons = new JPanel();
		vButtons.setLayout(new BorderLayout());
		vButtons.add(metaGeneralButtons, BorderLayout.WEST);
		vButtons.add(nodeAndLineButtons, BorderLayout.EAST);

		JPanel panelVisualizerAndButtons = new JPanel();
		panelVisualizerAndButtons.setLayout(new BorderLayout());
		panelVisualizerAndButtons.add(vButtons, BorderLayout.NORTH);
		panelVisualizerAndButtons.add(framVisualizer, BorderLayout.CENTER);
		
		//Split 2 = graph and CPC
		split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split2.setDividerLocation(200);
		split2.setTopComponent(new JScrollPane(framCPCTable));
		split2.setBottomComponent(panelVisualizerAndButtons);
		
		tableAndGraph.setRightComponent(split2);
		
		
		//tableAndGraph.setRightComponent(framVisualizer);
		
		
		searchField = new JTextField(20);	
		searchField.addActionListener(new SearchFieldTextActionListener());
		searchField.getDocument().addDocumentListener(new SearchFieldDocumentListener());
		searchField.getDocument().putProperty("name", "Text Field");
		
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.LINE_AXIS));
		
		toolbar.add(new JLabel("Search:"));
		toolbar.add(searchField);
		toolbar.add(createNewNodeButton());
		toolbar.add(createDeleteButton());
		
		contentPane.add(toolbar, BorderLayout.PAGE_START);	
		contentPane.add(tableAndGraph, BorderLayout.CENTER);

		this.setTitle("FRAM Visualizer");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
    class SearchFieldDocumentListener implements DocumentListener {
        final String newline = "\n";

        public void insertUpdate(DocumentEvent e) {
//            updateLog(e, "inserted into");
			String search = searchField.getText();
			framNodeEditorList.getList().setVisibilityFilter(search);
        }
        public void removeUpdate(DocumentEvent e) {
//            updateLog(e, "removed from");
			String search = searchField.getText();		
			framNodeEditorList.getList().setVisibilityFilter(search);
        }
        public void changedUpdate(DocumentEvent e) {
            //Plain text components don't fire these events.
        }
        
        /**
         * A debug method for the DocumentListener
         * @param e
         * @param action
         */
        public void updateLog(DocumentEvent e, String action) {
            Document doc = (Document)e.getDocument();
            int changeLength = e.getLength();
            
            System.out.println(
                changeLength + " character"
              + ((changeLength == 1) ? " " : "s ")
              + action + " " + doc.getProperty("name") + "."
              + newline
              + "  Text length = " + doc.getLength() + newline);            
        }
    }
    
    class SearchFieldTextActionListener implements ActionListener {
        /** Handle the text field Return. */
        public void actionPerformed(ActionEvent e) {
            searchField.selectAll();
        }
    }

	
	public void updateHideLineButton() {
		if(framVisualizer.getSelectedLine() != null &&
				framVisualizer.getSelectedLine().getVisibility()) {
			toggleHideLine.setIcon(ICON_HIDE);
			toggleHideLine.setText("Hide line");
		}
		else {
			toggleHideLine.setIcon(ICON_SHOW);
			toggleHideLine.setText("Show line");
		}
	}
	
	public void updateFlagNodeButton() {
		if(framVisualizer.getSelectedNode() != null) {
			toggleFlagNode.setSelected(framVisualizer.getSelectedNode().isFlagged());
		}
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
				framNodeEditorList.add(new FramFunction());
			}
        });
        return newNode;
	}
	private JToggleButton createShowLabelsButton() {
		JToggleButton buttonShowLabels = new JToggleButton();
		buttonShowLabels.setText("Show all labels");
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
	private JToggleButton createShowHiddenLinesButton() {
		JToggleButton buttonShowHiddenLines = new JToggleButton();
		buttonShowHiddenLines.setText("Show hidden lines");
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
    		FramFunctionList newList = new FramFunctionList("");
    		framNodeEditorList.setList(newList);
    		framVisualizer.setList(newList);
    	}
    	else if(e.getActionCommand()== "Save"){
    		fc.removeChoosableFileFilter(xmlFilter);
    		fc.setFileFilter(framFilter);
    		fc.setAcceptAllFileFilterUsed(false);

    		int returnVal = fc.showSaveDialog(this);
    		if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            framNodeEditorList.getList().SaveFile(file.getPath());
	        }	
    	}else if(e.getActionCommand()== "Export to XML"){
    		fc.removeChoosableFileFilter(framFilter);
    		fc.setFileFilter(xmlFilter);
    		fc.setAcceptAllFileFilterUsed(false);
    		
    		int returnVal = fc.showSaveDialog(this);
    		if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            framNodeEditorList.getList().SaveXMLFile(file.getPath());
	        }	
    	}else if(e.getActionCommand()=="Open"){
    		fc.removeChoosableFileFilter(xmlFilter);
    		fc.setFileFilter(framFilter);
    		fc.setAcceptAllFileFilterUsed(false);
    		
    		int returnVal = fc.showOpenDialog(this);
    		if (returnVal == JFileChooser.APPROVE_OPTION) {
    			File file = fc.getSelectedFile();
    			framNodeEditorList.setList(FramFunctionList.LoadFile(file.getPath()));
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
		menu.setMnemonic('F');
		
		menuItem = new JMenuItem("New");
		menuItem.setMnemonic('n');
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Open");
		menuItem.setMnemonic('o');
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Save");
		menuItem.setMnemonic('s');
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		JMenu exportMenu = new JMenu("Export");
		menu.add(exportMenu);
		
		menuItem = new JMenuItem("Export to XML");
		menuItem.addActionListener(this);
		exportMenu.add(menuItem);
		
		menuItem = new JMenuItem("Export to format 1");
		menuItem.addActionListener(this);
		exportMenu.add(menuItem);
		
		menuItem = new JMenuItem("Export to format 2");
		menuItem.addActionListener(this);
		exportMenu.add(menuItem);
		
		menuItem = new JMenuItem("Export to format 3");
		menuItem.addActionListener(this);
		exportMenu.add(menuItem);
		
		return menu;
	}
	private JMenu createNodeMenu(){
		JMenu menu;
		JMenuItem menuItem;				
		
		menu = new JMenu("Node");
		menu.setMnemonic('N');
		
		menuItem = new JMenuItem("New node");
		menuItem.setMnemonic('n');
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Delete node");
		menuItem.setMnemonic('o');
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
