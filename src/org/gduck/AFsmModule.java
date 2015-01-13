//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.gduck;

import android.os.Bundle;

public abstract class AFsmModule<E extends Enum<E>> 
			  extends ACompositeControl<IFsmState<E>>
		   implements IFsmModule<E> {

	private static final String 
			STATE_KEY_STUB = Class.class.getName()  + ".FSM_STATE";
	private String 
			// Bundle key for saved FsmState
			mStateBundleKey = null;
	private IFsmState<E> 
			// Default start FsmState (first one added)
			mDefaultStartState = null;
	private IFsmState<E> 
			// Actual start FsmState
			mStartState = null;
	private IFsmState<E> 
			// Current FsmState
			mCurrentState = null;
	
	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //

	@Override
	public void onActivityCreated(Bundle inBundle) {
		super.onActivityCreated(inBundle);
		setStartState(inBundle);
	}
	/**
	 * Call first if overridden
	 */
	@Override
	public void onStart() {
		super.onStart();
		onStart(mStartState);
		mCurrentState = mStartState; // Current State now defined...
    }
	/**
	 * Call first if overridden
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (DEBUG) 
			ASSERT_NON_NULL(mCurrentState, "mCurrentState");
		mCurrentState.onResume();
    }
	/**
	 * Call first if overridden
	 */
	@Override
    public void onPause() {
		super.onPause();
		if (DEBUG) 
			ASSERT_NON_NULL(mCurrentState, "mCurrentState");
		mCurrentState.onPause();
	}
	/**
	 * Call first if overridden
	 */
	@Override
    public void onStop() {
		super.onStop();
		if (DEBUG) 
			ASSERT_NON_NULL(mCurrentState, "mCurrentState");
		IFsmState<E> stopState = mCurrentState;
		mCurrentState = null; // Current state now undefined...
		stopState.onStop();
	}
	/**
	 * Call first if overridden
	 */
	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		super.onSaveInstanceState(outBundle);
		if (DEBUG) 
			ASSERT_NON_NULL(mCurrentState, "mCurrentState");
		// Delegate the save to the State
		mCurrentState.onSaveInstanceState(outBundle);
		// Save which State is current, qualify key stub with view id...
		mStateBundleKey = STATE_KEY_STUB + Integer.toString(getView().getId());
		outBundle.putInt(mStateBundleKey, mCurrentState.getIndex());
	}

	//	**************   C O N T R O L   S T R U C T U R E   ***************  //
	
	@Override
	public final void addChild(IFsmState<E> state) {
		super.addChild(state);
		state.setIndex(getChildren().indexOf(state));
		setDefaultStartState(state);
	}

	//	****************   V I E W   M A N A G E M E N T   *****************  //
	
	/**
	 * Specify a non-default starting State for the StateMachine,
	 * to restart in a previously paused State; may be called 
	 * from child class prior to onStart()
	 * 
	 * @param startState -the "restart" ViewState to enter onStart() 
	 */
	@Override
	public final void setStartState(IFsmState<E> startState) {
		if (DEBUG) { 
			ASSERT_NON_NULL(startState, "startState"); 
			ASSERT(isValidStateIndex(startState.getIndex()), 
					"startState not added!");
		}
		mStartState = startState;
	}
	/**
     * Get the current active ViewState
     * 
     * @return - current ViewState instance
     */
	@Override
	public final IFsmState<E> getCurrentState() {
		return mCurrentState;
	}
	@Override
	public void onEvent(E event) {
		if (DEBUG) ENTER_MSG("onEvent");
		if (mCurrentState == null) {
			if (DEBUG) {
				ASSERT_NON_NULL(null, "mCurrentState");
				EXIT();
			}
			return;
		}
		if (!isResumed()) {
			if (DEBUG) {
				logW("onEvent(): not resumed, ignoring event:" 
						+ getEventDesc(event));
				EXIT();
			}
			return;
		}
		// Remaining validation is ViewState's responsibility...
		mCurrentState.onEvent(event);
		if (DEBUG) EXIT();
	}
    /**
     * Effect a transition from the current FsmState to a new FsmState
     * Should only be called from a FsmManager onEvent() method
     * 
     * @param newState - FsmState to change to (may be current FsmState)
     */
	@Override
    public final void changeState(IFsmState<E> newState) {
		if (DEBUG) {
			ENTER("changeState");
	    	ASSERT_NON_NULL(newState, "newState");
	    	ASSERT_NON_NULL(mCurrentState, "mCurrentState");
	    	ASSERT_NON_NULL(getChild(newState.getIndex()), "child state");
	    	ASSERT(isResumed(), "not resumed!");
	    	logD("currentState: " + mCurrentState.getMvcTag() 
				     + " newState: " + newState.getMvcTag());
		}
		// Pause & undefine current State 
		mCurrentState.onPause(); 
		IFsmState<E> oldState = mCurrentState;
		mCurrentState = null;
		// Do actual transition
		transition(oldState, newState);
		// Define & run new current State
		mCurrentState = newState;
		mCurrentState.onResume();
		if (DEBUG) EXIT();
	}
	/**
     * Get the starting ViewState as previously set
     * 
     * @return - starting ViewState instance
     */
	protected final IFsmState<E> getStartState() {
		if (DEBUG)
			ASSERT_NON_NULL(mStartState, "getStartState(): mStartState!");
		return mStartState;
	}
	/**
	 * Override if using transactions 
     * 
     * @param state - the State to start
     */
	protected void onStart(IFsmState<E> state) {
		state.onStart();
	}
    /**
	 * Override if using transactions 

     * @param oldState - the State to stop
     * @param newState - the State to start
     */
	protected void transition(IFsmState<E> oldState, 
    						  IFsmState<E> newState) {
		oldState.onStop();
		newState.onStart();
	}
	/**
	 * Logging support utility
	 * @param event - Event enum value
	 * @return - String descriptor of event
	 */
	protected final String getEventDesc(E event) {
    	return "Event: " + event.toString();
    }
	private void setDefaultStartState(IFsmState<E> addedState) {
		// First state added is default start state
		if (mDefaultStartState == null)
			mDefaultStartState = addedState;
	}
	private void setStartState(Bundle inBundle) {
		IFsmState<E> restartState = null;
		// Determine if there is a restart State to establish
		if (inBundle != null) {
			// Get the saved (then-current) State
			int savedStateIndex = inBundle.getInt(mStateBundleKey);
			if (savedStateIndex >= 0 && 
					savedStateIndex < getChildren().size()) {
				restartState = getChild(savedStateIndex);
			} else
				// Use default state if not DEBUG
				if (DEBUG)
					ASSERT(false, "savedStateIndex out of bounds!");
		} else 
			if (DEBUG)
				logD("setStartState(bundle): null bundle...");
		setStartState(restartState != null ? restartState : mDefaultStartState);
	}
	private boolean isValidStateIndex(int stateIndex) {
		return stateIndex >= 0 && stateIndex <= getChildren().size();
	}
	
}