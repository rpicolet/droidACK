//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.gduck;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;

/**
 * Implements the Composite pattern for Controls; also propagates
 * common/shared lifecycle events to its child Controls
 * @author Randy Picolet
 */

public abstract class ACompositeControl<C extends IControl>
			  extends AControl
		   implements ICompositeControl<C> {

	// Start with enough memory for 10 children...
	private final ArrayList<C> mChildren = new ArrayList<C>(10);

	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //

	@Override
	public void onCreateView(View contextView) {
		super.onCreateView(contextView);
		int count = mChildren.size();
    	for (int i = 0; i < count; i++)
    		mChildren.get(i).onCreateView(contextView);
	}
	@Override
	public void onCreateView(View container, int resourceId) {
		super.onCreateView(container, resourceId);
		int count = mChildren.size();
    	for (int i = 0; i < count; i++)
    		mChildren.get(i).onCreateView(getView());
	}
	@Override
	public void onActivityCreated(Bundle inBundle) {
		super.onActivityCreated(inBundle);
		int count = mChildren.size();
    	for (int i = 0; i < count; i++)
    		mChildren.get(i).onActivityCreated(inBundle);
	}
	@Override
    public void onDestroyView() {
		super.onDestroyView();
		int count = mChildren.size();
    	for (int i = 0; i < count; i++)
    		mChildren.get(i).onDestroyView();
    }
	@Override
    public void onDestroy() {
		super.onDestroy();
		int count = mChildren.size();
    	for (int i = 0; i < count; i++)
    		mChildren.get(i).onDestroy();
    }

	//	**************   C O N T R O L   S T R U C T U R E   ***************  //
	
	@Override
	public void addChild(C childControl) {
		if (DEBUG) {
			if (childControl == null)
				logAndThrowNullError("addChildControl(): childControl");
		}
		mChildren.add(childControl);
		@SuppressWarnings("unchecked")
		ICompositeControl<IControl> parent = (ICompositeControl<IControl>) this;
		childControl.onCreate(parent);
	}
	@Override
	public final ArrayList<C> getChildren() {
		return mChildren;
	}
	@Override
	public final C getChild(int index) {
		return mChildren.get(index);
	}

}