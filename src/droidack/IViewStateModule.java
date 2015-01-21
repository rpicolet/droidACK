/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

/**
 * Extends/integrates IEventHandler and IChildModule to enable management 
 * of multiple mutually-exclusive IViewStates (sharing a common host/root 
 * View) as a finite state machine 
 * 
 * @author Randy Picolet
 */

public interface IViewStateModule<E extends Enum<E>> 
		 extends IChildModule<IViewState<E>>, IEventHandler<E> {
	
	//	****************   V I E W   M A N A G E M E N T   *****************  //
	
	/**
	 * Set a ViewState as the initial state to establish during onStart(); 
	 * to be of use, must be called before that. If not, the first ViewState
	 * added will be used by default
	 * 
	 * @param startState - ViewState to start in; must have been added 
	 * 					   previously
	 */
	public void setStartState(IViewState<E> startState);
	
	/**
	 * For ViewState use only; should only call from ViewState.onEvent()
	 * 
	 * @param newState - ViewState to switch to; must have been added 
	 * 					 previously 
	 */
	public void changeState(IViewState<E> newState);

	/**
	 * Courtesy logging/debug support only; get the currently-active ViewState
	 * instance; should _not_ be used to determine Events to send
	 * 
	 * @return - current ViewState
	 */
	public IViewState<E> getCurrentState();	
}