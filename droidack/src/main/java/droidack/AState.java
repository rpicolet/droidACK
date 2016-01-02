/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;

/**
 * Base class for a State in a generic AStateMachine that does not particpate
 * in Activity/Fragment lifecycle or Control events
 *
 * Author: Randy Picolet
 */
public abstract class AState<E extends Enum<E>>
        extends AComponent implements IEventHandler<E> {

    protected String mMethodPrefix;

    private boolean mIsEntered = false;

    protected AState() {
        if (DEBUG) {
            mMethodPrefix = "<" + getAckTag() + ">";
        }
    }

    protected void onEnter() { // May be called from onStart() - UI thread!
        if (DEBUG) {
            ENTER(mMethodPrefix + "onEnter");
            ASSERT(!mIsEntered, "already entered!");
        }
        mIsEntered = true;
        if (DEBUG) {
            EXIT();
        }
    }

    protected void onExit() { // May be called from onStop() - UI thread!
        if (DEBUG) {
            ENTER(mMethodPrefix + "onExit");
            ASSERT(mIsEntered, "not entered!");
        }
        mIsEntered = false;
        if (DEBUG) {
            EXIT();
        }
    }

    protected boolean preValidateEvent(E event) {
        if (DEBUG)
            ENTER(mMethodPrefix + "onEvent");
        if (event == null) {
            if (DEBUG) {
                ASSERT_NON_NULL(null, "event");
                EXIT();
            }
            return false;
        }
        if (DEBUG) {
            EXIT();
        }
        return true;
    }

    protected final void logAndThrowIllegalEventError(E event) {
        if (DEBUG) {
            logAndThrowError("Illegal event recieved: " + event);
        }
    }

}
