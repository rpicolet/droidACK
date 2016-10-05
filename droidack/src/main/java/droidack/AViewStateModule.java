/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;

import android.os.Bundle;

/**
 * Extends AControlModule to provide a finite state machine for a simple
 * tree of ViewStates and an enumerated set of events; propagates
 * "active"/"running" mode life-cycle calls only to the current ViewState
 *
 * @author Randy Picolet
 */
public abstract class AViewStateModule<E extends Enum<E>>
			  extends AControlModule<IViewState<E>>
		   implements IViewStateModule<E> {

	private static final String
			STATE_KEY_STUB = Class.class.getName()  + ".VIEW_STATE";
	private String
			// Bundle key for saved FsmState
			mStateBundleKey = null;
	private IViewState<E>
			// Default enter FsmState (first one added)
			mDefaultStartState = null;
	private IViewState<E>
			// Actual enter FsmState
			mStartState = null;
	private IViewState<E>
			// Current ViewState
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
		IViewState<E> startState = getStartState();
		onStart(startState);
		mCurrentState = startState; // Current State now defined...
    }

    /**
	 * Call first if overridden
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (DEBUG) {
            ASSERT_NON_NULL(mCurrentState, "mCurrentState");
        }
		mCurrentState.onResume();
    }

    /**
	 * Call first if overridden
	 */
	@Override
    public void onPause() {
		super.onPause();
		if (DEBUG) {
            ASSERT_NON_NULL(mCurrentState, "mCurrentState");
        }
		mCurrentState.onPause();
	}

    /**
	 * Call first if overridden
	 */
	@Override
    public void onStop() {
		super.onStop();
		if (DEBUG) {
            ASSERT_NON_NULL(mCurrentState, "mCurrentState");
        }
		IViewState<E> stopState = mCurrentState;
		mCurrentState = null; // Current state now undefined...
		stopState.onStop();
		// Start here if activity re-started (i.e. without being re-created)
		setStartState(stopState);
	}

    /**
	 * Call first if overridden
	 */
	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		super.onSaveInstanceState(outBundle);
		if (DEBUG) {
            ASSERT_NON_NULL(mCurrentState, "mCurrentState");
        }
		// Delegate the save to the State
		mCurrentState.onSaveInstanceState(outBundle);
		// Save which State is current, qualify key stub with view id...
		mStateBundleKey = STATE_KEY_STUB + Integer.toString(getView().getId());
		outBundle.putInt(mStateBundleKey, mCurrentState.getIndex());
	}

	//	**************   C O N T R O L   S T R U C T U R E   ***************  //

	@Override
	public final void addChild(IViewState<E> state) {
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
	public final void setStartState(IViewState<E> startState) {
		if (DEBUG) {
			ASSERT_NON_NULL(startState, "enter");
			ASSERT(isValidStateIndex(startState.getIndex()),
					"enter not added!");
		}
		mStartState = startState;
	}

	/**
     * Get the current active ViewState
     *
     * @return - current ViewState instance
     */
	@Override
	public final IViewState<E> getCurrentState() {
		return mCurrentState;
	}

    @Override
	public void onEvent(E event) {
		if (DEBUG) {
			ENTER("onEvent");
		}
        if (isResumed()) {
            if (DEBUG) {
                ASSERT_NON_NULL(mCurrentState, "mCurrentState");
            }
            // Defensive check here...
            if (mCurrentState != null) {
                // Remaining validation is ViewState's responsibility...
                mCurrentState.onEvent(event);
            }
        } else {
			if (DEBUG) {
				logW("onEvent(): not resumed, ignoring event:"
						+ getEventDesc(event));
			}
		}
		if (DEBUG) {
            EXIT();
        }
	}

    /**
     * Effect a transition from the current ViewState to a new ViewState
     * Should only be called from a ViewStateModule onEvent() method
     *
     * @param newState - FsmState to change to (may be current FsmState)
     */
	@Override
    public final void changeState(IViewState<E> newState) {
		if (DEBUG) {
			ENTER("changeState");
	    	ASSERT_NON_NULL(newState, "newState");
	    	ASSERT_NON_NULL(mCurrentState, "mCurrentState");
	    	ASSERT_NON_NULL(getChild(newState.getIndex()), "child state");
	    	ASSERT(isResumed(), "not resumed!");
	    	logD("currentState: " + mCurrentState.getAckTag()
                    + " newState: " + newState.getAckTag());
		}
		// Pause & undefine current State
		mCurrentState.onPause();
		IViewState<E> oldState = mCurrentState;
		mCurrentState = null;
		// Do actual transition
		transition(oldState, newState);
		// Define & run new current State
		mCurrentState = newState;
		mCurrentState.onResume();
		if (DEBUG) {
            EXIT();
        }
	}

	/**
     * Get the starting ViewState as previously set
     *
     * @return - starting ViewState instance
     */
	protected final IViewState<E> getStartState() {
		if (DEBUG) {
            ASSERT_NON_NULL(mStartState, "getStartState(): mStartState!");
        }
		return mStartState;
	}

    /**
	 * Override if using transactions
     *
     * @param state - the State to enter
     */
	protected void onStart(IViewState<E> state) {
		state.onStart();
	}

    /**
	 * Override if using transactions

     * @param oldState - the State to stop
     * @param newState - the State to enter
     */
	protected void transition(IViewState<E> oldState,
    						  IViewState<E> newState) {
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

    private void setDefaultStartState(IViewState<E> addedState) {
		// First state added is default enter state
		if (mDefaultStartState == null) {
            mDefaultStartState = addedState;
        }
	}

    private void setStartState(Bundle inBundle) {
		IViewState<E> restartState = null;
		// Determine if there is a restart State to establish
		if (inBundle != null) {
			// Get the saved (then-current) State
			int savedStateIndex = inBundle.getInt(mStateBundleKey);
			if (savedStateIndex >= 0 &&
					savedStateIndex < getChildren().size()) {
				restartState = getChild(savedStateIndex);
			} else {
                // Uses default state if not DEBUG...
                if (DEBUG) {
                    ASSERT(false, "savedStateIndex out of bounds!");
                }
            }
		} else if (DEBUG) {
            logD("setStartState(bundle): null bundle...");
        }
		setStartState(restartState != null ? restartState : mDefaultStartState);
	}

	private boolean isValidStateIndex(int stateIndex) {
		return stateIndex >= 0 && stateIndex <= getChildren().size();
	}
}
