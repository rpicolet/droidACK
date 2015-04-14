/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Extends AViewState to represent the use of a shared ViewGroup host for one of
 * several mutually-exclusive nested ControlFragments
 * 
 * @author Randy Picolet
 */

public abstract class AFragmentHostState<E extends Enum<E>>
		      extends AViewState<E> 
		   implements IFragmentHostState<E> {

	private String mFragmentName;
	private int mLayoutId;
	private int mInstanceId = 0;
	private AFragmentHostModule<E> mHostManager = null;
	private Fragment mChildFragment = null;

	// ***********   A C T I V I T Y   I N T E G R A T I O N   ************ //

	@Override
	public void onCreate(IControlModule<IControl> parent) {
		this.onCreate(parent, null, 0);
	}
	@Override
	public void onCreate(IControlModule<IControl> parent, 
						 String fragmentName, 
						 int layoutId) {
		super.onCreate(parent);
		setHostManager(parent);
		mFragmentName = fragmentName;
		mLayoutId = layoutId;
	}
	// For TabState use
	protected void onCreate(IControlModule<IControl> parent, 
							String fragmentName, 
							int layoutId, 
							int instanceId) {
		this.onCreate(parent, fragmentName, layoutId);
		mInstanceId = instanceId;
	}
	/**
	 * Call first if overridden
	 */
	@Override
	public void onStart() {
		super.onStart();
		if (mFragmentName == null) {
			if (DEBUG) 
				logD("onStart(): no hosted fragment");
			return;
		}
    	Bundle args = AControlFragment.initArgs(mInstanceId, mLayoutId);
		mChildFragment = 
				Fragment.instantiate(getActivity(), mFragmentName, args);
		mHostManager.hostChildFragment(mChildFragment);
	}
	/**
	 * Call first if overridden
	 */
	@Override
	public void onStop() {
		super.onStop();
		mHostManager.removeChildFragment();
		mChildFragment = null;
	}
	public Fragment getChildFragment() {
		return mChildFragment;
	}
	// Handle type safety up-front
	private void setHostManager(IControl parent) {
		@SuppressWarnings(value = "unchecked")	// Warning seems unavoidable
		AFragmentHostModule<E> manager = 		// as does this assignment
			(AFragmentHostModule<E>) parent; 	// but cast should be safe
		mHostManager = manager;
	}
}