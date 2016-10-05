/*
 *	Copyright (c) 2015,  Randy Picolet
 *
 *	This software is covered by the MIT license (see license.txt).
 */

package droidack;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Abstract DialogFragment
 *
 * @author Randy Picolet
 */
public abstract class ADialogFragment extends DialogFragment implements IDialogFragment {

	// Logging
	protected final static boolean DEBUG = Debug.getEnabled();
    protected final String mTag = this.getClass().getSimpleName();

	protected static final String STYLE_KEY = "style";
	protected static final String LAYOUT_ID_KEY = "layoutId";
	protected static final String THEME_ID_KEY = "themeId";

	protected int mStyle;
	protected int mThemeId;
	protected int mLayoutId;

	protected IDialogListener mDialogListener;

	private IFragmentRootModule mFragmentRootModule;

	// Use this Bundle to setArguments() prior to onAttach()
	public static Bundle initArgs(int style, int themeId, int layoutId) {
    	Bundle args = new Bundle();
		args.putInt(STYLE_KEY, style);
		args.putInt(THEME_ID_KEY, themeId);
		args.putInt(LAYOUT_ID_KEY, layoutId);
    	return args;
	}

	//	**********   L I F E C Y C L E   I N T E G R A T I O N   ***********  //

	@Override
    public void onCreate(Bundle inBundle) {
		super.onCreate(inBundle);
		// Set arg defaults
		mStyle = STYLE_NORMAL;
		mThemeId = 0;
		mLayoutId = 0;
		// Apply any Bundle args
		Bundle args = getArguments();
		if (args != null) {
			mStyle = args.getInt(STYLE_KEY);
			mThemeId = args.getInt(THEME_ID_KEY);
			mLayoutId = args.getInt(LAYOUT_ID_KEY);
		}
		// Stupid workaround for over-zealous ResourceType warning
		switch(mStyle) {
			case STYLE_NORMAL:
				setStyle(STYLE_NORMAL, mThemeId);
				break;
			case STYLE_NO_FRAME:
				setStyle(STYLE_NO_FRAME, mThemeId);
				break;
			case STYLE_NO_INPUT:
				setStyle(STYLE_NO_INPUT, mThemeId);
				break;
			case STYLE_NO_TITLE:
				setStyle(STYLE_NO_TITLE, mThemeId);
				break;
		}

		mFragmentRootModule = getFragmentRootModule();
		mFragmentRootModule.onCreate(this);
	}

	@Override
    public View onCreateView(LayoutInflater inflater,
    						 ViewGroup container,
    						 Bundle inBundle) {
		if (DEBUG) {
			Log.d(mTag, "onCreateView()...");
		}
		View view = null;
		if (mLayoutId != 0) {
			view = inflater.inflate(mLayoutId, container, false);
		}
		if (view == null) {
			if (DEBUG) {
				Log.d(mTag, "onCreateView(): no layout...");
			}
			mFragmentRootModule.onCreateView(container);
		} else {
			mFragmentRootModule.onCreateView(view);
		}
    	return view;
	}

	@Override
    public void onActivityCreated(Bundle inBundle) {
    	super.onActivityCreated(inBundle);
		mFragmentRootModule.onActivityCreated(inBundle);
	}

	@Override
    public void onStart() {
    	super.onStart();
		mFragmentRootModule.onStart();
    }

	@Override
    public void onResume() {
    	super.onResume();
		mFragmentRootModule.onResume();
    }

	@Override
    public void onPause() {
    	super.onPause();
		mFragmentRootModule.onPause();
    }

	@Override
    public void onStop() {
    	super.onStop();
		mFragmentRootModule.onStop();
    }

	@Override
    public void onDestroyView() {
    	super.onDestroyView();
		mFragmentRootModule.onDestroyView();
    }

	@Override
    public void onDestroy() {
    	super.onDestroy();
		mFragmentRootModule.onDestroy();
    }

	@Override
    public void onSaveInstanceState(Bundle outBundle) {
    	super.onSaveInstanceState(outBundle);
		mFragmentRootModule.onSaveInstanceState(outBundle);
    }

	@Override
	public void setDialogListener(IDialogListener dialogListener) {
		mDialogListener = dialogListener;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		if (DEBUG) {
			if (mDialogListener == null) {
				logAndThrowNullError("mDialogListener");
			}
		}
		mDialogListener.onCancel(this);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		if (DEBUG) {
			if (mDialogListener == null) {
				logAndThrowNullError("mDialogListener");
			}
		}
		mDialogListener.onDismiss(this);
	}

	//	****************   C O N T R O L   C O N T E X T   *****************  //

    @Override
	public Fragment getFragment() {
		return this;
	}

	@Override
	public IRootModule getRootModule() {
		return getFragmentRootModule();
	}

	@NonNull
	@Override
	public abstract IFragmentRootModule getFragmentRootModule();

	// Conveniences

	protected void logAndThrowNullError(String errorContext) {
		logAndThrowError(errorContext + ": null reference!");
	}

	protected void logAndThrowError(String errMsg) {
		Log.e(mTag, errMsg);
		throw new Error(mTag + "." + errMsg);
	}
}
