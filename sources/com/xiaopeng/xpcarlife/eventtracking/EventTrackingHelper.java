package com.xiaopeng.xpcarlife.eventtracking;

import android.util.Log;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEvent;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import com.xiaopeng.xpcarlife.eventtracking.EventBean;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes.dex */
public class EventTrackingHelper {
    public static final String OPEN_FROM_SMART_SPACE = "1";
    public static final String OPEN_INTRODUCE_FAILED = "2";
    public static final String OPEN_INTRODUCE_SUCCESS = "1";
    public static final String OPEN_MODE_FAILED_NOT_P = "2";
    public static final String OPEN_MODE_SUCCESS = "1";
    public static final String OPEN_PLAY_MODE = "2";
    public static final String OPEN_SLEEP_MODE = "1";
    private static final String TAG = "EventTrackingHelper";
    private static final String TRACKING_MODULE_NAME = "carlife";

    public static void sendMolecast(PagesEnum page, EventEnum btn, Object... args) {
        HashMap hashMap = new HashMap();
        EventTrackingParamKeys[] paramsIndex = btn.getParamsIndex();
        if (paramsIndex != null && args != null) {
            if (paramsIndex.length != args.length) {
                Log.i(TAG, " params key and value number not match!");
                return;
            }
            for (int i = 0; i < args.length; i++) {
                Object obj = args[i];
                if (!(obj instanceof Boolean) && !(obj instanceof Character) && !(obj instanceof Number) && !(obj instanceof String)) {
                    Log.i(TAG, "args type must be Boolean, Character, Number, String");
                    return;
                }
                hashMap.put(paramsIndex[i].getParamKey(), obj);
            }
        }
        EventBean.Builder buttonId = new EventBean.Builder().pageId(page.getPageId()).pageName(page.getPageName()).buttonId(btn.getEventId());
        if (hashMap.size() > 0) {
            buttonId.setParams(hashMap);
        }
        sendMolecast(buttonId.build());
    }

    private static IDataLog getDataLog() {
        return (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
    }

    private static void sendMolecast(EventBean data) {
        Log.i(TAG, "send molecast event bean = " + data.toString());
        IDataLog dataLog = getDataLog();
        IMoleEventBuilder buttonId = dataLog.buildMoleEvent().setModule(TRACKING_MODULE_NAME).setPageId(data.getPageId()).setButtonId(data.getButtonId());
        if (data.getParams() != null) {
            for (Map.Entry<String, Object> entry : data.getParams().entrySet()) {
                if (entry.getValue() instanceof String) {
                    buttonId.setProperty(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Number) {
                    buttonId.setProperty(entry.getKey(), (Number) entry.getValue());
                } else if (entry.getValue() instanceof Character) {
                    buttonId.setProperty(entry.getKey(), ((Character) entry.getValue()).charValue());
                } else if (entry.getValue() instanceof Boolean) {
                    buttonId.setProperty(entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
                } else {
                    Log.i(TAG, "params type invalid");
                }
            }
        }
        buttonId.setProperty("level", Integer.valueOf(data.getLevel()));
        if (dataLog != null) {
            IMoleEvent build = buttonId.build();
            Log.i(TAG, "send molecast mole event = " + build.toJson());
            dataLog.sendStatData(build);
        }
    }
}
