/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;

import java.util.List;

/**
 * Defines the overall Composite pattern for Controls
 *
 * @author Randy Picolet
 */
public interface IControlModule<C extends IControl>
		 extends IControl {

	/**
	 * Call from derived class onCreate() for each child control owned by
	 * this parent container; invokes child's onCreate()...
	 *
	 *  @param child - child IControl to add
	 */
	void addChild(C child);

	/**
	 * Get the child Controls added previously
	 *
	 * @return - List of added child Controls
	 */
	List<C> getChildren();

	/**
	 * Get a child Control added previously
	 *
	 * @param index - index of child Control
	 * @return - the indicated child Control; may be null
	 */
	C getChild(int index);
}
