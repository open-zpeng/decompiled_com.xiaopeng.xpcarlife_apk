package com.xiaopeng.xpcarlife.model;

import android.net.Uri;
import android.util.Log;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.xpcarlife.carapi.CarClientWrapper;
import com.xiaopeng.xpcarlife.data.CarLifeConstants;
import com.xiaopeng.xpcarlife.data.SpcStatus;
import com.xiaopeng.xpcarlife.util.CarLifeStateConverter;
/* loaded from: classes.dex */
public class CarLifeSpcController {
    private static final String TAG = "CarLifeSpcController";

    public SpcStatus getSpcStatusCarApi() {
        int i;
        try {
            i = CarClientWrapper.getInstance().getCarSpcManager().getSolarWorkSt();
        } catch (Exception e) {
            Log.d(TAG, "getSpcStatus error, error = " + e);
            e.printStackTrace();
            i = 0;
        }
        Log.d(TAG, "Karl log about getSpcStatusCarApi spcStatus = " + i);
        return CarLifeStateConverter.spcStatusConvert(i);
    }

    public SpcStatus getSpcStatusApiRouter() {
        int i;
        try {
            i = ((Integer) ApiRouter.route(new Uri.Builder().authority(CarLifeConstants.CHARGE_CONTROL_OPEN_API_SERVICE).path("getSolarWorkSt").build())).intValue();
        } catch (Exception e) {
            Log.d(TAG, "getSpcStatus error, error = " + e);
            e.printStackTrace();
            i = 0;
        }
        Log.d(TAG, "Karl log about getSpcStatusApiRouter spcStatus = " + i);
        return CarLifeStateConverter.spcStatusConvert(i);
    }
}
