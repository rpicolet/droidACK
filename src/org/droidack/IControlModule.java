//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.droidack;

/**
 * 
 * @author Randy Picolet
 */

/**
 * Specifies the basic composite ControlModule; package use only
 * 
 * @author Randy Picolet
 *
 * @param <C> 
 */
abstract interface IControlModule<C extends IComposableModule<?>>
	     extends ICompositeControl<C> {
}