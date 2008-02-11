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


import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.text.*;

/**
 * A combox that uses autocomplete to suggests what is in the combobox
 * @author petbe082
 *
 */
public class ComboBoxAutoComplete extends JComboBox	implements JComboBox.KeySelectionManager
{
	private static final long serialVersionUID = 3571587786729616007L;

	int lastPressedKey = -1;
	public int getLastPressedKey() {
		return this.lastPressedKey;
	}
	public void setLastPressedKey(int val) {
		this.lastPressedKey = val;
	}

	public class CBDocument extends PlainDocument
	{
		private static final long serialVersionUID = 8367276909010234390L;

		public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
		{
			if (str==null) return;
			super.insertString(offset, str, a);
			if(!isPopupVisible() && str.length() != 0) fireActionEvent();
		}
	}
	public ComboBoxAutoComplete(Object[] items)
	{
		super(items);

		setKeySelectionManager(this);
		JTextField tf;
		if(getEditor() != null)
		{
			tf = (JTextField)getEditor().getEditorComponent();
			if(tf != null)
			{
				tf.setDocument(new CBDocument());
				tf.addKeyListener(new KeyListener() {

					public void keyPressed(KeyEvent e) {
						int[] keys = new int[] {
								KeyEvent.VK_ENTER,
								KeyEvent.VK_LEFT,
								KeyEvent.VK_RIGHT,
								KeyEvent.VK_UP,
								KeyEvent.VK_DOWN
						};
						for(int key : keys) {
							if(key == e.getKeyCode()) {
								return;
							}
						}
						setLastPressedKey(e.getKeyCode());
					}

					public void keyReleased(KeyEvent e) {

					}

					public void keyTyped(KeyEvent e) {

					}


				});
				
				
//				addItemListener(new ItemListener() {
//
//					public void itemStateChanged(ItemEvent e) {
//						// TODO Auto-generated method stub
//		                JComboBox comboBox = (JComboBox)e.getSource();
//		                String item = (String)comboBox.getSelectedItem();
//		                System.out.println( item + "- ");
//					}
//					
//				});				

				addActionListener(new ActionListener()
				{
//				    public void contentsChanged(ListDataEvent e)
//				    {
//				    	
//		                JComboBox comboBox = (JComboBox)e.getSource();
//		                String item = (String)comboBox.getSelectedItem();
//		                System.out.println( item + "- ");
////				        if (_alwaysFireOnSelect)
////				        {
////				            selectedItemReminder = null;
////				        }
//
////				        super.contentsChanged(e);
//				    }
					
					public void actionPerformed(ActionEvent evt)
					{
						
		                JComboBox comboBox = (JComboBox)evt.getSource();
		                String item = (String)comboBox.getSelectedItem();
//		                System.out.println( item + " : ");

						// Don't select from list if last action was to delete
						if(getLastPressedKey() == KeyEvent.VK_BACK_SPACE
								|| getLastPressedKey() == KeyEvent.VK_DELETE) {
							return;
						}

						JTextField tf = (JTextField)getEditor().getEditorComponent();
						String text = tf.getText();
						String orgText = text;

						ComboBoxModel aModel = getModel();
						String current;
						int j;
						for(j = 0; j < aModel.getSize(); j++) {
							current = aModel.getElementAt(j).toString();
							if(current.toLowerCase().startsWith(text.toLowerCase())){
								text = current;
								break;

							}
						}
						tf.setText(text);

						if(orgText.length() != text.length()) {
							tf.setSelectionStart(orgText.length());
							tf.setSelectionEnd(text.length());
							tf.setCaretColor(tf.getSelectionColor());
//							A bit nasty but it seems to get the popup validated properly
							setSelectedItem(text);
							setPopupVisible(false);
							setPopupVisible(true);
						}
						else {
							tf.setCaretColor(Color.BLACK);
						}

					}
				});
			}
		}
	}
	public int selectionForKey(char aKey, ComboBoxModel aModel)
	{
//		System.out.println("grej: "+ aKey);
		return -1;
	}
	public void fireActionEvent()
	{

		super.fireActionEvent();
	}

}


