package org.gduck;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

public interface IControlActivity {

	public FragmentActivity getActivity();
	public View getView();
	public IRootControlModule getRootControlModule();
	public FragmentManager getChildFragmentManager();

	public void onCreate(Bundle inBundle);
    public void onStart();
    public void onResume();
    public void onPause();
    public void onStop();
    public void onDestroy();
    public void onSaveInstanceState(Bundle outBundle);
}