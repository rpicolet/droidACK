/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;


/**
 * Shared Model methods for ModelObserver clients
 *
 * @author Randy Picolet
 *
 * @param <M> - Model entity type to be observed
 * @param <P> - Enum of Model Property types that can be changed & observed;
 * 				Property types are arbitrary conveniences, and can map
 * 				to many actual fields, derived states, and/or their change events
 */

public interface IModel<M extends IModel<M, P>, P extends Enum<P>>
		 extends IComponent {

	/**
	 * Add a ModelObserver to those to be notified of property changes
	 *
	 * @param observer - the ModelObserver to add
	 * @param property - one of the Model's Property types
	 */
	void addObserver(IModelObserver<M, P> observer, P property);

	/**
	 * Remove a ModelObserver from those to be notified of property changes
	 *
	 * @param observer - the ModelObserver to remove
	 * @param property - one of the Model's Property types
	 */
	void removeObserver(IModelObserver<M, P> observer, P property);

	/**
	 * Commit pending changes to backing store (i.e. DB
	 * insert/update/delete) if Model instance is new,
	 * changed, or deleted; else do nothing
	 */
	void commitChanges();
}
