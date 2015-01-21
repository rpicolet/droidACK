/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

import android.util.Log;

/**
 * Base class for MVC components
 * 
 * @author Randy Picolet
 */

public abstract class AComponent implements IComponent {

	//	*****************   I D E N T I F I C A T I O N   ******************  //

	public final static boolean
			// Primary MVC debug flag
			DEBUG 			= Debug.getEnabled(),
			// Method tracing support
			TRACE_METHODS 	= true;

	private static final String
			// Empty string flag
			EMPTY = "",
			// Long class name, sometimes useful for logging
			ACK_CLASS = Class.class.getName();

	private static int 
			// Component instance/id counter
			sIdCounter = 0;
	
	private final int 
			// Process-unique Component ID
			mAckId = sIdCounter++;
	private String 
			// Class Logging tag, start out with short name 
			mAckTag = this.getClass().getSimpleName(),
			// For logging and method tracing...
			mMethodName = EMPTY;


	@Override
	public final String getAckClass() {
		return ACK_CLASS;
	}
	@Override
	public final int getAckId() {
		return mAckId;
	}
	@Override
	public final String getAckTag() {
		return mAckTag;
	}
	
	// Child tag customization
	protected final String setShortAckTag() {
		return setAckTag(this.getClass().getSimpleName());
	}
	protected final String setLongAckTag() {
		return setAckTag(ACK_CLASS);
	}
	protected final String setUniqueAckTag() {
		return setAckTag(this.getClass().getSimpleName() + 
				"." + Integer.toString(mAckId));
	}
	protected final String setAckTag(String mvcTag) {
		mAckTag = mvcTag;
		return mAckTag;
	}
	
	//	*******   L O G G I N G   &   E R R O R   H A N D L I N G   ********  //

	public final void ENTER(String methodName) {
		if (DEBUG) {
			String nonNullMethodName = methodName;
			if (methodName == null) {
				logI("ENTER: null methodName...");
				nonNullMethodName = EMPTY;
			}
			mMethodName = nonNullMethodName + "(): ";
		}
	}
	public final void ENTER_MSG(String methodName) {
		if (DEBUG) {
			ENTER(methodName);
			if (TRACE_METHODS) logV("entry...");
		}
	}
	public final void EXIT() {
		if (DEBUG)
			mMethodName = EMPTY;
	}
	public final void EXIT_MSG() {
		if (DEBUG) {
			if (TRACE_METHODS) logV("exit...");
			EXIT();
		}
	}
	
	// Assertion support
	public final void ASSERT(boolean condition, String errMsg) {
		if (DEBUG)
			if (!condition) 
				logAndThrowError(errMsg);
	}
	public final void ASSERT_NULL(Object instance, String name) {
		if (DEBUG)
			if (instance != null) 
				logAndThrowError(name + ": should be null!");
	} 
	public final void ASSERT_NON_NULL(Object instance, String name) {
		if (DEBUG)
			if (instance == null) 
				logAndThrowNullError(name);
	} 

	// Error handlers
	public final void logAndThrowNullError(String errorContext) {
		logAndThrowError(errorContext + ": null reference!");
	}
	public final void logAndThrowError(String errMsg) {
		logE(errMsg);
		throw new Error(mAckTag + "." + mMethodName + errMsg);
	}
	
	// Android Log wrappers
	public final void logE(String message) {
		Log.e(mAckTag, mMethodName + message);
	}
	public final void logW(String message) {
		Log.w(mAckTag, mMethodName + message);
	}
	public final void logI(String message) {
		Log.i(mAckTag, mMethodName + message);
	}
	public final void logD(String message) {
		Log.d(mAckTag, mMethodName + message);
	}
	public final void logV(String message) {
		Log.v(mAckTag, mMethodName + message);
	}
}
