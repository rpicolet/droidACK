/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Common interface for Control components in the ACK Control
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
	 * Set this Control's immediate parent in the tree; delegates
	 * from Activity.onCreate() or Fragment.onCreate()
	 *
	 * @param parent - IControlModule containing this one;
	 * 				   		 s/b null if this is an IRootModule
	 */
    void onCreate(IControlModule<IControl> parent);

    /**
 	 * Set the view to be used by this Control; delegates from
 	 * Activity.onCreate() or Fragment.onCreateView()
 	 *
 	 * @param view - View to be used by the Control;
 	 * 			     may be null
 	 */
    void onCreateView(View view);

     /**
 	 * Set the view to be used by this Control; delegates from
 	 * Fragment.onCreateView()
 	 *
 	 * @param container - containing ancestor View; non-null
 	 * @param resourceId - id of view to be used by this control;
 	 * 			           non-zero
 	 */
    void onCreateView(View container, int resourceId);

    /**
	 * Delegates from Fragment.onActivityCreated()
	 *
	 * @param inBundle - same Bundle instance
	 *  				 passed in to Fragment
	 */
    void onActivityCreated(Bundle inBundle);

    /**
	 * Delegates from Activity.onStart() or Fragment.onStart()
	 */
	void onStart();

    /**
	 * Delegates from Activity.onResume() or Fragment.onResume()
	 */
	void onResume();

	/**
	 * Delegates from Activity.onPause() or Fragment.onPause()
	 */
	void onPause();

    /**
	 * Delegates from Activity.onStop or Fragment.onStop()
	 */
	void onStop();

	/**
	 * Delegates from Fragment.onDestroyView()
	 */
	void onDestroyView();

	/**
	 * Delegates from Activity.onDestroy or Fragment.onDestroy()
	 */
	void onDestroy();

	/**
	 * Delegates from Activity.onSaveInstanceState() or
	 * Fragment.onSaveInstanceState()
	 *
	 * @param outBundle - same Bundle instance
	 *  				  passed in to Fragment
	 */
	void onSaveInstanceState(Bundle outBundle);


	/**
	 * @return - true after onCreate() and before onDestroy(),
	 * 			  else false
	 */
	boolean isCreated();

	/**
	 * @return - true after onCreateView() and before onDestroyView(),
	 * 			  else false
	 */
	boolean isViewCreated();

	/**
	 * @return - true after onActivityCreated() and before onDestroy(),
	 * 			  else false
	 */
	boolean isActivityCreated();

	/**
	 * @return - true after onStart() and before onStop(),
	 * 			  else false
	 */
	boolean isStarted();

	/**
	 * @return - true after onResume() and before onPause(),
	 * 			  else false
	 */
	boolean isResumed();

	/**
	 * Same semantics as isResumed()
	 */
	boolean isActive();

	/**
	 * @return - true if isStarted() and not isResumed(),
	 * 			  else false
	 */
	boolean isPaused();

	//	**************   C O N T R O L   S T R U C T U R E   ***************  //

	/**
	 * Gets the Activity providing context for this Control; if this is
	 * a Fragment Control, may or may not return a ControlActivity
	 *
	 * @return - context Activity instance; only valid after onAttach()
	 */
	Activity getActivity();

	/**
	 * Gets the ControlContext for this Control
	 *
	 * @return - context ControlContext instance; non-null
	 */
	IControlContext getControlContext();

	/**
	 * Gets the ControlActivity for this Control
	 *
	 * @return - context ControlActivity instance; null iff this
	 * 			 is a Fragment Control and there is no ControlActivity
	 */
	IControlActivity getControlActivity();

	/**
	 * Gets the ControlFragment containing/providing context for this Control
	 *
	 * @return - context ControlFragment instance; null iff this is an
	 * 			 Activity Control
	 */
	IControlFragment getControlFragment();

	/**
	 * Gets the IRootModule instance containing this Control
	 *
	 * @return - IRootModule, null iff this is a RootModule
	 */
	IRootModule getRootModule();

	/**
	 * Gets the parent IControlModule containing this one
	 *
	 * @return - IControlModule, null iff this is a RootModule
	 */
	IControlModule<IControl> getParent();

	//	****************   V I E W   M A N A G E M E N T   *****************  //

	/**
	 * Gets the View used by this Control; may be null
	 *
	 * @return - View instance
	 */
	View getView();
}
