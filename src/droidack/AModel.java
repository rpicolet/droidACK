/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import droidack.IModel;

/**
 * Shared implementation for IModel 
 * 
 * @author Randy Picolet
 */

public abstract class AModel<M extends IModel<M, P>, P extends Enum<P>> 
		extends AComponent implements IModel<M, P> {
	
	// Property->Observers map
	private final HashMap<P, CopyOnWriteArrayList<IModelObserver<M, P>>> 
			mPropertyObservers =
					new HashMap<P, CopyOnWriteArrayList<IModelObserver<M, P>>>();

	// Instance storage pending operation flags
	private boolean 
			mInsertPending = false,
			mUpdatePending = false,
			mDeletePending = false;
	
	@Override
	public void addObserver(IModelObserver<M, P> observer, P property) {
		synchronized (mPropertyObservers) {
			CopyOnWriteArrayList<IModelObserver<M, P>> observers = 
										mPropertyObservers.get(property);
			if (observers == null) {
				observers = new CopyOnWriteArrayList<IModelObserver<M, P>>();
				mPropertyObservers.put(property, observers);
			}
			observers.add(observer);
		}
	}
	@Override
	public void removeObserver(IModelObserver<M, P> observer, P property) {
		synchronized (mPropertyObservers) {
			CopyOnWriteArrayList<IModelObserver<M, P>> observers = 
										mPropertyObservers.get(property);
			if (observers == null) return;
			observers.remove(observer);
			if (observers.size() == 0) 
				mPropertyObservers.remove(property);
		}
	}
	@Override
	public void commitChanges() {
		if (isDeletePending()) {
			preDelete();
			doDelete();
		} else if (isInsertPending()) {
			preInsert();
			doInsert();
		} else if (isUpdatePending()) {
			preUpdate();
			doUpdate();
		}
		commitComplete();
	}

	/**
	 * Call when the Model is changed to notify registered Observers
	 * 
	 * @param property - one of the Model's Property types
	 * @param skipObserver  - an Observer to skip when notifying
	 * 		of changes; may be null, may be non-null but not added
	 * 		Intended to allow clients to opt out of notification for 
	 * 		changes	they themselves are making; must be added to the 
	 * 		Model's (e.g. generated) setter method parameters..
	 */
	protected void notifyObservers(P property, IModelObserver<M, P> skipObserver) {
		if (DEBUG)
			if (property == null)
				logAndThrowNullError("notifyObservers(); property");
		CopyOnWriteArrayList<IModelObserver<M, P>> observers;
		synchronized (mPropertyObservers) {
			observers = mPropertyObservers.get(property);
		}
		if (observers == null) return;  // No observers for this property...
		for (IModelObserver<M, P> observer : observers) {
			if (!observer.equals(skipObserver)) {
				@SuppressWarnings("unchecked") // Warning is appropriate (!),
				M changedInstance = (M) this;  // as abuse seems possible...	
				observer.onChange(changedInstance, property);
			}
		}
	}
	protected void setInsertPending() {
		mInsertPending = true;
	}
	protected void setUpdatePending() {
		mUpdatePending = true;
	}
	protected void setDeletePending() {
		mDeletePending = true;
	}
	protected boolean isInsertPending() {
		return mInsertPending;
	}
	protected boolean isUpdatePending() {
		return mUpdatePending;
	}
	protected boolean isDeletePending() {
		return mDeletePending;
	}
	protected void commitComplete() {
		if (DEBUG)
			logD("commitComplete()...");
		mInsertPending = false;
		mUpdatePending = false;
		mDeletePending = false;
	}

	/*
	 *	Hook stubs for commitChanges, override as needed...
	 */
	protected void preInsert() {
	}
	protected void doInsert() {
	}
	protected void preUpdate() {
	}
	protected void doUpdate() {
	}
	protected void preDelete() {
	}
	protected void doDelete() {
	}
}