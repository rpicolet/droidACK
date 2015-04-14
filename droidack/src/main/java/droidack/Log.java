/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

/**
 * Logging and error handling for static methods
 * NOTE: Use inherited instance methods for Models and Controls
 * 
 * @author Randy Picolet
 */
public class Log {

	//	*******   L O G G I N G   &   E R R O R   H A N D L I N G   ********  //

	public static void e(String tag, String message) {
		android.util.Log.e(tag, message);
	}
	public static void w(String tag, String message) {
		android.util.Log.w(tag, message);
	}
	public static void i(String tag, String message) {
		android.util.Log.i(tag, message);
	}
	public static void d(String tag, String message) {
		android.util.Log.d(tag, message);
	}
	public static void v(String tag, String message) {
		android.util.Log.v(tag, message);
	}
	
	/**
	 * Utility error handler for null reference
	 * 
	 * @param errorContext - method or other context where
	 * 					     the error occurred
	 */
	public static void throwNullError(String tag, String errorContext) {
		throwError(tag, errorContext + ": null reference!");
	}
	/**
	 * General error handler utility
	 * 
	 * @param errMsg - message text to log and throw
	 */
	public static void throwError(String tag, String errMsg) {
		android.util.Log.e(tag, errMsg);
		throw new Error(tag + "." + errMsg);
	}
}
