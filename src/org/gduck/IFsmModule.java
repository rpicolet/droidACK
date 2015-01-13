package rpicolet.mvc;

/**
 * Extends/integrates IEventHandler and ICompositeControl 
 * to enable management of multiple mutually-exclusive 
 * IViewStates (sharing a common host/root View) as a 
 * finite state machine 
 * 
 * @author Randy Picolet
 */

public interface IFsmManager<E extends Enum<E>> 
		 extends ICompositeControl<IFsmState<E>>, 
		         IEventHandler<E>, IModuleControl {
	
	//	****************   V I E W   M A N A G E M E N T   *****************  //
	
	/**
	 * Set a ViewState as the initial state to establish
	 * during onStart(); to be of use, must be called 
	 * before that.  If not called, the first ViewState
	 * added is will be the default start state
	 * 
	 * @param startState - ViewState to start in; must
	 *                     have been added previously
	 */
	public void setStartState(IFsmState<E> startState);
	
	/**
	 * For ViewState use only; should only call from 
	 * ViewState.onEvent()
	 * 
	 * @param newState - ViewState to switch to; must
	 * 					 have been added previously 
	 */
	public void changeState(IFsmState<E> newState);

	/**
	 * Courtesy logging/debug support only; get the 
	 * currently-active ViewState instance; should 
	 * _not_ be used to determine Events to send
	 * 
	 * @return - current ViewState
	 */
	public IFsmState<E> getCurrentState();	
}