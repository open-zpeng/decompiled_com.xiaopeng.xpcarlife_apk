package com.xiaopeng.xpcarlife.eventtracking;

import com.xiaopeng.lib.apirouter.ClientConstants;
import com.xiaopeng.speech.vui.constants.VuiConstants;
/* loaded from: classes.dex */
public enum EventTrackingParamKeys {
    KEY_TYPE(0, VuiConstants.ELEMENT_TYPE),
    KEY_RESULT(1, "result"),
    KEY_NAME(2, ClientConstants.ALIAS.P_NAME),
    KEY_COUNT(3, "count"),
    KEY_SOURCE(4, "source");
    
    public static final int INVALID_INDEX = -1;
    private int mIndex;
    private String mParamKey;

    EventTrackingParamKeys(int index, String paramKey) {
        this.mIndex = index;
        this.mParamKey = paramKey;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public String getParamKey() {
        return this.mParamKey;
    }

    public static int getIndex(EventTrackingParamKeys key) {
        for (int i = 0; i < values().length; i++) {
            EventTrackingParamKeys eventTrackingParamKeys = values()[i];
            if (eventTrackingParamKeys.getIndex() == key.getIndex() && eventTrackingParamKeys.getParamKey().equals(key.getParamKey())) {
                return i;
            }
        }
        return -1;
    }
}
