//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.droidack;

import java.util.ArrayList;

public abstract class AMediatorModule<M extends IModel<M, P>, P extends Enum<P>> 
			  extends AComposableModule<IMediatorModule<?, ?>> 
		   implements IMediatorModule<M, P> {
	
	// Semantic convenience
	public static final boolean SYNC_ROOT = true;
	public final IMediatorModule<M, P> SKIP_ME = this;
	public final IMediatorModule<M, P> NO_SKIP = null;
	
	// Independent sync flag
	private final boolean mIsSyncRoot;
	// Properties to observe directly; null OK, empty OK
	private P[] mProperties;
	// Mediated Model instance 
	private M mModel;

	protected AMediatorModule() {
		mIsSyncRoot = false;
	}
	protected AMediatorModule(boolean isSyncRoot) {
		mIsSyncRoot = isSyncRoot;
	}
	
	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //
	
	/**
	 * Call first if overridden...
	 */
	@Override
	public void onStart() {
		super.onStart();
		if (mIsSyncRoot) syncToModel();
	}
	/**
	 * Call first if overridden...
	 */
	@Override
	public void onPause() {
		super.onPause();
		if (mIsSyncRoot) commitModelChanges();
	}
	/**
	 * Call first if overridden...
	 */
	@Override
	public void onStop() {
		super.onStop();
		unobserveModel();
	}

	//	****************   M O D E L   M E D I A T I O N   *****************  //
	
	// IModelObserver
	/**
	 * Call first if overridden...
	 */
	@Override
	public void onChange(M model, P property) {
		if (DEBUG) {
			ENTER("onChange");
			ASSERT_NON_NULL(mProperties, "no properties set!");
			ASSERT(model == mModel, "invalid model!");
			ASSERT_NON_NULL(property, "property");
			ASSERT(isResumed(), "not resumed!");
			EXIT();
		}
	}
	
	// IMediatorModule
	@Override
	public final M getModel() {
		return mModel;
	}
	/**
	 * Force update of/return observed model and its mediated views
	 */
	@Override
	public final M syncToModel() {
		M model = acquireModel();
		if (model == null) {
			unobserveModel();
			updateViews();
		} else {
			observeModel(model);
			updateViews();
		}
		// Sync child ModelMediators
		ArrayList<IMediatorModule<?, ?>> mdlMediators = getChildren();
		int count = mdlMediators.size();
		for (int i = 0; i < count; i++)
			mdlMediators.get(i).syncToModel();
		return model;
	}
	@Override
	public final void commitModelChanges() {
		if (mModel != null) {
			mModel.commitChanges();
			// Commit model changes for child ModelMediators
			ArrayList<IMediatorModule<?, ?>> mdlMediators = getChildren();
			int count = mdlMediators.size();
			for (int i = 0; i < count; i++)
				mdlMediators.get(i).commitModelChanges();
		}
	}

	/**
	 * Call once before onStart() if observing any properties
	 *  
	 * @param properties - final (static OK?) array of Property 
	 * 					enum types to observe; non-null, non-empty
	 */
	protected final void setProperties(final P[] properties) {
		if (DEBUG) {
			ENTER("setProperties");
			ASSERT_NON_NULL(properties, "properties");
			ASSERT(properties.length > 0, "no properties!");
			ASSERT_NULL(mProperties, "already called!");
			ASSERT(!isStarted(), "already started!");
		}
		mProperties = properties;
		if (DEBUG) EXIT();
	}
	/**'
	 * Get the properties for this Mediator
	 * @return - array of Property enum types, may be null
	 */
	protected final P[] getProperties() {
		return mProperties;
	}
	/**
	 * Find or create the Model instance to be mediated
	 * 
	 * @return - mediated Model instance
	 */
	protected abstract M acquireModel();
	/**
	 * Update views to reflect observed property states
	 * NOTE: called even if model is null!
	 */
	protected void updateViews() {
		if (DEBUG)
			logD("No view updates...");
	}
	/**
	 * Utility for default case in onChange()
	 * 
	 * @param property - model Property enum value
	 */
	protected final void handleUnexpectedChange(P property) {
		if (DEBUG)
			ASSERT(false, "onChange(): unexpected property: "
						 	+ property.toString());
	}
    private void observeModel(M model) {
		if (model != mModel) {
			unobserveModel();
			mModel = model;
	    	if (mModel != null && mProperties != null) 
	    		for (P property : mProperties)
	   				mModel.addObserver(this, property);
		}
	}
    private void unobserveModel() {
		if (mModel != null) { 
			if (mProperties != null) 
				for (P property : mProperties)
	    			mModel.removeObserver(this, property);
	    	mModel = null;
		}
    }
}