/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import java.util.List;

/**
 * Implements the Composite pattern for Controls; also propagates
 * common/shared lifecycle events to its child Controls
 *
 * @author Randy Picolet
 */

public abstract class AControlModule<C extends IControl>
			  extends AControl
		   implements IControlModule<C> {

	// Start with enough memory for 10 children...
	private final ArrayList<C> mChildren = new ArrayList<>(10);

	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //

	@Override
	public void onCreate(IControlModule<IControl> parent) {
		super.onCreate(parent);
		int count = mChildren.size();
		@SuppressWarnings("unchecked")
		IControlModule<IControl> _this = (IControlModule<IControl>) this;
		for (int i = 0; i < count; i++)
			mChildren.get(i).onCreate(_this);
	}
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
		// Forget the children
		mChildren.clear();
    }

	//	**************   C O N T R O L   S T R U C T U R E   ***************  //

	// Call only before onCreate!
	@Override
	public void addChild(C childControl) {
		if (DEBUG)
			ASSERT_NON_NULL(childControl, "addChild(): childControl");
		mChildren.add(childControl);
		/*
		@SuppressWarnings("unchecked")
		IControlModule<IControl> parent = (IControlModule<IControl>) this;
		if (childControl != null) childControl.onCreate(parent);
		*/
	}
	@Override
	public final List<C> getChildren() {
		return mChildren;
	}
	@Override
	public final C getChild(int index) {
		return mChildren.get(index);
	}
}
