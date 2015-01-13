package rpicolet.mvc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Common interface for Control components in the MVC Control 
 * framework, organized into four responsibility areas:'
 * 
 * 		-	Activity Integration
 * 		- 	Control Structure
 * 		-	View Management
 * 		- 	Model Mediation
 * 
 * Activity Integration ensures that every concrete Control
 * has direct access to the context Activity and its resources,
 * and provides the hooks needed to streamline implementation 
 * of the standard Fragment life-cycle callback methods.
 * Control Fragments should directly delegate to the 
 * like-named life-cycle methods specified below.  
 * 
 * Control Structure enables creation, management, and 
 * traversal of both logical and physical hierarchies of 
 * Control instances.
 * 
 * View Management provides the mechanisms needed to populate
 * and access the standard Android View hierarchy without 
 * extending any View classes, and builds on the Activity
 * Integration mechanisms to persist View-state across the
 * life-cycle, off-loading this responsibility from concrete
 * implementation classes. 
 * 
 * Finally, Model Mediation integrates the Model framework 
 * with both the Activity life-cycle and View management. 
 * Two key methods enable the Control framework:
 *  - syncToModel(): propagate the current Model state to the
 *    				 dependent Controls and Views, and enable
 *					 call-backs from Models and dependent Views
 *  - commitModelChanges(): save any pending Model changes to 
 *  				 the backing store
 *  
 * @author Randy Picolet
 */

public interface IControl extends IComponent, IChildControlListener {

	//	***********   A C T I V I T Y   I N T E G R A T I O N   ************  //

	/**
	 * Get the Fragment containing/providing context for this Control
	 * 
	 * @return - context Fragment instance
	 */
	public IControlFragment getFragment();
	
	/**
	 * Get the Activity providing context for this Control
	 * 
	 * @return - context Activity instance
	 */
	public Activity getActivity();
	
    /**
	 * Set this Control's parent in the tree; delegated 
	 * from Fragment.onCreate()
	 *
	 * @param parent - IComposite]Control containing this one;
	 * 				   null for top-level ControlModules
	 */
    public void onCreate(ICompositeControl<IControl> parent);

    /**
 	 * Set the view to be used by this Control; delegated from 
 	 * Fragment.onCreateView()
 	 * 
 	 * @param view - View to be used by the Control;
 	 * 			     may be null
 	 */
    public void onCreateView(View view);
 	
     /**
 	 * Set the view to be used by this Control; delegated from 
 	 * Fragment.onCreateView()
 	 * 
 	 * @param container - containing View; non-null
 	 * @param resourceId - id of view to be used by this control; 
 	 * 			           non-zero
 	 */
    public void onCreateView(View container, int resourceId);
 	
    /**
	 * Delegate from Fragment.onActivityCreated()
	 * 
	 * @param inBundle - same Bundle instance 
	 *  				 passed in to Fragment
	 */
    public void onActivityCreated(Bundle inBundle);

    /**
	 * Delegate from Fragment.onStart() 
	 */
	public void onStart();

    /**
	 * Delegate from Fragment.onResume() 
	 */
	public void onResume();

	/**
	 * Delegate from Fragment.onPause() 
	 */
	public void onPause();

    /**
	 * Delegate from Fragment.onStop() 
	 */
	public void onStop();

	/**
	 * Delegate from Fragment.onDestroyView() 
	 */
	public void onDestroyView();

	/**
	 * Delegate from Fragment.onDestroy() 
	 */
	public void onDestroy();

	/**
	 * Delegate from Fragment.onSaveInstanceState() 

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
	 * Get the ICompositeControl containing this one
	 * @return - ICompositeControl, null for top-level (ControlModules)
	 */
	public ICompositeControl<IControl> getParent();

	/**
	 * Get the ControlModule instance containing this one
	 * @return - IControlModule, null for a ControlModule
	 */
	public IControlModule getModule();
	

	//	****************   V I E W   M A N A G E M E N T   *****************  //
	
	/**
	 * Get the View used by this Control;
	 * may be null
	 * 
	 * @return - View instance
	 */
	public View getView();
}