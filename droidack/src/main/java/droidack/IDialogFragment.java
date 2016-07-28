/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;

public interface IDialogFragment extends IControlFragment {

	interface IDialogListener {
		void onDismiss(IDialogFragment fragment);
		void onCancel(IDialogFragment fragment);
	}

	void setDialogListener(IDialogListener listener);
}
