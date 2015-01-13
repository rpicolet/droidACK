//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.gduck;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Convenience class for ControlFragments that would otherwise
 * directly extend Fragment; provides life-cycle wrappers and
 * delegation to the RootControlModule
 * 
 * @author Randy Picolet
 */
public abstract class AControlFragment extends Fragment 
		   implements IControlFragment {
	
	//	*************   A C T I V I T Y   L I F E C Y C L E   **************  //

	/**
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
		if (DEBUG)
			Log.d(mTag, "onAttach()...");
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
		getFragmentControlModule().onCreate(this);
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
			getRootControlModule().onCreateView(view);
		else if (container != null)
			getRootControlModule().onCreateView(container);
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
		getRootControlModule().onActivityCreated(inBundle);
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
    public void onDestroyView() {
    	super.onDestroyView();
    	getRootControlModule().onDestroyView();
    }	
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	getRootControlModule().onDestroy();
    }	
	@Override
	public void onDetach() {
		if (DEBUG)
			Log.d(mTag, "onDetach()...");
		super.onDetach();
		mActivity = null;
	}
    @Override
    public void onSaveInstanceState(Bundle outBundle) {
    	super.onSaveInstanceState(outBundle);
    	getRootControlModule().onSaveInstanceState(outBundle);
    }
    
	public FragmentActivity getFragmentActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	public IControlActivity getControlActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	public Fragment getFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	public IControlFragment getControlFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	public IRootControlModule getRootControlModule() {
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