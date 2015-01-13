package rpicolet.mvc;


/**
 * Shared Model methods for Observer clients 
 * 
 * @author Randy Picolet
 *
 * @param <M> - Model entity type to be observed
 * @param <P> - Enum of Model Property types that can be observed
 */

public interface IModel<M extends IModel<M, P>, P extends Enum<P>> 
		 extends IComponent {
	
	/**
	 * Add a ModelObserver to those to be notified of property changes
	 * 
	 * @param observer - the Observer to add
	 * @param property - one of the Model's Property types
	 */
	public void addObserver(IObserver<M, P> observer, P property);

	/**
	 * Remove a ModelObserver from those to be notified of property changes
	 *  
	 * @param observer - the Observer to remove
	 * @param property - one of the Model's Property types
	 */
	public void removeObserver(IObserver<M, P> observer, P property);

	/**
	 * Commit pending changes to backing store (i.e. DB 
	 * insert/update/delete) if Model instance is new, 
	 * changed, or deleted; else do nothing
	 */
	public void commitChanges();
}