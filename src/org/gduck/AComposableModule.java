package org.gduck;

import java.util.ArrayList;

import android.os.Bundle;

/**
 * Extends AControlModule for a simple fixed tree of ComposableModules; 
 * propagates "active"/"running" mode life-cycle calls to all children
 * 
 * @author Randy Picolet
 */

public abstract class AComposableModule 
	 	extends AControlModule<IComposableModule<?>> 
	 	implements IComposableModule<IComposableModule<?>> {

	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //

	@Override
	public void onStart() {
		super.onStart();
		ArrayList<IComposableModule<?>> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onStart();
    }
	@Override
	public void onResume() {
		super.onResume();
		ArrayList<IComposableModule<?>> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onResume();
    }
	@Override
    public void onPause() {
		super.onPause();
		ArrayList<IComposableModule<?>> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onPause();
	}
	@Override
    public void onStop() {
		super.onStop();
		ArrayList<IComposableModule<?>> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onStop();
	}
	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		super.onSaveInstanceState(outBundle);
		ArrayList<IComposableModule<?>> children = getChildren();
		int count = children.size();
    	for (int i = 0; i < count; i++)
    		children.get(i).onSaveInstanceState(outBundle);
	}
}
