/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

/*
 * ACK Debug Configuration
 * 
 * 		Global enables/disables for ACK Debug mechanisms
 */
public class Debug {
	private static boolean sEnabled = false;
	private static boolean sTraceEnabled = false;

	// Call from main activity/app component static{} block 
	public static void setEnabled(boolean enabled) {
		sEnabled = enabled;
	}
	public static void setTraceEnabled(boolean enabled) {
		sTraceEnabled = enabled;
	}

	public static boolean getEnabled() {
		return sEnabled;
	}
	public static boolean getTraceEnabled() {
		return sTraceEnabled;
	}
}
