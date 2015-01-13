//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.gduck;

/**
 * Extends/integrates IEventHandler and ICompositeControl 
 * to enable management of multiple mutually-exclusive 
 * IFsmStates (sharing a common host/root View) as a 
 * finite state machine 
 * 
 * @author Randy Picolet
 */

public interface IFsmModule<E extends Enum<E>> 
		 extends IComposableModule<IFsmState<E>>, IEventHandler<E> {
	
	//	****************   V I E W   M A N A G E M E N T   *****************  //
	
	/**
	 * Set a FsmState as the initial state to establish
	 * during onStart(); to be of use, must be called 
	 * before that.  If not called, the first FsmState
	 * added is will be the default start state
	 * 
	 * @param startState - FsmState to start in; must
	 *                     have been added previously
	 */
	public void setStartState(IFsmState<E> startState);
	
	/**
	 * For FsmState use only; should only call from 
	 * FsmState.onEvent()
	 * 
	 * @param newState - FsmState to switch to; must
	 * 					 have been added previously 
	 */
	public void changeState(IFsmState<E> newState);

	/**
	 * Courtesy logging/debug support only; get the 
	 * currently-active FsmState instance; should 
	 * _not_ be used to determine Events to send
	 * 
	 * @return - current FsmState
	 */
	public IFsmState<E> getCurrentState();	
}