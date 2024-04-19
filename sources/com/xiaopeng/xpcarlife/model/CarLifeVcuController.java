package com.xiaopeng.xpcarlife.model;

import android.util.Log;
import com.xiaopeng.xpcarlife.carapi.CarClientWrapper;
import com.xiaopeng.xpcarlife.data.GearLevel;
import com.xiaopeng.xpcarlife.util.CarLifeStateConverter;
/* loaded from: classes.dex */
public class CarLifeVcuController {
    private static final String TAG = "CarLifeVcuController";

    public GearLevel getGearLevel() {
        int i = 0;
        try {
            i = CarClientWrapper.getInstance().getCarVcuManager().getDisplayGearLevel();
            Log.d(TAG, "getGearLevel success, gearLevel = " + i);
        } catch (Exception e) {
            Log.d(TAG, "getGearLevel error, error = " + e);
            e.printStackTrace();
        }
        Log.d(TAG, "Karl log about getGearLevel gearLevel = " + i);
        return CarLifeStateConverter.gearLevelConvert(i);
    }
}
