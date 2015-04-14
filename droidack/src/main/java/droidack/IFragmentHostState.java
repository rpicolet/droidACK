/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

public interface IFragmentHostState<E extends Enum<E>> 
	     extends IViewState<E> {
	
	/**
	 * Call from child.onCreate(parent) if hosting a Fragment (the 
	 * normal case);  otherwise just call super.onCreate(parent) directly
	 * 
	 * @param parent - the FragmentHostManager parent
	 * @param fragmentName - class name for the hosted Fragment;
	 * @param layoutId - resource ID for hosted fragment layout
	 */
	public void onCreate(IControlModule<IControl> parent, String fragmentName, 
			int layoutId);
}
