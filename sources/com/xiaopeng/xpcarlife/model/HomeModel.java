package com.xiaopeng.xpcarlife.model;

import com.xiaopeng.xpcarlife.data.CarLifeFeatureBean;
import com.xiaopeng.xpcarlife.data.HvacChannelStatus;
import com.xiaopeng.xpcarlife.data.HvacConcentrationStatus;
import com.xiaopeng.xpcarlife.data.HvacStatus;
import com.xiaopeng.xpcarlife.data.SpcStatus;
import com.xiaopeng.xpcarlife.data.TrunkPowerStatus;
import com.xiaopeng.xpcarlife.util.CarLifeFeatureHelper;
/* loaded from: classes.dex */
public class HomeModel extends BaseModel {
    private static final String TAG = "HomeModel";
    private final CarLifeHvacController mHvacController = new CarLifeHvacController();
    private final CarLifeMcuController mCarLifeMcuController = new CarLifeMcuController();
    private final CarLifeSpcController mCarLifeSpcController = new CarLifeSpcController();

    public HvacStatus getHvacSfsState() {
        return this.mHvacController.getHvacSfsState();
    }

    public HvacConcentrationStatus getHvacSfsConcentrationState() {
        return this.mHvacController.getHvacSfsConcentrationState();
    }

    public HvacChannelStatus getHvacSfsChannel() {
        return this.mHvacController.getHvacSfsChannel();
    }

    public TrunkPowerStatus getTrunkPowerStatus() {
        return this.mCarLifeMcuController.getTrunkPowerStatusCarApi();
    }

    public TrunkPowerStatus getFridgeStatus() {
        return this.mCarLifeMcuController.getTrunkPowerStatusCarApi();
    }

    public SpcStatus getSpcStatus() {
        return this.mCarLifeSpcController.getSpcStatusCarApi();
    }

    public CarLifeFeatureBean getCarLifeFeature() {
        CarLifeFeatureBean carLifeFeatureBean = new CarLifeFeatureBean();
        carLifeFeatureBean.setSPCEnable(CarLifeFeatureHelper.isSPCFeatureEnable());
        carLifeFeatureBean.setSFSEnable(CarLifeFeatureHelper.isSFSFeatureEnable());
        carLifeFeatureBean.setSpaceCapsuleEnable(CarLifeFeatureHelper.isSpaceCapsuleFeatureEnable());
        if (CarLifeFeatureHelper.isFridgeFeatureEnableApiRouter()) {
            carLifeFeatureBean.setFridgeEnable(true);
            carLifeFeatureBean.setTrunkPowerEnable(false);
        } else {
            carLifeFeatureBean.setTrunkPowerEnable(CarLifeFeatureHelper.isTrunkPowerFeatureEnable());
        }
        return carLifeFeatureBean;
    }
}
