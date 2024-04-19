package com.xiaopeng.xpcarlife.eventtracking;
/* loaded from: classes.dex */
public enum EventEnum {
    OPEN_TRUNK_POWER("B001", "打开12V电源页面", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_SOURCE}),
    OPEN_SOLAR_ROOF("B001", "打开太阳能车顶页面", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_SOURCE}),
    OPEN_PLAY_SLEEP_SPACE("B002", "百变智能空间打开观影/睡眠空间", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_TYPE, EventTrackingParamKeys.KEY_RESULT}),
    OPEN_HIGHLIGHT_INTRODUCE("B001", "点击亮点介绍", new EventTrackingParamKeys[]{EventTrackingParamKeys.KEY_RESULT});
    
    private String mEventId;
    private String mEventName;
    private int mLevel;
    private EventTrackingParamKeys[] mParamsKeys;

    EventEnum(String eventId, String eventName, int level) {
        this.mEventId = eventId;
        this.mEventName = eventName;
        this.mLevel = level;
        this.mParamsKeys = null;
    }

    EventEnum(String eventId, String eventName, EventTrackingParamKeys[] paramsIndex) {
        this.mEventId = eventId;
        this.mEventName = eventName;
        this.mLevel = 0;
        this.mParamsKeys = paramsIndex;
    }

    EventEnum(String eventId, String eventName) {
        this.mEventId = eventId;
        this.mEventName = eventName;
        this.mLevel = 0;
        this.mParamsKeys = null;
    }

    EventEnum(String eventId, String eventName, int level, EventTrackingParamKeys[] paramsIndex) {
        this.mEventId = eventId;
        this.mEventName = eventName;
        this.mLevel = level;
        this.mParamsKeys = paramsIndex;
    }

    public String getEventId() {
        return this.mEventId;
    }

    public int getLevel() {
        return this.mLevel;
    }

    public EventTrackingParamKeys[] getParamsIndex() {
        return this.mParamsKeys;
    }
}
