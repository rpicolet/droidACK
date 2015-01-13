//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.droidack;

/**
 * MediatorModule is responsible for maintaining bi-directional synchronization
 * between a selected Model instance and Views or other Controls dependent on 
 * that instance. 
 */  
public interface IMediatorModule<M extends IModel<M, P>, P extends Enum<P>>
		 extends IModelObserver<M, P>,
		 		 IComposableModule<IMediatorModule<?, ?>> {
	
	/**
	 * Gets the Model instance under mediation
	 * 
	 * @return - IModel instance; may be null
	 */
	public M getModel();
	/**
	 * Updates the currently selected Model instance and all dependent Views
	s * 
	 * @return - IModel instance
	 */
	public M syncToModel();
	/**
	 * Applies any pending changes to the backing store; this can be 
	 * called at any time in response to User or lifecycle events
	 */
	public void commitModelChanges();
}