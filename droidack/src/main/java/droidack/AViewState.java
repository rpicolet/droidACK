/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;


/**
 * @author Randy Picolet
 */

public abstract class AViewState<E extends Enum<E>>
			  extends AControl
		   implements IViewState<E> {
	
	private int 
			mIndex = -1;
		
	// IViewState - For use by parent FsmManager only
	@Override
	public void setIndex(int index) {
		mIndex = index;
	}
	@Override
	public int getIndex() {
		return mIndex;
	}

	/**
	 * Convenience logging/validation for onEvent(); 
	 * should be called first
	 * 
	 * @param event - onEvent() argument
	 * @return		- true if the event should be handled; 
	 * 				  child must still validate enum type
	 */
	protected final boolean preValidateEvent(E event) {
		if (DEBUG) 
			logD("onEvent() " + getEventDesc(event));
		if (!isResumed()) {
			if (DEBUG)
				logD("onEvent() not resumed, ignoring: " 
						+ getEventDesc(event));
			return false;
		} 
		if (event == null) {
			if (DEBUG)
				logAndThrowNullError("validateEvent(): event");
			return false;
		}
		return true;
	}
	/**
	 * Logging support utility
	 * @param event - Event enum value
	 * @return - String descriptor of event
	 */
	protected final String getEventDesc(E event) {
    	if (event != null) 
    		return "Event: " + event.toString();
		return "Event: null!";
    }
	/**
	 * Utility error handler for onEvent() switch default
	 * 
	 * NOTE: no distinction is made between "can't happen"
	 * and "shouldn't happen" event occurrences, but 
	 * presumably "can't happen" events won't...  On the
	 * other hand, legal but "ignored" events should be 
	 * explicitly handled by a switch case, not defaulted.
	 *  
	 * @param event - the illegal Event that occurred
	 */
	protected final void logAndThrowIllegalEventError(E event) {
		if (DEBUG)
			logAndThrowError("Illegal event received: "
								+ getEventDesc(event));
	}
}