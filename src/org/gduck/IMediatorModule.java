package rpicolet.mvc;

public interface IModelMediator<M extends IModel<M, P>, P extends Enum<P>>
		 extends IObserver<M, P>,
		 		 IFixedTree<IControl> {
	
	/**
	 * Get the Model instance under this Control's mediation
	 * 
	 * @return - IModel instance; may be null
	 */
	public M getModel();
}