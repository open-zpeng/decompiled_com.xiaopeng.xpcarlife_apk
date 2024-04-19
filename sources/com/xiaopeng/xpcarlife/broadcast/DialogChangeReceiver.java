package com.xiaopeng.xpcarlife.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/* loaded from: classes.dex */
public class DialogChangeReceiver extends BroadcastReceiver {
    public static final String ACTION_DIALOG_CHANGED = "com.xiaopeng.intent.action.XUI_DIALOG_CHANGED";
    private static final String EXTRA_HAS_VISIBLE_DIALOG = "android.intent.extra.HAS_VISIBLE_DIALOG";
    private static final String TAG = "DialogChangeReceiver";
    IDialogChangeListener mDialogChangeListener;
    boolean mHasShownDialog;

    public DialogChangeReceiver(IDialogChangeListener dialogChangeListener) {
        this.mDialogChangeListener = dialogChangeListener;
    }

    public void setDialogChangeListener(IDialogChangeListener dialogChangeListener) {
        this.mDialogChangeListener = dialogChangeListener;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (ACTION_DIALOG_CHANGED.equals(intent.getAction())) {
            boolean booleanExtra = intent.getBooleanExtra(EXTRA_HAS_VISIBLE_DIALOG, false);
            Log.i(TAG, "EXTRA_HAS_VISIBLE_DIALOG:" + booleanExtra);
            if (booleanExtra == this.mHasShownDialog) {
                return;
            }
            this.mHasShownDialog = booleanExtra;
            IDialogChangeListener iDialogChangeListener = this.mDialogChangeListener;
            if (iDialogChangeListener != null) {
                if (booleanExtra) {
                    iDialogChangeListener.onDialogShow();
                } else {
                    iDialogChangeListener.onDialogHidden();
                }
            }
        }
    }
}
