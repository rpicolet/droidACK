package rpicolet.mvc;

import java.util.HashMap;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * 
 * @author Randy Picolet
 *
 * @param <T> Tab Enum; must include Id() methods!
 */
public abstract class ATabManager<T extends Enum<T>> 
			  extends AFragmentHostManager<T> {

	private static final int NO_INSTANCE_ID = -1;
	private static final int NO_RES_ID = 0;
	private static final String SELECTOR_ID = "selectorId";
	private static final String HILITE_ID = "hiliteId";
	private static final String LAYOUT_ID = "layoutId";
	private static final String FRAGMENT_NAME = "fragmentName";
	// Parent view
	private View mTabContainerView;
	// Actual tab-content host view
	private int mContentHostId;
	// Content hilite image view
	private int mContentHiliteId;
	ImageView mContentHilite;
	// Context-specific instance id (for tabs using the same Fragment class)
 	private int mInstanceId;
	// Tab event Enum
	private Class<T> mTabEnum;
	// Event<->State Maps 
	private final HashMap<T, TabState<T>> mEventToState = 
			new HashMap<T, TabState<T>>();
	private final HashMap<TabState<T>, T> mStateToEvent = 
			new HashMap<TabState<T>, T>();
	// State->SelectorHelper Map
	private final HashMap<TabState<T>, SelectorHelper> mStateToHelper = 
			new HashMap<TabState<T>, SelectorHelper>();

	protected void onCreate(ICompositeControl<IControl> parent,
			 Class<T> tabEnum,
			 int contentHostId, 
			 int contentHiliteId) {
		onCreate(parent, tabEnum, contentHostId, contentHiliteId, 
				NO_INSTANCE_ID);
	}		
	protected void onCreate(ICompositeControl<IControl> parent,
			 Class<T> tabEnum,
			 int contentHostId, 
			 int contentHiliteId,
			 int instanceId) {
		super.onCreate(parent);
		mTabEnum = tabEnum;
		mContentHostId = contentHostId;
		mContentHiliteId = contentHiliteId;
		mInstanceId = instanceId;
	    for (T tabEvent : mTabEnum.getEnumConstants()) addTab(tabEvent);
	}
	
	@Override
	public void onCreateView(View parent) {
		super.onCreateView(parent, mContentHostId);
		mTabContainerView = parent;
		mContentHilite = 
				(ImageView) parent.findViewById(mContentHiliteId);
    	// Set up each selector using the Helper  
		TabState<T> state;
		SelectorHelper helper;
		// Note: actual TabHost content is defined in nested Fragment layouts
    	for (final T tabEvent : mTabEnum.getEnumConstants()) {
    		state = mEventToState.get(tabEvent);
    		helper = new SelectorHelper(tabEvent);
    		// Map State->SelectorHelper
			mStateToHelper.put(state, helper);
    	}
    }
	@Override
	public void onStart() {
		super.onStart();	// This syncs the lifecycle of the hosted fragment
		// Set the initial tab selection
		TabState<T> startTab = (TabState<T>) getStartState();
		mStateToHelper.get(startTab).setSelected();
	}
	public T getCurrentTab() {
		return mStateToEvent.get(getCurrentState());
	}
	/**
	 * Tab Selectors, Events, and States are 1-to-1-to-1, so no need for
	 * State-specific Event handlers...
	 */
	@Override
	public void onEvent(T tabEvent) {
		TabState<T> oldState = (TabState<T>) getCurrentState();
		if (!oldState.preValidateEvent(tabEvent)) return;
		TabState<T> newState = mEventToState.get(tabEvent); 
		// Filter out re-clicks (really should not happen...)
		if (newState == oldState) return;
		// Set current selector view as unselected 
		mStateToHelper.get(oldState).setUnselected();
		// Change the actual tab host content fragment; no back stack
		changeState(newState, false);
		// Set the new selector view as selected
		mStateToHelper.get(newState).setSelected();
	}
	public final int getLayoutId(TabState<T> tabState) {
		return getSelectorId(mStateToEvent.get(tabState), LAYOUT_ID);
	}
	public final String getFragmentName(TabState<T> tabState) {
		T tabEvent = mStateToEvent.get(tabState);
		String fragName = null;
		try {
			fragName = (String) mTabEnum.getField(FRAGMENT_NAME).get(tabEvent);
		} catch (Exception e) { 
			if (DEBUG) 
				ASSERT(false, "getFragmentName(): Tab Enum must define a " 
							  + FRAGMENT_NAME + " field...");
		}
		return fragName;
	}
	protected TabState<T> getTabState(T tab) {
		return mEventToState.get(tab);
	}
	private void addTab(T tabEvent) {
		// Create the State for this TabEvent, using 
		// selectorId for fragment's instanceId
		int instanceId = mInstanceId == NO_INSTANCE_ID ? 
				getSelectorId(tabEvent, SELECTOR_ID) : mInstanceId;
		TabState<T> tabState = new TabState<T>(this, instanceId, tabEvent.name());
	   	// Map Event<->State (both directions) before adding child!
	   	mEventToState.put(tabEvent, tabState);
		mStateToEvent.put(tabState, tabEvent);
		// Add the child State (uses mStateToEvent...)
		addChild(tabState);
	}
	View getSelectorView(T tabEvent, String enumField) {
		// Get the viewId
		int viewId = getSelectorId(tabEvent, enumField);
		// Get the View whose resource Id (if any) is provided by 
		// the given Enum method
		View view = null;
		if (viewId != NO_RES_ID) {
			view = mTabContainerView.findViewById(viewId);
			if (DEBUG)
				ASSERT_NON_NULL(view, "getSelectorView(): view");
		}
		return view;
	}
	private int getSelectorId(T tabEvent, String enumField) {
		int id = NO_RES_ID;
		try {
			id = (Integer) mTabEnum.getField(enumField).get(tabEvent);
		} catch (Exception e) { 
			if (DEBUG) 
				ASSERT(false, "getSelectorId(): Tab Enum must define a " 
							  + enumField + " field...");
		}
		return id;
	}

    ////////////////////////////////////////////////
	//	*******  CLASS: Selector Helper  *******  //
    ////////////////////////////////////////////////
	private class SelectorHelper {
		final View mSelector;
		private final ImageView mSelectorHilite;
		
		public SelectorHelper(final T tabEvent) {
			// Set the selector and hilite views
			mSelector = getSelectorView(tabEvent, SELECTOR_ID);
			mSelectorHilite = (ImageView) getSelectorView(tabEvent, HILITE_ID);
    		// Set selector click callback
			mSelector.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View clickedView) {
					if (DEBUG)
						ASSERT(clickedView == mSelector,
								"onClick(): wrong view selector!");
					onEvent(tabEvent);
				}
			});
		}
		public void setSelected() {
			// TODO Set new selector to selected
			mSelector.setSelected(true);
			if (mSelectorHilite != null) {
				mSelectorHilite.setVisibility(View.VISIBLE);
				if (mContentHilite != null)
					mContentHilite.setImageDrawable(
							mSelectorHilite.getDrawable());
			}
		}
		public void setUnselected() {
			// TODO set current selector to unselected
			mSelector.setSelected(false);
			if (mSelectorHilite != null)
				mSelectorHilite.setVisibility(View.INVISIBLE);
		}
	}
}