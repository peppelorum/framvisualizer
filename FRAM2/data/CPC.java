/**

 	A visualizer for FRAM (Functional Resonance Accident Model).
 	This tool helps modelling the the FRAM table and visualize it.
	Copyright (C) 2007  Peppe Bergqvist <peppe@peppesbodega.nu>, Fredrik Gustafsson <fregu808@student.liu.se>,
	Jonas Haraldsson <haraldsson@gmail.com>, Gustav Lad�n <gusla438@student.liu.se>
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
package data;

import java.io.Serializable;
import java.util.ArrayList;

public class CPC implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9034461918811470582L;
	private FramNode parent;
	
	public static boolean typeExists(String type) {
		for(String t : CPC_TYPES) {
			if(t.equals(type)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static final String[] CPC_TYPES = new String[]{
		"Available resources",
		"Training experience",
		"Quality communication",
		"HMI and operational support",
		"Access procedures and methods",
		"Conditions of work",
		"Number of goals and conflict resolution",
		"Available time and time preasure",
		"Circadian rytm, stress",
		"Crew collaboration quality",
		"Quality and support of organization"
	};
	
	private ArrayList<CPCAttribute> list;
	
	public CPC(FramNode node) {
		this.parent = node;
		list = new ArrayList<CPCAttribute>();
		
	}
	public void setAttribute(String type, String value, String comment, Boolean[] cpcForAspects){
		if(hasAttribute(type)) {
			CPCAttribute attrib = getAttribute(type);
			attrib.setValue(value);
			attrib.setComment(comment);			
			attrib.setCpcForAspects(cpcForAspects);
		}
		else {
			list.add(new CPCAttribute(type, value, comment, cpcForAspects));
		}
	}
		
	public boolean hasAttribute(String type) {
		for(CPCAttribute attrib : list) {
			if(attrib.getType().equals(type)) {
				return true;
			}
		}
		
		return false;
	}
	
	public CPCAttribute getAttribute(String type) {
		for(CPCAttribute attrib : list) {
			if(attrib.getType().equals(type)) {
				return attrib;
			}
		}
		
		return null;
	}
	
	public FramNode getParent() {
		return parent;
	}

}
