//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.droidack;



/**
 * 
 * @author Randy Picolet
 */

public abstract class AActivityControlModule
			  extends AControlModule<IComposableModule<?>>
		   implements IActivityControlModule {

	// Activity providing the life-cycle context for this ControlModule
	private IControlActivity mActivity = null;

	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //
	
	// Prevent use of wrong onCreate
	@Override
	public final void onCreate(ICompositeControl<IControl> parent) {
		ASSERT(false, "onCreate(parent): use onCreate(activity) instead!");
	}
	@Override
	public void onCreate(IControlActivity activity) {
		if (DEBUG) {
			ASSERT_NON_NULL(activity, "onCreate(); activity");
			setMvcTag(activity.getClass().getSimpleName() + "." + getMvcTag());
		}
		super.onCreate(null);	// Top-level Control in the Fragment, no parent
		mActivity = activity;
	}

	//	**************   C O N T R O L   S T R U C T U R E   ***************  //
	
	@Override
	public final IControlActivity getControlActivity() {
		if (DEBUG)
			ASSERT_NON_NULL(mActivity, "getControlActivity(): mActivity");
		return mActivity;
	}
	@Override
	public final IControlFragment getControlFragment() {
		return null;
	}
}