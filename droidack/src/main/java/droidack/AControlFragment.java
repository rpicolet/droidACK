/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Convenience class for ControlFragments that would otherwise
 * directly extend Fragment; provides life-cycle wrappers and
 * delegation to the RootModule
 * 
 * @author Randy Picolet
 */
public abstract class AControlFragment extends Fragment 
		   implements IControlFragment {
	
	//	*************   F R A G M E N T   L I F E C Y C L E   **************  //

	/*
	 *  Logging tag
	 */
	protected final static boolean DEBUG = Debug.getEnabled();
    protected final String mTag = this.getClass().getSimpleName();
    
    protected int mInstanceId = 0;
    protected int mLayoutId = 0;
    protected Activity mActivity;    
	
    // Setting args via setArguments() with the Bundle created here
    // overrides values set by setDefaultArgs()...
    public static Bundle initArgs(int instanceId, int layoutId) {
    	Bundle args = new Bundle();
    	args.putInt(INSTANCE_ID_KEY, instanceId);
    	args.putInt(LAYOUT_ID_KEY, layoutId);
    	return args;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}
    @Override
    public void onCreate(Bundle inBundle) {
		super.onCreate(inBundle);
		Bundle args = getArguments();
		if (args != null) {
			mInstanceId = args.getInt(INSTANCE_ID_KEY);
			mLayoutId = args.getInt(LAYOUT_ID_KEY); 
		}
		getFragmentRootModule().onCreate(this);
	}
	@Override
    public View onCreateView(LayoutInflater inflater, 
    						 ViewGroup container, 
    						 Bundle inBundle) {
		if (DEBUG) 
			Log.d(mTag, "onCreateView()...");
		View view = null;
		if (mLayoutId != 0)
			view = inflater.inflate(mLayoutId, container, false);
		else if (DEBUG) 
			Log.i(mTag, "onCreateView(): no layoutId...");
		if (view != null)
			getRootModule().onCreateView(view);
		else if (container != null)
			getRootModule().onCreateView(container);
		else if (DEBUG)
			Log.i(mTag, "onCreateView(): no container...");
		if (DEBUG)
			if (view == null)
				Log.i(mTag, "onCreateView(): no view...");
    	return view;
	}
	@Override
    public void onActivityCreated(Bundle inBundle) {
    	super.onActivityCreated(inBundle);
		getRootModule().onActivityCreated(inBundle);
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
    public void onDestroyView() {
    	super.onDestroyView();
    	getRootModule().onDestroyView();
    }	
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	getRootModule().onDestroy();
    }	
	@Override
	public void onDetach() {
		super.onDetach();
		mActivity = null;
	}
    @Override
    public void onSaveInstanceState(Bundle outBundle) {
    	super.onSaveInstanceState(outBundle);
    	getRootModule().onSaveInstanceState(outBundle);
    }
    
	//	****************   C O N T R O L   C O N T E X T   *****************  //
    
	@Override
	public Fragment getFragment() {
		return this;
	}
	@Override
	public IRootModule getRootModule() {
		return getFragmentRootModule();
	}
	@Override
	public IFragmentRootModule getFragmentRootModule() {
		// TODO Auto-generated method stub
		return null;
	}

	
	// Set the default/static construction arguments; these are
    // overridden when an initArgs() Bundle is passed to setArguments()... 
    protected void setDefaultArgs(int instanceId, int layoutId) {
    	mInstanceId = instanceId;
    	mLayoutId = layoutId;
    }
	protected void logAndThrowNullError(String errorContext) {
		logAndThrowError(errorContext + ": null reference!");
	}
	protected void logAndThrowError(String errMsg) {
		Log.e(mTag, errMsg);
		throw new Error(mTag + "." + errMsg);
	}
}