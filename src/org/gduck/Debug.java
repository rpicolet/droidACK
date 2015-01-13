package rpicolet.mvc;

/*
 * MVC Debug Configuration
 * 
 * 		Global enable/disable for MVC Debug mechanisms
 */
public class Debug {
	private static boolean sEnabled = false;
	
	// Call from main activity/app component static{} block 
	public final static void setEnabled(boolean enabled) {
		sEnabled = enabled;
	}
	
	public final static boolean getEnabled() {
		return sEnabled;
	}
}
