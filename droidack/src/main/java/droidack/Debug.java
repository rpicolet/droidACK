/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

/*
 * MVC Debug Configuration
 * 
 * 		Global enable/disable for MVC Debug mechanisms
 */
public class Debug {
	private static boolean sEnabled = false;
	
	// Call from main activity/app component static{} block 
	public static void setEnabled(boolean enabled) {
		sEnabled = enabled;
	}
	
	public static boolean getEnabled() {
		return sEnabled;
	}
}
