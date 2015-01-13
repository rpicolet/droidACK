//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.droidack;

public abstract class ADialogControlModule extends AFragmentControlModule 
							   implements IDialogControlModule {
	@Override
	public void onNeutralClick() {
		if (DEBUG)
			ASSERT(false, "No onNeutralClick() defined!");
	}
	
	@Override
	public void onPositiveClick() {
		if (DEBUG)
			ASSERT(false, "No onPositiveClick() defined!");		
	}
}