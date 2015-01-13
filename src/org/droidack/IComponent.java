//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.droidack;

public interface IComponent {
	/**
	 * Get the fully-qualified component Class name, 
	 * 
	 * @return - Component Class name
	 */
	public String getMvcClass();
	/**
	 * Get the unique (runtime) instanceID, suitable for functional 
	 * mappings within a process; not useful _across_ processes/app versions!  
	 * 
	 * @return - Component ID
	 */
	public int getMvcId();
	/**
	 * Get the logging tag for the Component
	 * 
	 * @return - Component tag 
	 */
	public String getMvcTag();	
}
