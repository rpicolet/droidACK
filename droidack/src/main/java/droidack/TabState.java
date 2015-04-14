/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;


/**
 * TabState control; responsible only for the actual nested
 * ControlFragment; selector buttons are handled by the Manager
 
 * @author Randy Picolet
 *
 * @param <T> - Tab event enum
 */

public class TabState<T extends Enum<T>> 
			  extends AFragmentHostState<T> {

	private final ATabManager<T> mManager;
	private final int mInstanceId;

	public TabState(ATabManager<T> manager, int instanceId, String tabName) {
		mManager = manager;
		mInstanceId = instanceId;
		String mTag = super.getAckTag() + "." + tabName;
		setAckTag(mTag);
	}
	@Override
	public final void onCreate(IControlModule<IControl> parent) {
		String fragmentName = mManager.getFragmentName(this);
		int layoutId = mManager.getLayoutId(this);
		if (fragmentName == null && layoutId == 0)
			super.onCreate(parent);
		else if (fragmentName != null && layoutId != 0)
			super.onCreate(parent, fragmentName, layoutId, mInstanceId);
		else if (DEBUG)
			ASSERT(false, "illegal fragmentName-layoutId combination!");
	}
	@Override
	public final void onEvent(T tab) {
		if (DEBUG)
			logAndThrowError("onEvent(); should not be called for TabStates!");
	}
}
