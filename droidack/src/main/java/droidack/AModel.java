/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Shared implementation for IModel
 *
 * @author Randy Picolet
 */

@SuppressWarnings("EmptyMethod")
public abstract class AModel<M extends IModel<M, P>, P extends Enum<P>>
		extends AComponent implements IModel<M, P> {

	// Master notification-enabled  flag
    private static boolean  sIsNotifyEnabled;

	// Property->Observers map
	private final HashMap<P, CopyOnWriteArrayList<IModelObserver<M, P>>>
			mPropertyObservers = new HashMap<>();

	// Instance storage pending operation flags
	private boolean mInsertPending = false;
    private boolean mUpdatePending = false;
    private boolean mDeletePending = false;


    public static void setNotifyEnable(boolean enabled) {
        sIsNotifyEnabled = enabled;
    }

    @Override
	public void addObserver(IModelObserver<M, P> observer, P property) {
		synchronized (mPropertyObservers) {
			CopyOnWriteArrayList<IModelObserver<M, P>> observers =
										mPropertyObservers.get(property);
			if (observers == null) {
				observers = new CopyOnWriteArrayList<>();
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
		if (DEBUG) {
            ENTER("notifyObservers");
            ASSERT_NON_NULL(property, "property");
		}
        if (!sIsNotifyEnabled) {
			if (DEBUG) {
				logV("notifications disabled: property - " + property);
				EXIT();
			}
            return;
        }
		CopyOnWriteArrayList<IModelObserver<M, P>> observers;
		synchronized (mPropertyObservers) {
			observers = mPropertyObservers.get(property);
		}
		if (observers != null) {
			for (IModelObserver<M, P> observer : observers) {
				if (!observer.equals(skipObserver)) {
					@SuppressWarnings("unchecked") // Warning is appropriate (!),
					M changedInstance = (M) this;  // as abuse seems possible...
					observer.onChange(changedInstance, property);
				}
			}
		}
		if (DEBUG) {
			EXIT();
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
