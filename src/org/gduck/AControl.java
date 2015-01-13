package rpicolet.mvc;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

/**
 * AControl provides the shared implementations of IControl
 * 
 * @author Randy Picolet
 */
public abstract class AControl extends AComponent implements IControl {

	// CompositeControl containing this one 
	private ICompositeControl<IControl> mParent = null;
	// View/ViewGroup managed by this Control
	private View mView = null;
	// Life-cycle method/state flags
	private boolean mIsCreated = false;
	private boolean mIsViewCreated = false;
	private boolean mIsActivityCreated = false;
	private boolean mIsStarted = false;
	private boolean mIsResumed = false;
	
	protected final String setMediumMvcTag() {
		if (mParent != null)
			return setMvcTag(mParent.getMvcTag() + "." + getMvcTag());
		return getMvcTag();
	}

	
	//	***********   A C T I V I T Y   I N T E G R A T I O N   ************  //

	@Override
	public final FragmentActivity getActivity() {
		return getFragment().getActivity();
	}
	@Override
	public IControlFragment getFragment() {
		if (DEBUG) 
			ASSERT_NON_NULL(mParent, "mParent");
		return mParent.getFragment();
	}
	/**
	 * Call first if overridden or extended with additional parameters
	 */
	@Override
    public void onCreate(ICompositeControl<IControl> parent) {
		if (DEBUG) {
			ENTER_MSG("onCreate");
			if (mIsCreated) 
				logW("onCreate(): already called!");
			ASSERT(!(parent == null && !IControlModule.class.isInstance(this)), 
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
			view = getFragment().getView().findViewById(resourceId);
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
	public final ICompositeControl<IControl> getParent() {
		if (DEBUG) 
			ASSERT(!(mParent == null && !IControlModule.class.isInstance(this)), 
				"null mParent!");
		return mParent;
	}
	@Override
	public final IControlModule getModule() {
		return getFragment().getControlModule();
	}
	/**
	 * Default implementation, delegates to parent;
	 * override to intercept as needed
	 */
	@Override
	public void onChildEvent(IControl child, ChildEvent event) {
		getParent().onChildEvent(this, event);
	}

	//	****************   V I E W   M A N A G E M E N T   *****************  //
	
	@Override
	public final View getView() {
		return mView;
	}
}