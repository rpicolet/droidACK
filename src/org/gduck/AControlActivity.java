//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.gduck;

import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import android.os.Bundle;

/**
 * Convenience class for ControlActivities that would otherwise
 * directly extend FragmentActivity; provides life-cycle wrappers and
 * delegation to the RootControlModule
 * 
 * @author Randy Picolet
 */
public abstract class AControlActivity extends FragmentActivity 
		   implements IControlActivity {
	
	//	*************   A C T I V I T Y   L I F E C Y C L E   **************  //

	/**
	 *  Logging tag
	 */
	protected final static boolean DEBUG = Debug.getEnabled();
    protected final String mTag = this.getClass().getSimpleName();
    
    protected final Activity mActivity = this;    
	
    @Override
    public void onCreate(Bundle inBundle) {
		super.onCreate(inBundle);
		getActivityControlModule().onCreate(this);
	}
    @Override
    public void onStart() {
    	super.onStart();
    	getRootControlModule().onStart();
    }	
    @Override
    public void onResume() {
    	super.onResume();
    	getRootControlModule().onResume();
    }	
    @Override
    public void onPause() {
    	super.onPause();
    	getRootControlModule().onPause();
    }	
    @Override
    public void onStop() {
    	super.onStop();
    	getRootControlModule().onStop();
    }	
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	getRootControlModule().onDestroy();
    }	
    @Override
    public void onSaveInstanceState(Bundle outBundle) {
    	super.onSaveInstanceState(outBundle);
    	getRootControlModule().onSaveInstanceState(outBundle);
    }
    
	protected void logAndThrowNullError(String errorContext) {
		logAndThrowError(errorContext + ": null reference!");
	}
	protected void logAndThrowError(String errMsg) {
		Log.e(mTag, errMsg);
		throw new Error(mTag + "." + errMsg);
	}
}