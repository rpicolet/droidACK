/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt). 
 */

package droidack;

import android.app.Dialog;
import android.os.Bundle;

public interface IDialogFragment extends IControlFragment {

	public interface IDismissListener {
		public void onDismiss(IDialogFragment fragment);
	}

	public static final String LAYOUT_ID_KEY = "layoutId";
	
	public static final String TITLE_TEXT_ID_KEY = "titleTextId";
	public static final String CONTENT_LAYOUT_ID_KEY = "contentLayoutId";
	public static final String NEGATIVE_TEXT_ID_KEY = "negativeTextId";
	public static final String NEUTRAL_TEXT_ID_KEY = "neutralTextId";
	public static final String POSITIVE_TEXT_ID_KEY = "positiveTextId";

	public Dialog onCreateDialog(Bundle inBundle);
	public IDialogModule getDialogModule();
	public void setListener(IDismissListener listener);
}
