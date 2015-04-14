/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

import android.view.View;


public interface IControlFragment extends IControlContext {

	// Parameter keys for Fragment creation using initArgs()
	public static final String INSTANCE_ID_KEY = "instanceId";
	public static final String LAYOUT_ID_KEY = "layoutId";
	
    // Child Control accessors
    public IFragmentRootModule getFragmentRootModule();
    
    public View getView();
}