/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * AControl provides the shared implementations of IControl
 * 
 * @author Randy Picolet
 */
public abstract class AControl extends AComponent implements IControl {

	// CompositeControl containing this one 
	private IControlModule<IControl> mParent = null;
	// View/ViewGroup managed/used by this Control
	private View mView = null;
	// Life-cycle method/state flags
	private boolean mIsCreated = false;
	private boolean mIsViewCreated = false;
	private boolean mIsActivityCreated = false;
	private boolean mIsStarted = false;
	private boolean mIsResumed = false;
	
	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //

	/**
	 * Call first if overridden or extended with additional parameters
	 */
	@Override
    public void onCreate(IControlModule<IControl> parent) {
		if (DEBUG) {
			ENTER_MSG("onCreate");
			if (mIsCreated) 
				logW("onCreate(): already called!");
			ASSERT(!(parent == null && !IRootModule.class.isInstance(this)), 
					"null parent!");
		}
		mParent = parent;
		mView = null;

		mIsCreated = true;
		mIsViewCreated = false;
		mIsActivityCreated = false;
		mIsStarted = false;
		mIsResumed = false;
		if (DEBUG) EXIT();
	}
	/**
	 * Call to set view if overridden 
	 * (no need to be called first)...
	 */
	@Override
    public void onCreateView(View view) {
		createView(view);
	}
	/**
	 * Call to set view if overridden 
	 * (no need to be called first)...
	 */
	@Override
	public void onCreateView(View container, int resourceId) {
		View view;
		if (container != null)
			view = container.findViewById(resourceId);
		else
			view = getControlContext().getView().findViewById(resourceId);
		if (DEBUG)
			ASSERT_NON_NULL(view, "view");
		createView(view);
	}
	/**
	 * Call first/early if overridden...
	 */
	@Override
    public void onActivityCreated(Bundle inBundle) {
		if (DEBUG) {
			ENTER_MSG("onActivityCreated");
			ASSERT(!mIsActivityCreated, "already called!");
		}
		mIsActivityCreated = true;
		if (DEBUG) EXIT();
	}
	/**
	 * Call first/early if overridden...
	 */
    @Override
    public void onStart() {
		if (DEBUG) {
			ENTER_MSG("onStart");
			ASSERT(!mIsStarted, "already started!"); 
		}
		mIsStarted = true;
		if (DEBUG) EXIT();
    }	
	/**
	 * Call first/early if overridden...
	 */
    @Override
    public void onResume() {
		if (DEBUG) {
			ENTER_MSG("onResume");
			ASSERT(!mIsResumed, "already resumed!"); 
		}
		mIsResumed = true;
		if (DEBUG) EXIT();
    }	
	/**
	 * Call first/early if overridden...
	 */
    @Override
    public void onPause() {
		if (DEBUG) {
			ENTER_MSG("onPause");
			ASSERT(mIsResumed, "not resumed!");
		}
		mIsResumed = false;
		if (DEBUG) EXIT();
    }	
	/**
	 * Call first/early if overridden...
	 */
    @Override
    public void onStop() {
		if (DEBUG) {
			ENTER_MSG("onStop");
			ASSERT(mIsStarted, "not started!");
			ASSERT(!mIsResumed, "not paused!");
		}
		mIsStarted = false;
		if (DEBUG) EXIT();
    }	
	/**
	 * Call first/early if overridden...
	 */
    @Override
    public void onDestroyView() {
		if (DEBUG) {
			ENTER_MSG("onDestroyView");
			ASSERT(!mIsStarted, "not stopped!");
		}
		mIsViewCreated = false;
		mView = null;
		if (DEBUG) EXIT();
     }	
	/**
	 * Call first/early if overridden...
	 */
    @Override
    public void onDestroy() {
		if (DEBUG) {
			ENTER_MSG("onDestroy");
			ASSERT(!mIsStarted, "not stopped!");
		}
		mIsActivityCreated = false;  // TODO check out setRetainInstance() semantics!
		mIsCreated = false;
		if (DEBUG) EXIT();
    }
	/**
	 * Call first/early if overridden...
	 */
	@Override
	public void onSaveInstanceState(Bundle saveBundle){
		if (DEBUG) {
			ENTER_MSG("onSaveInstanceState");
			ASSERT(mIsStarted, "already stopped!");
			EXIT();
		}
	}
	@Override
	public final boolean isCreated() {
		return (mIsCreated);
	}
	@Override
	public final boolean isViewCreated() {
		return (mIsViewCreated);
	}
	@Override
	public final boolean isActivityCreated() {
		return (mIsActivityCreated);
	}
	@Override
	public final boolean isStarted() {
		return (mIsStarted);
	}
	@Override
	public final boolean isResumed() {
		return (mIsResumed);
	}
	@Override
	public final boolean isActive() {
		return isResumed();
	}
	@Override
	public final boolean isPaused() {
		return (mIsStarted && !mIsResumed);
	}
	private void createView(View view) {
		if (DEBUG) {
			ENTER_MSG("onCreateView");
			ASSERT(!mIsViewCreated, "already called!");
			if (view == null) 
				logD("null view...");
			//else
			// logD("viewId = " + view.getId());
		}
		mIsViewCreated = true;
		mView = view;
		if (DEBUG) EXIT();
	}

	//	**************   C O N T R O L   S T R U C T U R E   ***************  //
	
	@Override
	public final Activity getActivity() {
		return getControlFragment().getActivity();
	}
	@Override
	public final IControlContext getControlContext() {
		IControlFragment ctrlFrag = getControlFragment();
		return ctrlFrag == null ? getControlActivity() : ctrlFrag;
	}
	@Override
	public IControlActivity getControlActivity() {
		if (DEBUG) 
			ASSERT_NON_NULL(mParent, "mParent");
		return mParent.getControlActivity();
	}
	@Override
	public IControlFragment getControlFragment() {
		if (DEBUG) 
			ASSERT_NON_NULL(mParent, "mParent");
		return mParent.getControlFragment();
	}
	@Override
	public final IRootModule getRootModule() {
		
		return getControlFragment().getRootModule();
	}
	@Override
	public final IControlModule<IControl> getParent() {
		if (DEBUG) 
			ASSERT(!(mParent == null && !IRootModule.class.isInstance(this)), 
				"null mParent!");
		return mParent;
	}

	//	****************   V I E W   M A N A G E M E N T   *****************  //
	
	@Override
	public final View getView() {
		return mView;
	}
}