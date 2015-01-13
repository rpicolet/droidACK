package rpicolet.mvc;

public interface IObserver<M extends IModel<M, P>, P extends Enum<P>> 
		 extends IControl {

	/**
	 * Accept/handle notification of a Property change to a Model
	 * 
	 * @param model - Model instance that changed
	 * @param property - one of its Property Enum types
	 */
	public void onChange(M model, P property);
}
