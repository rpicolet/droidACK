//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.gduck;

/**
 * Specifies a CompositeControl that can be directly added 
 * to a ControlModule.
 * 
 * @author Randy Picolet
 */
public interface IComposableModule<C extends IControl> 
		extends ICompositeControl<C> {
}
