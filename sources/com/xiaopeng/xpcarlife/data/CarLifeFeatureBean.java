package com.xiaopeng.xpcarlife.data;
/* loaded from: classes.dex */
public class CarLifeFeatureBean {
    private boolean isSPCEnable = false;
    private boolean isSFSEnable = false;
    private boolean isSpaceCapsuleEnable = false;
    private boolean isFridgeEnable = false;
    private boolean isTrunkPowerEnable = false;

    public boolean allDisable() {
        return (isSFSEnable() || isTrunkPowerEnable() || isSPCEnable() || isFridgeEnable()) ? false : true;
    }

    public boolean isTrunkPowerEnable() {
        return this.isTrunkPowerEnable;
    }

    public void setTrunkPowerEnable(boolean trunkPowerEnable) {
        this.isTrunkPowerEnable = trunkPowerEnable;
    }

    public boolean isSPCEnable() {
        return this.isSPCEnable;
    }

    public void setSPCEnable(boolean SPCEnable) {
        this.isSPCEnable = SPCEnable;
    }

    public boolean isSFSEnable() {
        return this.isSFSEnable;
    }

    public void setSFSEnable(boolean SFSEnable) {
        this.isSFSEnable = SFSEnable;
    }

    public boolean isSpaceCapsuleEnable() {
        return this.isSpaceCapsuleEnable;
    }

    public void setSpaceCapsuleEnable(boolean spaceCapsuleEnable) {
        this.isSpaceCapsuleEnable = spaceCapsuleEnable;
    }

    public boolean isFridgeEnable() {
        return this.isFridgeEnable;
    }

    public void setFridgeEnable(boolean fridgeEnable) {
        this.isFridgeEnable = fridgeEnable;
    }
}
