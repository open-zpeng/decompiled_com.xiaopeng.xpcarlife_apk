package com.xiaopeng.xpcarlife.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.speech.vui.Helper.IVuiSceneHelper;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.VuiUpdateType;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xpcarlife.mvvm.IView;
import com.xiaopeng.xpcarlife.mvvm.IViewModel;
import com.xiaopeng.xpcarlife.viewmodel.BaseViewModel;
import com.xiaopeng.xui.app.XActivity;
import java.util.Collections;
import java.util.List;
/* loaded from: classes.dex */
public abstract class BaseActivity<H extends BaseViewModel> extends XActivity implements IView, IVuiSceneHelper {
    protected H mViewModel;
    protected String mVuiSceneId = getClass().getSimpleName();
    protected final String TAG = getClass().getSimpleName();

    public abstract void bindLiveData();

    protected abstract H createViewModel();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        H createViewModel = createViewModel();
        this.mViewModel = createViewModel;
        createViewModel.init();
        bindLiveData();
        VuiEngine.getInstance(this).initScene(getLifecycle(), getSceneId(), getRootView(), this, new IVuiElementChangedListener() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$BaseActivity$TEgdCx4JozszH4sbVY8_xcbkKp0
            @Override // com.xiaopeng.vui.commons.IVuiElementChangedListener
            public final void onVuiElementChaned(View view, VuiUpdateType vuiUpdateType) {
                BaseActivity.this.lambda$onCreate$0$BaseActivity(view, vuiUpdateType);
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$0$BaseActivity(View view, VuiUpdateType vuiUpdateType) {
        Log.d(this.TAG, "onVuiElementChanged vuiUpdateType：" + vuiUpdateType);
        if (VuiUpdateType.UPDATE_VIEW.equals(vuiUpdateType)) {
            VuiEngine.getInstance(this).updateScene(getSceneId(), view);
        } else {
            VuiEngine.getInstance(this).updateElementAttribute(getSceneId(), view);
        }
    }

    @Override // com.xiaopeng.xpcarlife.mvvm.IView
    public IViewModel getViewModel() {
        return this.mViewModel;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    public <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        liveData.observe(this, observer);
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        Log.d(this.TAG, "onInterceptVuiEvent ");
        return false;
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        Log.d(this.TAG, "onVuiEvent " + vuiEvent);
    }

    public View getRootView() {
        return getWindow().getDecorView();
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return this.mVuiSceneId;
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public List<View> getBuildViews() {
        return Collections.singletonList(getRootView());
    }
}
