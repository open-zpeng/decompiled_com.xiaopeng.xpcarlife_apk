package com.xiaopeng.xpcarlife;

import android.app.Application;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.xpcarlife.carapi.CarClientWrapper;
import com.xiaopeng.xpcarlife.carapi.XuiClientWrapper;
import com.xiaopeng.xui.Xui;
/* loaded from: classes.dex */
public class CarLifeApplication extends Application {
    private static CarLifeApplication sApplication;

    public static CarLifeApplication getInstance() {
        return sApplication;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        Module.register(DataLogModuleEntry.class, new DataLogModuleEntry(this));
        initVui();
        CarClientWrapper.getInstance().connectToCar();
        XuiClientWrapper.getInstance().connectToXuiService();
    }

    private void initVui() {
        VuiEngine.getInstance(this).subscribe(VuiObserver.class.getName());
        VuiEngine.getInstance(this).setLoglevel(LogUtils.LOG_DEBUG_LEVEL);
        Xui.init(this);
        Xui.setVuiEnable(true);
    }
}
