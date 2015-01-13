package rpicolet.mvc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Abstract DialogFragment
 * 
 * @author Randy Picolet
 */
public abstract class ADialogFragment extends DialogFragment 
							       implements IDialogFragment{

	//	***********   A C T I V I T Y   I N T E G R A T I O N   ************  //

	// Logging
	protected final static boolean DEBUG = Debug.getEnabled();
    protected final String mTag = this.getClass().getSimpleName();
    
    protected Dialog mDialog;
    protected View mContentView;
	protected IDialogFragmentListener mListener;

	private int 
			// Basic dialog
			mLayoutId,
			// Alert dialog
			mTitleTextId, 
			mContentLayoutId, 
			mNegativeTextId, 
			mNeutralTextId, 
			mPositiveTextId;
	
	public static Bundle initArgs(int layoutId) {
    	Bundle args = new Bundle();
    	args.putInt(LAYOUT_ID_KEY, layoutId);
    	return args;
	}
	public static Bundle initAlertArgs(int titleTextId, int contentLayoutId,
			int negativeTextId, int neutralTextId, int positiveTextId) {
    	Bundle args = new Bundle();
    	args.putInt(TITLE_TEXT_ID_KEY, titleTextId);
    	args.putInt(CONTENT_LAYOUT_ID_KEY, contentLayoutId);
    	args.putInt(NEGATIVE_TEXT_ID_KEY, negativeTextId);
    	args.putInt(NEUTRAL_TEXT_ID_KEY, neutralTextId);
    	args.putInt(POSITIVE_TEXT_ID_KEY, positiveTextId);
    	return args;
	}
	@Override
	public void onAttach(Activity activity) {
		if (DEBUG)
			Log.d(mTag, "onAttach()...");
		super.onAttach(activity);
	}
	@Override
    public void onCreate(Bundle inBundle) {
		super.onCreate(inBundle);
		// Init default args
		mLayoutId = 0;
		mTitleTextId = 0;
		mContentLayoutId = 0;
		mNegativeTextId = 0;
		mNeutralTextId = 0;
		mPositiveTextId = 0;
		Bundle args = getArguments();
		if (args != null) {
			mLayoutId = args.getInt(LAYOUT_ID_KEY);
			mTitleTextId = args.getInt(TITLE_TEXT_ID_KEY);
			mContentLayoutId = args.getInt(CONTENT_LAYOUT_ID_KEY);
			mNegativeTextId = args.getInt(NEGATIVE_TEXT_ID_KEY);
			mNeutralTextId = args.getInt(NEUTRAL_TEXT_ID_KEY);
			mPositiveTextId = args.getInt(POSITIVE_TEXT_ID_KEY);
		}
		getControlModule().onCreate(this);
	}
	@Override
	public Dialog onCreateDialog(Bundle inBundle) {
		if (mContentLayoutId == 0) {
			// Not using the AlertDialog
			setStyle(STYLE_NO_FRAME, 0);
			mDialog = new Dialog(getActivity(), 
					android.R.style.Theme_Holo_Dialog_NoActionBar);
			return mDialog;
		}
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    mContentView = inflater.inflate(mContentLayoutId, null);
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setView(mContentView);
	    if (mTitleTextId != 0) 
	    	builder.setTitle(mTitleTextId);
	    if (mNegativeTextId != 0) 
	    	builder.setNegativeButton(mNegativeTextId, 
	    			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					mDialog.cancel();
				}
			});
	    if (mNeutralTextId != 0)
	    	builder.setNeutralButton(mNeutralTextId, 
	    			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					getDialogControlModule().onNeutralClick();
				}
			});
	    if (mPositiveTextId == 0)
	    	logAndThrowError("No positiveTextId provided!");
    	builder.setPositiveButton(mPositiveTextId, 
    			new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				getDialogControlModule().onPositiveClick();
			}
		});
	    mDialog = builder.create();
	    return mDialog;
	}
	@Override
    public View onCreateView(LayoutInflater inflater, 
    						 ViewGroup container, 
    						 Bundle inBundle) {
		if (DEBUG) 
			Log.d(mTag, "onCreateView()...");
		View view = null;
		if (mLayoutId != 0)
				view = inflater.inflate(mLayoutId, container, false);
		if (view == null) {
			if (DEBUG) 
				Log.d(mTag, "onCreateView(): no layout...");
			getControlModule().onCreateView(container);
		} else
			getControlModule().onCreateView(view);
    	return view;
	}
	@Override
    public void onActivityCreated(Bundle inBundle) {
    	super.onActivityCreated(inBundle);
		getControlModule().onActivityCreated(inBundle);
	}
    @Override
    public void onStart() {
    	super.onStart();
    	getControlModule().onStart();
    }	
    @Override
    public void onResume() {
    	super.onResume();
    	getControlModule().onResume();
    }	
    @Override
    public void onPause() {
    	super.onPause();
    	getControlModule().onPause();
    }	
    @Override
    public void onStop() {
    	super.onStop();
    	getControlModule().onStop();
    }	
    @Override
    public void onDestroyView() {
    	super.onDestroyView();
    	getControlModule().onDestroyView();
    }	
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	getControlModule().onDestroy();
    }	
	@Override
	public void onDetach() {
		if (DEBUG)
			Log.d(mTag, "onDetach()...");
		super.onDetach();
	}
    @Override
    public void onSaveInstanceState(Bundle outBundle) {
    	super.onSaveInstanceState(outBundle);
    	getControlModule().onSaveInstanceState(outBundle);
    }	
	
	@Override
	public IControlModule getControlModule() {
		return getDialogControlModule();
	}
	@Override
	public void onDismiss(DialogInterface dialog) {
		if (DEBUG) 
			if (dialog != mDialog)
				logAndThrowError("wrong dialog!");
		if (mListener != null)
			mListener.onDismiss(this);
	}
	public void setListener(IDialogFragmentListener listener) {
		mListener = listener;
	}

	// Call from child's onCreate() to build a standard AlertDialog
	protected final void initDialogIds(int titleTextId, int contentLayoutId, 
			int negativeTextId, int neutralTextId, int positiveTextId) {
		mTitleTextId = titleTextId;
		mContentLayoutId = contentLayoutId;
		mNegativeTextId = negativeTextId;
		mNeutralTextId = neutralTextId;
		mPositiveTextId = positiveTextId;
	}
	protected void logAndThrowNullError(String errorContext) {
		logAndThrowError(errorContext + ": null reference!");
	}
	protected void logAndThrowError(String errMsg) {
		Log.e(mTag, errMsg);
		throw new Error(mTag + "." + errMsg);
	}
}