package rpicolet.mvc;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface IControlFragment {

	public static final String INSTANCE_ID_KEY = "instanceId";
	public static final String LAYOUT_ID_KEY = "layoutId";
	
	public FragmentActivity getActivity();
	public View getView();
	public IControlModule getControlModule();
	public FragmentManager getChildFragmentManager();

	public void onAttach(Activity activity);
	public void onCreate(Bundle inBundle);
    public View onCreateView(LayoutInflater inflater, 
			 				 ViewGroup container, 
			 				 Bundle inBundle);
    public void onActivityCreated(Bundle inBundle);
    public void onStart();
    public void onResume();
    public void onPause();
    public void onStop();
    public void onDestroyView();
    public void onDestroy();
    public void onDetach();
    public void onSaveInstanceState(Bundle outBundle);
}