package rpicolet.mvc;

// For use by FsmManager only...

interface IFsmState<E extends Enum<E>>
		 extends IEventHandler<E>, IControl {
	
	public void setIndex(int index);
	public int getIndex();
}
