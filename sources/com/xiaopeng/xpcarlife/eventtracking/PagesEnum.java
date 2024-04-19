package com.xiaopeng.xpcarlife.eventtracking;
/* loaded from: classes.dex */
public enum PagesEnum {
    CAR_LIFE_TRUNK_POWER("P10136", "12V电源"),
    CAR_LIFE_SOLAR_ROOF("P10137", "太阳能车顶"),
    CAR_LIFE_VERSATILE_SPACE("P10133", "百变智能空间");
    
    private String mPageId;
    private String mPageName;

    PagesEnum(String pageId, String pageName) {
        this.mPageId = pageId;
        this.mPageName = pageName;
    }

    public String getPageId() {
        return this.mPageId;
    }

    public String getPageName() {
        return this.mPageName;
    }
}
