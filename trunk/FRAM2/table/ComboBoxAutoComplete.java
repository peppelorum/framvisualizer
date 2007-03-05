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


import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * A combox that uses autocomplete to suggests what is in the combobox
 * @author petbe082
 *
 */
public class ComboBoxAutoComplete extends JComboBox	implements JComboBox.KeySelectionManager
{
	private static final long serialVersionUID = 3571587786729616007L;
	
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
				
				addActionListener(new ActionListener()
						{
					public void actionPerformed(ActionEvent evt)
					{
						JTextField tf = (JTextField)getEditor().getEditorComponent();
						String text = tf.getText();
						String orgText = text;
						
						ComboBoxModel aModel = getModel();
						String current;
						for(int j = 0; j < aModel.getSize(); j++) {
							current = aModel.getElementAt(j).toString();
							if(current.toLowerCase().startsWith(text.toLowerCase())){
								text = current;									
								break;
								
							}
						}
						tf.setText(text);		
						
						tf.setSelectionStart(orgText.length());
						tf.setSelectionEnd(text.length());
						
					}
						});
			}
		}
	}
	public int selectionForKey(char aKey, ComboBoxModel aModel)
	{
		return -1;
	}
	public void fireActionEvent()
	{
		super.fireActionEvent();
	}
}


