package com.xiaopeng.xpcarlife.model;

import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.xpcarlife.carapi.CarClientWrapper;
import com.xiaopeng.xpcarlife.data.CarLifeConstants;
import com.xiaopeng.xpcarlife.data.FridgeStatus;
import com.xiaopeng.xpcarlife.data.TrunkPowerStatus;
import com.xiaopeng.xpcarlife.util.CarLifeStateConverter;
/* loaded from: classes.dex */
public class CarLifeMcuController {
    private static final String TAG = "CarLifeMcuController";

    public TrunkPowerStatus getTrunkPowerStatusCarApi() {
        int i;
        try {
            i = CarClientWrapper.getInstance().getCarMcuManager().getTrunkPowerStatus();
        } catch (Exception e) {
            Log.d(TAG, "getTrunkPowerStatus error, error = " + e);
            e.printStackTrace();
            i = 0;
        }
        Log.d(TAG, "Karl log about getTrunkPowerStatusCarApi trunkPowerStatus = " + i);
        return CarLifeStateConverter.trunkPowerStatusConvert(i);
    }

    public TrunkPowerStatus getTrunkPowerStatusApiRouter() {
        int i;
        try {
            i = ((Integer) ApiRouter.route(new Uri.Builder().authority(CarLifeConstants.CHARGE_CONTROL_OPEN_API_SERVICE).path("getTrunkPowerSwitchStatus").build())).intValue();
        } catch (RemoteException e) {
            Log.d(TAG, "getTrunkPowerStatus error, error = " + e);
            e.printStackTrace();
            i = 0;
        }
        Log.d(TAG, "Karl log about getTrunkPowerStatusApiRouter trunkPowerStatus = " + i);
        return CarLifeStateConverter.trunkPowerStatusConvert(i);
    }

    public FridgeStatus getFridgeSwitchStatusApiRouter() {
        int i;
        try {
            i = ((Integer) ApiRouter.route(new Uri.Builder().authority(CarLifeConstants.CHARGE_CONTROL_OPEN_API_SERVICE).path("getFridgeSwitchStatus").build())).intValue();
        } catch (RemoteException e) {
            Log.d(TAG, "getTrunkPowerStatus error, error = " + e);
            e.printStackTrace();
            i = 0;
        }
        Log.d(TAG, "Karl log about getFridgeSwitchStatusApiRouter fridgeStatus = " + i);
        return CarLifeStateConverter.fridgeStatusConvert(i);
    }
}
