/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

/**
 * 
 * @author Randy Picolet
 */

public abstract class AFragmentRootModule
			  extends ACompositeModule<IChildModule<?>>
		   implements IFragmentRootModule {

	// Fragment providing the life-cycle context for this ControlModule
	private IControlFragment mFragment = null;

	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //
	
	// Prevent use of wrong onCreate
	@Override
	public final void onCreate(IControlModule<IControl> parent) {
		ASSERT(false, "onCreate(parent): use onCreate(fragment) instead!");
	}
	@Override
	public void onCreate(IControlFragment fragment) {
		if (DEBUG) {
			ASSERT_NON_NULL(fragment, "onCreate(); fragment");
			setAckTag(fragment.getClass().getSimpleName() + "." + getAckTag());
		}
		super.onCreate(null);	// Top-level Control in the Fragment, no parent
		mFragment = fragment;
	}

	//	**************   C O N T R O L   S T R U C T U R E   ***************  //
	
	@Override
	public final IControlFragment getControlFragment() {
		if (DEBUG)
			ASSERT_NON_NULL(mFragment, "getFragment(): mFragment");
		return mFragment;
	}
}