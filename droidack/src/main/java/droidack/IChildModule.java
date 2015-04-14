/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

/**
 * Specifies an IControlModule that can be directly added 
 * to an IRootModule.
 * 
 * @author Randy Picolet
 */
public interface IChildModule<C extends IControl> extends IControlModule<C> {
}
