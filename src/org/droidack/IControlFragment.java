//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.droidack;


public interface IControlFragment extends IControlContext {

	// Paraemeter keys for Fragment creation using initArgs()
	public static final String INSTANCE_ID_KEY = "instanceId";
	public static final String LAYOUT_ID_KEY = "layoutId";
	
    // Child Control accessors
    public IFragmentControlModule getFragmentControlModule();
}