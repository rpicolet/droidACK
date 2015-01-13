package rpicolet.mvc;

import java.util.ArrayList;

public interface ICompositeControl<C extends IControl> 
		 extends IControl {

	/**
	 * Call from derived class onCreate() for each child control owned by 
	 * this parent container; invokes child's onCreate()...
	 * 
	 *  @param child - child IControl to add
	 */
	public void addChild(C child);
	
	/**
	 * Get the child Controls added previously
	 * 
	 * @return - ArrayList of added child Controls
	 */
	public ArrayList<C> getChildren();

	/**
	 * Get a child Control added previously
	 * 
	 * @param index - index of child Control
	 * @return - the indicated child Control; may be null
	 */
	public C getChild(int index);
}
