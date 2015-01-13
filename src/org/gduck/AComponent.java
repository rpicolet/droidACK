package rpicolet.mvc;

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
			MVC_CLASS = Class.class.getName();

	private static int 
			// Component instance/id counter
			sIdCounter = 0;
	
	private final int 
			// Process-unique Component ID
			mMvcId = sIdCounter++;
	private String 
			// Class Logging tag, start out with short name 
			mMvcTag = this.getClass().getSimpleName(),
			// For logging and method tracing...
			mMethodName = EMPTY;


	@Override
	public final String getMvcClass() {
		return MVC_CLASS;
	}
	@Override
	public final int getMvcId() {
		return mMvcId;
	}
	@Override
	public final String getMvcTag() {
		return mMvcTag;
	}
	
	// Child tag customization
	protected final String setShortMvcTag() {
		return setMvcTag(this.getClass().getSimpleName());
	}
	protected final String setLongMvcTag() {
		return setMvcTag(MVC_CLASS);
	}
	protected final String setUniqueMvcTag() {
		return setMvcTag(this.getClass().getSimpleName() + 
				"." + Integer.toString(mMvcId));
	}
	protected final String setMvcTag(String mvcTag) {
		mMvcTag = mvcTag;
		return mMvcTag;
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
		throw new Error(mMvcTag + "." + mMethodName + errMsg);
	}
	
	// Android Log wrappers
	public final void logE(String message) {
		Log.e(mMvcTag, mMethodName + message);
	}
	public final void logW(String message) {
		Log.w(mMvcTag, mMethodName + message);
	}
	public final void logI(String message) {
		Log.i(mMvcTag, mMethodName + message);
	}
	public final void logD(String message) {
		Log.d(mMvcTag, mMethodName + message);
	}
	public final void logV(String message) {
		Log.v(mMvcTag, mMethodName + message);
	}
}
