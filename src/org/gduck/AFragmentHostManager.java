package rpicolet.mvc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Extends AFsmManager to enable supervising 
 * multiple mutually-exclusive Fragment Controls 
 * that share a common ViewGroup host
 * 
 * @author Randy Picolet
 */

public abstract class AFragmentHostManager<E extends Enum<E>> 
			  extends AFsmManager<E> 
		   implements IChildFragmentListener {
	
	//	****************   V I E W   M A N A G E M E N T   *****************  //
	
	// Currently hosted child Fragment, if not null
	private IControlFragment mChildFragment = null;
	// Currently active FragmentTransaction, if not null
	private FragmentTransaction mTransaction = null;
	// Needed to acquire FragmentTransactions
	private FragmentManager mFragmentManager; 
	// Support for back-stack operation
	private boolean mAddToBackStack;

	protected final IControlFragment getChildFragment() {
		return mChildFragment;
	}
	/**
	 * Default implementation, delegates to parent, override as needed
	 */
	@Override
	public boolean onChildEvent(Fragment fragment, ChildEvent event) {
		if (fragment == mChildFragment) { 
			super.onChildEvent(this, event);
			return true;
		}
		return false;
	}
	/**
	 * Call from onEvent() methods
	 * 
	 * @param addToBackStack - true adds transaction to 
	 * 						   back stack, false does not
	 */
	public final void changeState(AFragmentHostState<E> state, 
								  boolean addToBackStack) {
		mAddToBackStack = addToBackStack;
		changeState(state);
	}
	/**
	 * Call from onStart() methods
	 * 
	 * @param childFragment - child ControlFragment instance to host
	 */
	protected final void hostChildFragment(Fragment childFragment) {
		removeChildFragment();
		if (childFragment == null) {
			if (DEBUG)
				logAndThrowNullError("hostChildFragment(): childFragment");
			return;
		}
		mChildFragment = (IControlFragment) childFragment;
		mTransaction.add(getView().getId(), childFragment);
	}
	/**
	 * Call from onStop() methods
	 */
	protected final void removeChildFragment() {
		if (mChildFragment != null) {
			// If no transaction is in operation, assume removal by the system
			// (e.g. as a nested fragment of one being removed)
			if (mTransaction != null)
				mTransaction.remove((Fragment) mChildFragment);
			mChildFragment = null;
		}
	}
	/**
	 * Not for child class or State use
	 * 
	 * Use FragmentTransactions...
	 */
	@Override
	protected final void onStart(IFsmState<E> state) {
		mFragmentManager = getFragment().getChildFragmentManager();
		mTransaction = mFragmentManager.beginTransaction();
		state.onStart();
		mTransaction.commit();
		mTransaction = null;
	}

	/**
	 * Not for child class or State use
	 * 
	 * Use FragmentTransactions...
	 */
	@Override
	protected final void transition(IFsmState<E> oldState,
									IFsmState<E> newState) {
		mTransaction = mFragmentManager.beginTransaction();
		oldState.onStop();
		newState.onStart();
		if (mAddToBackStack) mTransaction.addToBackStack(null);
		mTransaction.commit();
		mTransaction = null;
	}
}