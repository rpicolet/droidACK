//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.gduck;

public interface IModelObserver<M extends IModel<M, P>, P extends Enum<P>> 
		 extends IControl {

	/**
	 * Accept/handle notification of a Property change to a Model
	 * 
	 * @param model - Model instance that changed
	 * @param property - one of its Property Enum types
	 */
	public void onChange(M model, P property);
}
