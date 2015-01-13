package rpicolet.mvc;

/**
 * 
 * @author Randy Picolet
 */

public interface IControlModule 
	     extends ICompositeControl<IModuleControl>,
	     		 IChildFragmentListener {

	public void onCreate(IControlFragment fragment);
	
	public void addChildFragmentListener(IChildFragmentListener listener);
	
	public void removeChildFragmentListener(IChildFragmentListener listener);
}