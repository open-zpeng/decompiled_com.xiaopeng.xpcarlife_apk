package com.xiaopeng.xpcarlife.view;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xpcarlife.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.widget.XCheckBox;
import com.xiaopeng.xui.widget.XLoading;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class WebViewDialog extends XDialog implements ComponentCallbacks {
    private static final String AGREEMENT_PRIVACY_LOCAL_URL = "file:///android_asset/agreement/aircraft_user_agreement.html";
    private static final String AGREEMENT_PRIVACY_NIGHT_LOCAL_URL = "file:///android_asset/agreement/aircraft_user_agreement.html";
    private static final String AGREEMENT_PROTOCOL_LOCAL_URL = "file:///android_asset/agreement/aircraft_user_agreement.html";
    private static final String AGREEMENT_PROTOCOL_NIGHT_LOCAL_URL = "file:///android_asset/agreement/aircraft_user_agreement_night.html";
    private static final int SHOW_PRIVACY = 1;
    private static final int SHOW_PROTOCOL = 0;
    private static final String TAG = "WebViewDialog";
    private XCheckBox mAgreeChecked;
    private final Context mContext;
    private boolean mIsDayMode;
    private XTextView mPrivacyTextView;
    private XTextView mProtocolTextView;
    private ViewGroup mRootWebView;
    private volatile int mShowState;
    private WebView mWebView;
    private final int mWebViewMargin;
    private XLoading mXLoading;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$initWebView$4(View v) {
        return true;
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    public WebViewDialog(Context context) {
        super(context, (int) R.style.XDialogView_Large_Custom);
        this.mShowState = 0;
        this.mIsDayMode = true;
        View inflate = LayoutInflater.from(context).inflate(R.layout.web_view_dialog_content_layout, (ViewGroup) null);
        setCustomView(inflate, false);
        this.mContext = context;
        this.mWebViewMargin = context.getResources().getDimensionPixelOffset(R.dimen.x_dialog_content_scrollbar_in_padding);
        init(inflate);
        initListener();
    }

    @Override // com.xiaopeng.xui.app.XDialog
    public void show() {
        super.show();
        initOnShow();
    }

    private void initOnShow() {
        Context context = this.mContext;
        if (context != null) {
            this.mIsDayMode = isDay(context.getResources().getConfiguration());
            Log.i(TAG, "initOnShow==> isDayMode:" + this.mIsDayMode);
        }
        Context context2 = this.mContext;
        if (context2 != null) {
            context2.registerComponentCallbacks(this);
        }
        if (this.mWebView == null) {
            initWebView();
        }
        renderLayoutByDayNightStatus();
        resetUrl();
    }

    private void resetUrl() {
        resetState();
        loadUrl();
    }

    private void resetState() {
        setAgreeCheck(false);
        this.mShowState = 0;
        setPositiveButtonEnable(false);
    }

    private void setAgreeCheck(boolean isCheck) {
        XCheckBox xCheckBox = this.mAgreeChecked;
        if (xCheckBox != null) {
            xCheckBox.setChecked(isCheck);
        }
    }

    public void destroy() {
        Log.d(TAG, "destroy");
        ViewGroup viewGroup = this.mRootWebView;
        if (viewGroup != null) {
            viewGroup.removeView(this.mWebView);
        }
        WebView webView = this.mWebView;
        if (webView != null) {
            webView.clearHistory();
            this.mWebView.removeAllViews();
            this.mWebView.destroy();
            this.mWebView = null;
        }
        Context context = this.mContext;
        if (context != null) {
            context.unregisterComponentCallbacks(this);
        }
        XLoading xLoading = this.mXLoading;
        if (xLoading != null) {
            xLoading.setVisibility(8);
        }
    }

    private void initListener() {
        this.mAgreeChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$WebViewDialog$S58gAwPcTSsX_qFqvvC7B5EIJF4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                WebViewDialog.this.lambda$initListener$0$WebViewDialog(compoundButton, z);
            }
        });
        this.mProtocolTextView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$WebViewDialog$zKBZaIxmBjH8lpWtOUKBznEtskM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WebViewDialog.this.lambda$initListener$1$WebViewDialog(view);
            }
        });
        this.mPrivacyTextView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$WebViewDialog$kFZYv7li75IkixqsAJfMUIq08yc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WebViewDialog.this.lambda$initListener$2$WebViewDialog(view);
            }
        });
        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$WebViewDialog$Ioa5FmuuueJZvvTm_a0bkcUPWrs
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                WebViewDialog.this.lambda$initListener$3$WebViewDialog(dialogInterface);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$WebViewDialog(CompoundButton buttonView, boolean isChecked) {
        setPositiveButtonEnable(isChecked);
    }

    public /* synthetic */ void lambda$initListener$1$WebViewDialog(View v) {
        if (this.mRootWebView.getScrollY() != 0) {
            this.mRootWebView.scrollTo(0, 0);
        } else {
            Log.d(TAG, "scroll bar position = " + this.mRootWebView.getScrollY());
        }
        if (this.mShowState == 0) {
            return;
        }
        this.mShowState = 0;
        loadUrl();
    }

    public /* synthetic */ void lambda$initListener$2$WebViewDialog(View v) {
        if (this.mShowState == 1) {
            return;
        }
        this.mShowState = 1;
        loadUrl();
    }

    public /* synthetic */ void lambda$initListener$3$WebViewDialog(DialogInterface v) {
        destroy();
    }

    private void setSelectState(boolean isSelect) {
        XTextView xTextView = this.mProtocolTextView;
        if (xTextView != null) {
            xTextView.setSelected(isSelect);
        }
        XTextView xTextView2 = this.mPrivacyTextView;
        if (xTextView2 != null) {
            xTextView2.setSelected(!isSelect);
        }
    }

    private void loadUrl() {
        boolean z = this.mIsDayMode;
        String str = AGREEMENT_PROTOCOL_NIGHT_LOCAL_URL;
        String str2 = "file:///android_asset/agreement/aircraft_user_agreement.html";
        String str3 = z ? "file:///android_asset/agreement/aircraft_user_agreement.html" : AGREEMENT_PROTOCOL_NIGHT_LOCAL_URL;
        String string = this.mContext.getString(R.string.web_view_dialog_protocol_title);
        if (this.mShowState == 0) {
            if (this.mIsDayMode) {
                str = "file:///android_asset/agreement/aircraft_user_agreement.html";
            }
            string = this.mContext.getString(R.string.web_view_dialog_protocol_title);
            setSelectState(true);
            str2 = str;
        } else if (this.mShowState == 1) {
            boolean z2 = this.mIsDayMode;
            string = this.mContext.getString(R.string.web_view_dialog_privacy_title);
            setSelectState(false);
        } else {
            setSelectState(true);
            str2 = str3;
        }
        setTitle(string);
        if (this.mWebView != null) {
            Log.i(TAG, "startLoadUrl:" + str2 + ", web=" + this.mWebView);
            this.mWebView.loadUrl(str2);
            return;
        }
        Log.e(TAG, "loadUrl error, webview is null. " + str2);
    }

    private void init(View rootView) {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.getDecorView().setSystemUiVisibility(5894);
        }
        this.mRootWebView = (ViewGroup) rootView.findViewById(R.id.web_view_parent);
        this.mPrivacyTextView = (XTextView) rootView.findViewById(R.id.agreement_privacy);
        this.mProtocolTextView = (XTextView) rootView.findViewById(R.id.agreement_protocol);
        this.mAgreeChecked = (XCheckBox) rootView.findViewById(R.id.checked_agree);
        this.mXLoading = (XLoading) rootView.findViewById(R.id.loading);
    }

    private void initWebView() {
        if (this.mRootWebView != null) {
            this.mWebView = new WebView(this.mContext);
            Log.i(TAG, "initWebView:" + this.mWebView);
            this.mXLoading.setVisibility(0);
            this.mWebView.setVisibility(8);
            this.mWebView.setHorizontalScrollBarEnabled(false);
            this.mWebView.setVerticalScrollBarEnabled(false);
            this.mWebView.setScrollbarFadingEnabled(true);
            this.mWebView.setBackgroundColor(0);
            this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.xiaopeng.xpcarlife.view.WebViewDialog.1
                @Override // android.webkit.WebViewClient
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    Log.w(WebViewDialog.TAG, "onWebViewReceivedError, " + ((Object) (error != null ? error.getDescription() : "")) + ", " + (request != null ? request.getUrl() : ""));
                }

                @Override // android.webkit.WebViewClient
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    Log.i(WebViewDialog.TAG, "onWebViewPageFinished==> url:" + url + ", " + view);
                    if (WebViewDialog.this.mRootWebView != null) {
                        WebViewDialog.this.mRootWebView.scrollTo(0, 0);
                    }
                    WebViewDialog.this.mXLoading.setVisibility(8);
                    WebViewDialog.this.mWebView.setVisibility(0);
                }
            });
            this.mWebView.setLongClickable(true);
            this.mWebView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$WebViewDialog$sFVnzca8esCsoJOPFsBvxC7HXss
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return WebViewDialog.lambda$initWebView$4(view);
                }
            });
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-1, -2);
            marginLayoutParams.setMarginStart(this.mWebViewMargin);
            marginLayoutParams.setMarginEnd(this.mWebViewMargin);
            this.mRootWebView.addView(this.mWebView, marginLayoutParams);
        }
    }

    @Override // com.xiaopeng.xui.app.XDialog
    public boolean isShowing() {
        return getDialog().isShowing();
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        if (ThemeManager.isThemeChanged(newConfig)) {
            boolean isDay = isDay(newConfig);
            this.mIsDayMode = isDay;
            Log.i(TAG, "onConfigurationChanged==> isDayMode:" + isDay);
            if (isShowing()) {
                renderLayoutByDayNightStatus();
                loadUrl();
            }
        }
    }

    public void renderLayoutByDayNightStatus() {
        XTextView xTextView = this.mPrivacyTextView;
        if (xTextView != null) {
            xTextView.setTextColor(this.mContext.getColorStateList(R.color.webview_dialog_button_selector_color));
        }
        XTextView xTextView2 = this.mProtocolTextView;
        if (xTextView2 != null) {
            xTextView2.setTextColor(this.mContext.getColorStateList(R.color.webview_dialog_button_selector_color));
        }
        XCheckBox xCheckBox = this.mAgreeChecked;
        if (xCheckBox != null) {
            xCheckBox.setTextColor(this.mContext.getColor(R.color.x_theme_text_02));
        }
    }

    private boolean isDay(Configuration configuration) {
        return (configuration.uiMode & 48) == 16;
    }
}
