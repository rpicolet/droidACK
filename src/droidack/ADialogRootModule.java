/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

public abstract class ADialogRootModule extends AFragmentRootModule 
							   implements IDialogRootModule {
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