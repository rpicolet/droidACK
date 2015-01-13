package rpicolet.mvc;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

/**
 * 
 * @author Randy Picolet
 */

public abstract class AControlModule
			  extends ASimpleTree<IModuleControl>
		   implements IControlModule {

	// Fragment providing the life-cycle context for this ControlModule
	private IControlFragment mFragment = null;
	//
	private ArrayList<IChildFragmentListener> mChildFragListeners = 
			new ArrayList<IChildFragmentListener>();

	//	***********   A C T I V I T Y   I N T E G R A T I O N   ************  //
	
	// Prevent use of wrong onCreate
	@Override
	public final void onCreate(ICompositeControl<IControl> parent) {
		ASSERT(false, "onCreate(parent): use onCreate(fragment) instead!");
	}
	@Override
	public void onCreate(IControlFragment fragment) {
		if (DEBUG) {
			ASSERT_NON_NULL(fragment, "onCreate(); fragment");
			setMvcTag(fragment.getClass().getSimpleName() + "." + getMvcTag());
		}
		super.onCreate(null);	// Top-level Control in the Fragment, no parent
		mFragment = fragment;
	}

	//	**************   C O N T R O L   S T R U C T U R E   ***************  //
	
	@Override
	public final IControlFragment getFragment() {
		if (DEBUG)
			ASSERT_NON_NULL(mFragment, "getFragment(): mFragment");
		return mFragment;
	}
	@Override
	public void addChildFragmentListener(IChildFragmentListener listener) {
		if (listener == null) {
			if (DEBUG)
				ASSERT(false, "addChildFragmentListener(): listener");
			return;
		}
		mChildFragListeners.add(listener);
	}
	@Override
	public void removeChildFragmentListener(IChildFragmentListener listener) {
		mChildFragListeners.remove(listener);
	}
	@Override
	public boolean onChildEvent(Fragment childFragment, ChildEvent event) {
		int count = mChildFragListeners.size();
			// Local ModuleControls are listening
			for (int i = 0; i < count; i++)
				if (mChildFragListeners.get(i)
						.onChildEvent(childFragment, event)) 
					return true;
		// Not handled, delegate up the Fragment/Module tree
		onChildEvent(this, event);
		return false;	// Ignored across Fragments... see below
	}

	// Not handled in this ControlModule, so delegate to parent Fragment's
	@Override 
	public final void onChildEvent(IControl child, ChildEvent event) {
		Fragment myFragment = (Fragment) getFragment();
		((IControlFragment) myFragment.getParentFragment())
				.getControlModule().onChildEvent(myFragment, event);
	}
}