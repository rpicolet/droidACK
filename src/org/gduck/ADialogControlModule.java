package rpicolet.mvc;

public abstract class ADialogControlModule extends AControlModule 
							   implements IDialogControlModule {
	@Override
	public void onNeutralClick() {
		if (DEBUG)
			ASSERT(false, "No onNeutralClick() defined!");
	}
	
	@Override
	public void onPositiveClick() {
		if (DEBUG)
			ASSERT(false, "No onPositiveClick() defined!");		
	}
}