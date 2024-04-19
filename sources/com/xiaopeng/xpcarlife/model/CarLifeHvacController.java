package com.xiaopeng.xpcarlife.model;

import android.util.Log;
import com.xiaopeng.xpcarlife.carapi.CarClientWrapper;
import com.xiaopeng.xpcarlife.data.HvacChannelStatus;
import com.xiaopeng.xpcarlife.data.HvacConcentrationStatus;
import com.xiaopeng.xpcarlife.data.HvacStatus;
import com.xiaopeng.xpcarlife.util.CarLifeStateConverter;
/* loaded from: classes.dex */
public class CarLifeHvacController {
    private static final String TAG = "CarLifeHvacController";

    public HvacStatus getHvacSfsState() {
        int i;
        try {
            i = CarClientWrapper.getInstance().getCarHvacManager().getSfsSwitchStatus();
        } catch (Exception e) {
            Log.d(TAG, "getHvacState error, error = " + e);
            e.printStackTrace();
            i = 0;
        }
        Log.d(TAG, "Karl log about getHvacSfsState sfsSwitchStatus = " + i);
        return CarLifeStateConverter.switchStateConvert(i);
    }

    public HvacConcentrationStatus getHvacSfsConcentrationState() {
        int i;
        try {
            i = CarClientWrapper.getInstance().getCarHvacManager().getSfsConcentrationStatus();
        } catch (Exception e) {
            Log.d(TAG, "getHvacSfsConcentrationState error, error = " + e);
            e.printStackTrace();
            i = 0;
        }
        Log.d(TAG, "Karl log about getHvacSfsConcentrationState sfsSwitchStatus = " + i);
        return CarLifeStateConverter.concentrationStateConvert(i);
    }

    public HvacChannelStatus getHvacSfsChannel() {
        int i;
        try {
            i = CarClientWrapper.getInstance().getCarHvacManager().getSfsChannel();
        } catch (Exception e) {
            Log.d(TAG, "getHvacSfsChannel error, error = " + e);
            e.printStackTrace();
            i = 0;
        }
        Log.d(TAG, "Karl log about getHvacSfsChannel sfsSwitchStatus = " + i);
        return CarLifeStateConverter.channelStateConvert(i);
    }
}
