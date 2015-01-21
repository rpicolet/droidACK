/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

import java.util.ArrayList;

import android.os.Bundle;

/**
 * Extends AControlModule for a simple fixed tree of ChildModules; 
 * propagates "active"/"running" mode life-cycle calls to all children
 * 
 * @author Randy Picolet
 */

public abstract class ACompositeModule<C extends IChildModule<?>> 
	 	extends AControlModule<C> {

	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //

	@Override
	public void onStart() {
		super.onStart();
		ArrayList<C> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onStart();
    }
	@Override
	public void onResume() {
		super.onResume();
		ArrayList<C> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onResume();
    }
	@Override
    public void onPause() {
		super.onPause();
		ArrayList<C> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onPause();
	}
	@Override
    public void onStop() {
		super.onStop();
		ArrayList<C> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onStop();
	}
	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		super.onSaveInstanceState(outBundle);
		ArrayList<C> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onSaveInstanceState(outBundle);
	}
}
