package com.xiaopeng.xpcarlife.data;
/* loaded from: classes.dex */
public class IntroduceEntity {
    public static final int TYPE_CONTENT = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_TITLE = 0;
    private final int mResourceID;
    private final int mType;

    public IntroduceEntity(int resourceID, int type) {
        this.mResourceID = resourceID;
        this.mType = type;
    }

    public int getResourceID() {
        return this.mResourceID;
    }

    public int getType() {
        return this.mType;
    }
}
