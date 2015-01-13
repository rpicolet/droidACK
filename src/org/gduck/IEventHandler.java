package rpicolet.mvc;

/**
 * Generic Event-handling mechanism using an Enum for
 * Events to simplify use of switch statements
 * 
 * @author Randy Picolet
 *
 * @param <E> - Enum of Event types
 */
public interface IEventHandler<E extends Enum<E>> {  

	/**
	 * Handle/respond to the Event of the given type; 
	 * otherwise, just log and/or consume the Event
	 * 
	 * @param event - which Event has been triggered (non-null)
	 */
	public void onEvent(E event);
}
