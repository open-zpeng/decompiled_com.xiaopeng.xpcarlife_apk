package com.xiaopeng.xpcarlife.util;

import android.net.Uri;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.xpcarlife.data.CarLifeConstants;
/* loaded from: classes.dex */
public class CarLifeFeatureHelper {
    private static final int CFC_CODE_INVALID = 0;
    private static final String FEATURE_INVALID = "0";
    private static final String FEATURE_VALID = "1";
    private static final String SYSTEM_PROPERTY_CFC_VEHICLE_LEVEL = "persist.sys.xiaopeng.cfcVehicleLevel";
    private static final String SYSTEM_PROPERTY_PACKAGE_1 = "persist.sys.xiaopeng.Package1";
    private static final String SYSTEM_PROPERTY_SFS = "persist.sys.xiaopeng.SFS";
    private static final String SYSTEM_PROPERTY_SPC = "persist.sys.xiaopeng.SPC";
    private static final String TAG = "CarLifeFeatureHelper";
    private static final int TRUNK_POWER_MINI_VEHICLE_LEVEL = 2;

    public static boolean isTrunkPowerFeatureEnable() {
        String str = SystemProperties.get(SYSTEM_PROPERTY_CFC_VEHICLE_LEVEL, FEATURE_INVALID);
        Log.d(TAG, "isTrunkPowerFeatureEnable = " + str);
        return parseCfcProperty(str) >= 2;
    }

    public static boolean isSPCFeatureEnable() {
        String str = SystemProperties.get(SYSTEM_PROPERTY_SPC, FEATURE_INVALID);
        Log.d(TAG, "isSPCFeatureEnable = " + str);
        return featureEnabled(str);
    }

    public static boolean isSPCFeatureEnableApiRouter() {
        try {
            return ((Boolean) ApiRouter.route(new Uri.Builder().authority(CarLifeConstants.CHARGE_CONTROL_OPEN_API_SERVICE).path("hasSolarRoof").build())).booleanValue();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isFridgeFeatureEnableApiRouter() {
        try {
            return ((Boolean) ApiRouter.route(new Uri.Builder().authority(CarLifeConstants.CHARGE_CONTROL_OPEN_API_SERVICE).path("hasFridge").build())).booleanValue();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSFSFeatureEnable() {
        String str = SystemProperties.get(SYSTEM_PROPERTY_SFS, FEATURE_INVALID);
        Log.d(TAG, "isSFSFeatureEnable = " + str);
        return featureEnabled(str);
    }

    public static boolean isSpaceCapsuleFeatureEnable() {
        String str = SystemProperties.get(SYSTEM_PROPERTY_PACKAGE_1, FEATURE_INVALID);
        Log.d(TAG, "isSpaceCapsuleFeatureEnable = " + str);
        return featureEnabled(str);
    }

    private static boolean featureEnabled(String property) {
        return "1".equals(property);
    }

    private static int parseCfcProperty(String property) {
        if (!TextUtils.isEmpty(property)) {
            try {
            } catch (Exception unused) {
                return 0;
            }
        }
        return Integer.parseInt(property);
    }
}
