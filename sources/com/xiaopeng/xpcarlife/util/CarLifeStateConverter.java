package com.xiaopeng.xpcarlife.util;

import android.util.Log;
import com.xiaopeng.xpcarlife.data.FridgeStatus;
import com.xiaopeng.xpcarlife.data.GearLevel;
import com.xiaopeng.xpcarlife.data.HvacChannelStatus;
import com.xiaopeng.xpcarlife.data.HvacConcentrationStatus;
import com.xiaopeng.xpcarlife.data.HvacStatus;
import com.xiaopeng.xpcarlife.data.SpcStatus;
import com.xiaopeng.xpcarlife.data.TrunkPowerStatus;
/* loaded from: classes.dex */
public class CarLifeStateConverter {
    private static final int FRIDGE_ERROR = -1;
    public static final int FRIDGE_OFF = 0;
    private static final int FRIDGE_ON = 1;
    private static final int FRIDGE_WORKING = 2;
    private static final String TAG = "CarLifeStateConverter";

    public static HvacStatus switchStateConvert(int carState) {
        HvacStatus hvacStatus = HvacStatus.HVAC_STATUS_OFF;
        if (carState == 0) {
            hvacStatus = HvacStatus.HVAC_STATUS_OFF;
        } else if (carState == 1) {
            hvacStatus = HvacStatus.HVAC_STATUS_ON;
        } else if (carState == 2) {
            hvacStatus = HvacStatus.HVAC_STATUS_ERROR;
        }
        Log.d(TAG, "Karl log about switchStateConvert carState = " + carState + "    hvacStatus = " + hvacStatus);
        return hvacStatus;
    }

    public static HvacChannelStatus channelStateConvert(int concentrationState) {
        HvacChannelStatus hvacChannelStatus = HvacChannelStatus.HVAC_CONCENTRATION_CHANNEL_1;
        if (concentrationState == 0) {
            hvacChannelStatus = HvacChannelStatus.HVAC_CONCENTRATION_CHANNEL_1;
        } else if (concentrationState == 1) {
            hvacChannelStatus = HvacChannelStatus.HVAC_CONCENTRATION_CHANNEL_2;
        } else if (concentrationState == 2) {
            hvacChannelStatus = HvacChannelStatus.HVAC_CONCENTRATION__CHANNEL_3;
        }
        Log.d(TAG, "Karl log about channelStateConvert concentrationState = " + concentrationState + "    hvacChannelStatus = " + hvacChannelStatus);
        return hvacChannelStatus;
    }

    public static HvacConcentrationStatus concentrationStateConvert(int concentrationState) {
        HvacConcentrationStatus hvacConcentrationStatus = HvacConcentrationStatus.HVAC_CONCENTRATION_CLOSED;
        if (concentrationState == 0) {
            hvacConcentrationStatus = HvacConcentrationStatus.HVAC_CONCENTRATION_CLOSED;
        } else if (concentrationState == 1) {
            hvacConcentrationStatus = HvacConcentrationStatus.HVAC_CONCENTRATION_LOW;
        } else if (concentrationState == 2) {
            hvacConcentrationStatus = HvacConcentrationStatus.HVAC_CONCENTRATION_HIGH;
        }
        Log.d(TAG, "Karl log about concentrationStateConvert concentrationState = " + concentrationState + "    hvacChannelStatus = " + hvacConcentrationStatus);
        return hvacConcentrationStatus;
    }

    public static TrunkPowerStatus trunkPowerStatusConvert(int trunkPowerState) {
        TrunkPowerStatus trunkPowerStatus = TrunkPowerStatus.TRUNK_POWER_STATUS_OFF;
        if (trunkPowerState == 0) {
            trunkPowerStatus = TrunkPowerStatus.TRUNK_POWER_STATUS_OFF;
        } else if (trunkPowerState == 1) {
            trunkPowerStatus = TrunkPowerStatus.TRUNK_POWER_STATUS_ON;
        } else if (trunkPowerState == 2) {
            trunkPowerStatus = TrunkPowerStatus.TRUNK_POWER_STATUS_WORKING;
        } else if (trunkPowerState == 3) {
            trunkPowerStatus = TrunkPowerStatus.TRUNK_POWER_STATUS_FAULT;
        }
        Log.d(TAG, "Karl log about trunkPowerStatusConvert trunkPowerState = " + trunkPowerState + "    trunkPowerStatus = " + trunkPowerStatus);
        return trunkPowerStatus;
    }

    public static SpcStatus spcStatusConvert(int spcState) {
        SpcStatus spcStatus = SpcStatus.SPC_STATUS_OFF;
        if (spcState == 0) {
            spcStatus = SpcStatus.SPC_STATUS_OFF;
        } else if (spcState == 1) {
            spcStatus = SpcStatus.SPC_STATUS_ON;
        }
        Log.d(TAG, "Karl log about spcStatusConvert spcState = " + spcState + "    spcStatus = " + spcStatus);
        return spcStatus;
    }

    public static FridgeStatus fridgeStatusConvert(int fridgeState) {
        FridgeStatus fridgeStatus = FridgeStatus.FRIDGE_STATUS_OFF;
        if (fridgeState == 0) {
            fridgeStatus = FridgeStatus.FRIDGE_STATUS_OFF;
        } else if (fridgeState == 1) {
            fridgeStatus = FridgeStatus.FRIDGE_STATUS_ON;
        } else if (fridgeState == 2) {
            fridgeStatus = FridgeStatus.FRIDGE_STATUS_WORKING;
        } else if (fridgeState == 3) {
            fridgeStatus = FridgeStatus.FRIDGE_STATUS_FAULT;
        }
        Log.d(TAG, "Karl log about fridgeStatusConvert fridgeState = " + fridgeState + "    fridgeStatus = " + fridgeStatus);
        return fridgeStatus;
    }

    public static GearLevel gearLevelConvert(int vcuGearLevel) {
        GearLevel gearLevel = GearLevel.GEAR_LEVEL_INVALID;
        if (vcuGearLevel == 0) {
            gearLevel = GearLevel.GEAR_LEVEL_INVALID;
        } else if (vcuGearLevel == 1) {
            gearLevel = GearLevel.GEAR_LEVEL_D;
        } else if (vcuGearLevel == 2) {
            gearLevel = GearLevel.GEAR_LEVEL_N;
        } else if (vcuGearLevel == 3) {
            gearLevel = GearLevel.GEAR_LEVEL_R;
        } else if (vcuGearLevel == 4) {
            gearLevel = GearLevel.GEAR_LEVEL_P;
        }
        Log.d(TAG, "Karl log about gearLevelConvert vcuGearLevel = " + vcuGearLevel + "    gearLevel = " + gearLevel);
        return gearLevel;
    }
}
