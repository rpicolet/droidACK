//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.gduck;

/**
 * 
 * @author Randy Picolet
 */

public interface IFragmentControlModule 
	     extends IRootControlModule {

	public void onCreate(IControlFragment fragment);
}