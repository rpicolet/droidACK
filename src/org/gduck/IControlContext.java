package org.gduck;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

interface IControlContext {

	// Accessors for this Activity's or Fragment's RootControlModule, 
	// its children,  and for any child ControlFragments

	public Activity getActivity();
	public FragmentActivity getFragmentActivity();
	public View getView();
	public IRootControlModule getRootControlModule();
	public FragmentManager getFragmentManager();

	// Standard Activity Lifecycle delegates
	public void onCreate(Bundle inBundle);
    public void onStart();
    public void onResume();
    public void onPause();
    public void onStop();
    public void onDestroy();
    public void onSaveInstanceState(Bundle outBundle);
}
