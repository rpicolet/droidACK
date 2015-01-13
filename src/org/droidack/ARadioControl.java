//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.droidack;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public abstract class ARadioControl<E extends Enum<E>> 
			  extends AControl 
		   implements OnClickListener {

	private View mContainer;	// NOTE: do NOT use a RadioGroup!!
	private E[] mEnumValues;
	private int mValueCount;
	private int[] mButtonIds;
	private RadioButton[] mButtons;
	private int mSelectionIndex = -1;

	protected void onCreate(ICompositeControl<IControl> parent, 
			Class<E> valueEnum, int[] buttonIds) {
		super.onCreate(parent);
		if (DEBUG) {
			ASSERT_NON_NULL(valueEnum, "valueEnum");
			ASSERT_NON_NULL(buttonIds, "buttonIds");
		}
		mEnumValues = valueEnum.getEnumConstants();
		mValueCount = mEnumValues.length;
		mButtonIds = buttonIds;
		if (DEBUG)
			ASSERT(mButtonIds.length == mValueCount, 
					"buttonId/enum length mismatch!");
	}
	@Override
	public void onCreateView(View container) {
		super.onCreateView(container);
		if (DEBUG) 
			ASSERT(!(RadioGroup.class.isInstance(container)), 
					"conflicts with use of RadioGroup!"); 
		mContainer = container;
		mButtons = new RadioButton[mButtonIds.length];
		RadioButton button = null;
		for (int i = 0; i < mValueCount; i++ ) {
			button = (RadioButton)mContainer.findViewById(mButtonIds[i]);
			button.setOnClickListener(this);
			button.setTag(Integer.valueOf(i));
			button.setChecked(false);
			mButtons[i] = button;
		}
	}
	@Override
	public void onClick(View view) {
		mButtons[mSelectionIndex].setChecked(false);
		mSelectionIndex = ((Integer)view.getTag()).intValue();
		if (DEBUG)
			ASSERT(mButtons[mSelectionIndex] == view, "index/view mismatch!");
		((RadioButton)view).setChecked(true); 
	}
	// Call after view (buttons) created and before resume...
	public void setInitialSelection(E initSelection) {
		if (DEBUG) { 
			ASSERT_NON_NULL(mButtons, "mButtons");
			ASSERT_NON_NULL(initSelection, "initSelection");
		}
		mSelectionIndex = initSelection.ordinal();
		mButtons[mSelectionIndex].setChecked(true);
	}
	public E getSelection() {
		if (DEBUG)
			ASSERT(mSelectionIndex >= 0 && mSelectionIndex < mValueCount,
					"illegal mSelectionIndex!");
		return mEnumValues[mSelectionIndex];
	}
}