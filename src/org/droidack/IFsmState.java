//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.droidack;

// For use by FsmManager only...

interface IFsmState<E extends Enum<E>>
		 extends IEventHandler<E>, IControl {
	
	public void setIndex(int index);
	public int getIndex();
}
