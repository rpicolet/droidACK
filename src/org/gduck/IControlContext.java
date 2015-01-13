//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.gduck;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Specifies accessors for overall Control context for _either_ 
 * IControlActivity or IControlFragment
 *  
 * @author Randy Picolet
 */
interface IControlContext {

	// Accessors for this Activity's or Fragment's RootControlModule, 
	// its children, and for any child ControlFragments
	public Activity getActivity();
	public FragmentActivity getFragmentActivity();
	public IControlActivity getControlActivity();
	
	public Fragment getFragment();
	public IControlFragment getControlFragment();
	
	public IRootControlModule getRootControlModule();
	public FragmentManager getChildFragmentManager();

	public View getView();
}
