package com.xiaopeng.xui.widget.dialogview;
/* loaded from: classes.dex */
public interface XDialogViewInterface {
    public static final int BUTTON_NEGATIVE = -2;
    public static final int BUTTON_POSITIVE = -1;

    /* loaded from: classes.dex */
    public interface IScreenPositionCallback {
        int getScreenPositionInfo();
    }

    /* loaded from: classes.dex */
    public interface OnClickListener {
        void onClick(XDialogView xDialogView, int i);
    }

    /* loaded from: classes.dex */
    public interface OnCloseListener {
        boolean onClose(XDialogView xDialogView);
    }

    /* loaded from: classes.dex */
    public interface OnCountDownListener {
        boolean onCountDown(XDialogView xDialogView, int i);
    }

    /* loaded from: classes.dex */
    public interface OnDismissListener {
        void onDismiss(XDialogView xDialogView);
    }
}
