/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;

/**
 * Base class for a generic utility StateMachine that does not particpate
 * in Activity/Fragment lifecycle or Control events.
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
        exit();
        enter(newState);
        if (DEBUG) {
            EXIT();
        }
    }

    protected void exit() {
        if (DEBUG) {
            ASSERT_NON_NULL(mCurrentState, "mCurrentState");
        }
        // Undefine current State
        AState<E> currentState = mCurrentState;
        mCurrentState = null;
        currentState.onExit();
    }

    protected void enter(AState<E> state) {
        if (DEBUG) {
            ASSERT_NON_NULL(state, "state");
            ASSERT_NULL(mCurrentState, "mCurrentState");
        }
        state.onEnter();
        mCurrentState = state;
        // Current State now defined...
    }

    protected AState<E> getCurrentState() {
        return mCurrentState;
    }
}

