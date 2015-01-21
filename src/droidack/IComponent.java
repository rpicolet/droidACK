/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

public interface IComponent {
	/**
	 * Get the fully-qualified component Class name, 
	 * 
	 * @return - Component Class name
	 */
	public String getAckClass();
	/**
	 * Get the unique (runtime) instanceID, suitable for functional 
	 * mappings within a process; not useful _across_ processes/app versions!  
	 * 
	 * @return - Component ID
	 */
	public int getAckId();
	/**
	 * Get the logging tag for the Component
	 * 
	 * @return - Component tag 
	 */
	public String getAckTag();	
}
