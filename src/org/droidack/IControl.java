//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.droidack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Common interface for Control components in the MVC Control 
 * framework, organized into these responsibility areas:'
 * 
 * 		-	Lifecycle Integration
 * 		- 	Control Structure
 * 		-	View Management
 * 
 * Lifecycle integration provides the hooks needed to streamline implementation 
 * of the standard Fragment life-cycle callback methods.
 * Control Fragments should directly delegate to the 
 * like-named life-cycle methods specified below.  
 * 
 * Control Structure enables creation, management, and 
 * traversal of both logical and physical hierarchies of 
 * Control instances, ensures that every Control instance
 * has access to the context Activity and its resources,.
 * 
 * View Management provides the mechanisms needed to populate
 * and access the standard Android View hierarchy without 
 * extending any View classes, and builds on the Lifecycle
 * Integration mechanisms to persist View-state across the
 * life-cycle, off-loading this responsibility from concrete
 * implementation classes. 
 * 
 * @author Randy Picolet
 */

public interface IControl extends IComponent {

	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //

	/**
	 * Set this Control's parent in the tree; delegates 
	 * from Activity.onCreate() or Fragment.onCreate()
	 *
	 * @param parent - ICompositeControl containing this one;
	 * 				   null if this is a RootControlModule
	 */
    public void onCreate(ICompositeControl<IControl> parent);

    /**
 	 * Set the view to be used by this Control; delegates from 
 	 * Activity.onCreate() or Fragment.onCreateView()
 	 * 
 	 * @param view - View to be used by the Control;
 	 * 			     may be null
 	 */
    public void onCreateView(View view);
 	
     /**
 	 * Set the view to be used by this Control; delegates from 
 	 * Fragment.onCreateView()
 	 * 
 	 * @param container - containing View; non-null
 	 * @param resourceId - id of view to be used by this control; 
 	 * 			           non-zero
 	 */
    public void onCreateView(View container, int resourceId);
 	
    /**
	 * Delegates from Fragment.onActivityCreated()
	 * 
	 * @param inBundle - same Bundle instance 
	 *  				 passed in to Fragment
	 */
    public void onActivityCreated(Bundle inBundle);

    /**
	 * Delegates from Activity.onStart() or Fragment.onStart() 
	 */
	public void onStart();

    /**
	 * Delegates from Activity.onResume() or Fragment.onResume() 
	 */
	public void onResume();

	/**
	 * Delegates from Activity.onPause() or Fragment.onPause() 
	 */
	public void onPause();

    /**
	 * Delegates from Activity.onStop or Fragment.onStop() 
	 */
	public void onStop();

	/** 
	 * Delegates from Fragment.onDestroyView() 
	 */
	public void onDestroyView();

	/**
	 * Delegates from Activity.onDestroy or Fragment.onDestroy() 
	 */
	public void onDestroy();

	/**
	 * Delegates from Activity.onSaveInstanceState() or
	 * Fragment.onSaveInstanceState() 
	 * 
	 * @param outBundle - same Bundle instance 
	 *  				  passed in to Fragment
	 */
	public void onSaveInstanceState(Bundle outBundle);

	
	/**
	 * @returns - true after onCreate() and before onDestroy(), 
	 * 			  else false
	 */
	public boolean isCreated();

	/**
	 * @returns - true after onCreateView() and before onDestroyView(), 
	 * 			  else false
	 */
	public boolean isViewCreated();

	/**
	 * @returns - true after onActivityCreated() and before onDestroy(),
	 * 			  else false
	 */
	public boolean isActivityCreated();

	/**
	 * @returns - true after onStart() and before onStop(), 
	 * 			  else false
	 */
	public boolean isStarted();

	/**
	 * @returns - true after onResume() and before onPause(), 
	 * 			  else false
	 */
	public boolean isResumed();

	/**
	 * Same semantics as isResumed()
	 */
	public boolean isActive();

	/**
	 * @returns - true if isStarted() and not isResumed(), 
	 * 			  else false
	 */
	public boolean isPaused();
	
	//	**************   C O N T R O L   S T R U C T U R E   ***************  //
	
	/**
	 * Gets the Activity providing context for this Control; if this is
	 * a Fragment Control, may or may not return a ControlActivity
	 * 
	 * @return - context Activity instance; only valid after onAttach()
	 */
	public Activity getActivity();

	/**
	 * Gets the ControlContext for this Control
	 * 
	 * @return - context ControlContext instance; non-null
	 */
	public IControlContext getControlContext();
	
	/**
	 * Gets the ControlActivity for this Control
	 * 
	 * @return - context ControlActivity instance; null iff this
	 * 			 is a Fragment Control and there is no ControlActivity
	 */
	public IControlActivity getControlActivity();
	
	/**
	 * Gets the ControlFragment containing/providing context for this Control
	 * 
	 * @return - context ControlFragment instance; null iff this is an 
	 * 			 Activity Control
	 */
	public IControlFragment getControlFragment();
	
	/**
	 * Gets the RootControlModule instance containing this Control
	 * 
	 * @return - IRootControlModule, null iff this is a RootControlModule
	 */
	public IRootControlModule getRootModule();

	/**
	 * Gets the ICompositeControl containing this one
	 * 
	 * @return - ICompositeControl, null iff this is a RootControlModules
	 */
	public ICompositeControl<IControl> getParent();

	//	****************   V I E W   M A N A G E M E N T   *****************  //
	
	/**
	 * Gets the View used by this Control; may be null
	 * 
	 * @return - View instance
	 */
	public View getView();
}