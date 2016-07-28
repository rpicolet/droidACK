/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;

import android.os.Bundle;
import java.util.List;

/**
 * Extends AControlModule for a simple fixed tree of ChildModules;
 * propagates "active"/"running" mode life-cycle calls to all children
 *
 * @author Randy Picolet
 */

public abstract class ACompositeModule<C extends IControl>
	 	extends AControlModule<C> {

	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //

	@Override
	public void onStart() {
		super.onStart();
		List<C> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onStart();
    }
	@Override
	public void onResume() {
		super.onResume();
		List<C> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onResume();
    }
	@Override
    public void onPause() {
		super.onPause();
		List<C> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onPause();
	}
	@Override
    public void onStop() {
		super.onStop();
		List<C> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onStop();
	}
	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		super.onSaveInstanceState(outBundle);
		List<C> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onSaveInstanceState(outBundle);
	}
}
