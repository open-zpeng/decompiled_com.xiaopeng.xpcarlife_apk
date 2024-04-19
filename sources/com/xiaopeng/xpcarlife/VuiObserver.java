package com.xiaopeng.xpcarlife;

import android.util.Log;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.xui.Xui;
/* loaded from: classes.dex */
public class VuiObserver implements IServicePublisher {
    private final String TAG = VuiObserver.class.getSimpleName();

    public void onEvent(String event, String data) {
        Log.d(this.TAG, "消息接收 event== " + event + " ,data== " + data);
        VuiEngine.getInstance(Xui.getContext()).dispatchVuiEvent(event, data);
    }

    public String getElementState(String sceneId, String elementId) {
        Log.d(this.TAG, "getElementState elementId== " + elementId);
        return VuiEngine.getInstance(Xui.getContext()).getElementState(sceneId, elementId);
    }
}
