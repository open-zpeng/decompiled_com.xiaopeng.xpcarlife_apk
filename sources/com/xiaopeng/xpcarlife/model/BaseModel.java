package com.xiaopeng.xpcarlife.model;

import com.xiaopeng.xpcarlife.data.GearLevel;
import com.xiaopeng.xpcarlife.mvvm.IModel;
/* loaded from: classes.dex */
public abstract class BaseModel implements IModel {
    private final CarLifeVcuController mCarLifeVcuController = new CarLifeVcuController();

    public GearLevel getGearLevel() {
        return this.mCarLifeVcuController.getGearLevel();
    }
}
