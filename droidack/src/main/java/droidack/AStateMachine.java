/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;

/**
 * Base class for a generic utility StateMachine that does not particpate
 * in Activity/Fragment lifecycle or Control events
 *
 * Author: Randy Picolet
 */
public class AStateMachine<E extends Enum<E>> extends AComponent
        implements IEventHandler<E> {

    protected String mMethodPrefix = "";

    private AState<E> mCurrentState;

    // IEventHandler
    public void onEvent(E event) {
        if (DEBUG) {
            ENTER(mMethodPrefix + "onEvent");
            //logD(event.name());
        }
        if (mCurrentState == null) {
            if (DEBUG) {
                ASSERT_NON_NULL(null, "mCurrentState");
                EXIT();
            }
            return;
        }
        // Remaining validation is State responsibility...
        mCurrentState.onEvent(event);
        if (DEBUG) {
            EXIT();
        }
    }

    // AState use only
    public void changeState(AState<E> newState) {
        if (DEBUG) {
            ENTER(mMethodPrefix + "changeState");
            ASSERT_NON_NULL(newState, "newState");
            ASSERT_NON_NULL(mCurrentState, "mCurrentState");
			/*
	    	logV("currentState: " + mCurrentState.getAckTag()
				     + " newState: " + newState.getAckTag());
			*/
        }
        // Undefine current State during transition
        AState<E> oldState = mCurrentState;
        mCurrentState = null;
        // Do actual transition
        oldState.onExit();
        newState.onEnter();
        // Define new current State
        mCurrentState = newState;
        if (DEBUG) {
            EXIT();
        }
    }

    protected void setStartState(AState<E> startState) {
        if (DEBUG) {
            ENTER(mMethodPrefix + "setStartState");
            ASSERT_NON_NULL(startState, "startState");
        }
        startState.onEnter();
        mCurrentState = startState; // Current State now defined...
        if (DEBUG) {
            EXIT();
        }
    }

    protected AState<E> getCurrentState() {
        return mCurrentState;
    }
}

