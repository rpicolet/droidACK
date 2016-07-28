package droidack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

/**
 * Author: Randy Picolet
 */
public class AlertDialogFragment extends ADialogFragment {

	public interface OnNeutralClickListener {
		void onClick(AlertDialogFragment fragment);
	}

	public interface OnPositiveClickListener {
		void onClick(AlertDialogFragment fragment);
	}

	// Short names
	static final int BUTTON_NEGATIVE = DialogInterface.BUTTON_NEGATIVE;
	static final int BUTTON_NEUTRAL = DialogInterface.BUTTON_NEUTRAL;
	static final int BUTTON_POSITIVE = DialogInterface.BUTTON_POSITIVE;

	protected int mAlertLayoutId;
	protected int mTitleTextId;
	protected int mMessageId;
	protected int mNegativeTextId;
	protected int mNeutralTextId;
	protected int mPositiveTextId;

	protected OnNeutralClickListener mNeutralClickListener;
	protected OnPositiveClickListener mPositiveClickListener;

	// Init the default built-in OnClickListener; can be replaced by initStandard()
	protected OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which) {
				case BUTTON_NEGATIVE:
					dialog.cancel();
					break;
				case BUTTON_NEUTRAL:
					if (mNeutralClickListener != null) {
						mNeutralClickListener.onClick(AlertDialogFragment.this);
					}
					break;
				case BUTTON_POSITIVE:
					if (mPositiveClickListener != null) {
						mPositiveClickListener.onClick(AlertDialogFragment.this);
					} else if (DEBUG) {
						logAndThrowNullError("mPositiveClickListener");
					}
					break;
				default:
					if (DEBUG) {
						logAndThrowError("Unknown which button!");
					}
			}
			// Always dismiss
			dismiss();
		}
	};

	private class RootModule extends AFragmentRootModule {}
	private final RootModule mRootModule = new RootModule();

	// Only default constructors allowed for Fragments
	public AlertDialogFragment() {}

	// Call immediately after construction if only the Positive response is needed
	public void init(int style,
					 int themeId,
					 int alertLayoutId,
					 int titleTextId,
					 int messageId,
					 int negativeTextId,
					 int positiveTextId,
					 OnPositiveClickListener positiveListener) {
		mStyle = style;
		mThemeId = themeId;
		mAlertLayoutId = alertLayoutId;
		mTitleTextId = titleTextId;
		mMessageId = messageId;
		mNegativeTextId = negativeTextId;
		mPositiveTextId = positiveTextId;
		mPositiveClickListener = positiveListener;
	}

	// Call immediately after construction if both Positive and Neutral responses are needed
	public void init(int style,
					 int themeId,
					 int alertLayoutId,
					 int titleTextId,
					 int messageId,
					 int negativeTextId,
					 int positiveTextId,
					 OnPositiveClickListener positiveListener,
					 int neutralTextId,
					 OnNeutralClickListener neutralListener) {
		init(style, themeId, alertLayoutId, titleTextId, messageId, negativeTextId,
				positiveTextId,positiveListener);
		mNeutralTextId = neutralTextId;
		mNeutralClickListener = neutralListener;
	}

	// Call immediately after construction for standard full control of all 3 responses
	public void initStandard(int style,
							 int themeId,
							 int alertLayoutId,
							 int titleTextId,
							 int messageId,
							 int negativeTextId,
							 int neutralTextId,
							 int positiveTextId,
							 OnClickListener onClickListener) {
		mStyle = style;
		mThemeId = themeId;
		mAlertLayoutId = alertLayoutId;
		mTitleTextId = titleTextId;
		mMessageId = messageId;
		mNegativeTextId = negativeTextId;
		mNeutralTextId = neutralTextId;
		mPositiveTextId = positiveTextId;
		mOnClickListener = onClickListener;
	}


	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle inBundle) {
		Activity activity = getActivity();
		LayoutInflater inflater = activity.getLayoutInflater();
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		if (mAlertLayoutId != 0)
			builder.setView(inflater.inflate(mAlertLayoutId, null));
		if (mTitleTextId != 0)
			builder.setTitle(mTitleTextId);
		if (mMessageId != 0)
			builder.setMessage(mMessageId);
		if (mNegativeTextId != 0)
			builder.setNegativeButton(mNegativeTextId, mOnClickListener);
		if (mNeutralTextId != 0)
			builder.setNeutralButton(mNeutralTextId, mOnClickListener);
		if (mPositiveTextId != 0)
			builder.setPositiveButton(mPositiveTextId, mOnClickListener);
		return builder.create();
	}

	@NonNull
	@Override
	public IFragmentRootModule getFragmentRootModule() {
		return mRootModule;
	}
}
