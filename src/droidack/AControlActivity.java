/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;

/**
 * Convenience class for ControlActivities that would otherwise
 * directly extend FragmentActivity; provides life-cycle wrappers and
 * delegation to the RootModule
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
    
    protected View mView;
	
    @Override
    public void onCreate(Bundle inBundle) {
		super.onCreate(inBundle);
		getActivityModule().onCreate(this);
	}
    @Override
    public void onStart() {
    	super.onStart();
    	getRootModule().onStart();
    }	
    @Override
    public void onResume() {
    	super.onResume();
    	getRootModule().onResume();
    }	
    @Override
    public void onPause() {
    	super.onPause();
    	getRootModule().onPause();
    }	
    @Override
    public void onStop() {
    	super.onStop();
    	getRootModule().onStop();
    }	
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	getRootModule().onDestroy();
    }	
    @Override
    public void onSaveInstanceState(Bundle outBundle) {
    	super.onSaveInstanceState(outBundle);
    	getRootModule().onSaveInstanceState(outBundle);
    }

	//	****************   C O N T R O L   C O N T E X T   *****************  //
    
	@Override
	public Activity getActivity() {
		return this;
	}
	@Override
	public Fragment getFragment() {
		return null;
	}
	@Override
	public IRootModule getRootModule() {
		return getActivityModule();
	}
	@Override
	public FragmentManager getChildFragmentManager() {
		return getSupportFragmentManager();
	}
	@Override
	public View getView() {
		if (DEBUG)
			if (mView == null)
				logAndThrowNullError("mView");
		return mView;
	}
	
	// Should be called by child during onCreate()
	protected void setView(View view) {
		mView = view;
	}
	
	// Conveniences
	protected void logAndThrowNullError(String errorContext) {
		logAndThrowError(errorContext + ": null reference!");
	}
	protected void logAndThrowError(String errMsg) {
		Log.e(mTag, errMsg);
		throw new Error(mTag + "." + errMsg);
	}

}