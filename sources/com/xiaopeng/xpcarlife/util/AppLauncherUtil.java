package com.xiaopeng.xpcarlife.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.xiaopeng.xpcarlife.carapi.XuiClientWrapper;
import com.xiaopeng.xpcarlife.eventtracking.EventEnum;
import com.xiaopeng.xpcarlife.eventtracking.EventTrackingHelper;
import com.xiaopeng.xpcarlife.eventtracking.PagesEnum;
import com.xiaopeng.xpcarlife.view.PlayVideoActivity;
/* loaded from: classes.dex */
public class AppLauncherUtil {
    public static final String ACTION_POWER = "android.intent.action.showTrunkPowerDialog";
    public static final String ACTION_SOLAR_ROOF = "android.intent.action.showSolarRoofDialog";
    public static final String ACTION_SPACE_CAPSULE_GUIDE = "com.xiaopeng.carcontrol.intent.action.SHOW_SPACE_CAPSULE_USE_GUIDE";
    public static final String DATA_AROMA = "xiaopeng://aiot/device/detail?type=fragrance&from=carlife";
    public static final String DATA_FREEZER = "xiaopeng://aiot/device/detail?type=fridge&from=carlife";
    public static final String PACKAGE_NAME_POWER_CENTER = "com.xiaopeng.chargecontrol";

    public static void launch() {
    }

    public static String launchSleepMode() {
        return XuiClientWrapper.getInstance().enterSleepMode();
    }

    public static String launchMovieMode() {
        return XuiClientWrapper.getInstance().enterMovieMode();
    }

    public static void launchIntroduce(Activity activity) {
        activity.startActivity(new Intent(activity, PlayVideoActivity.class));
    }

    public static void launchGuide(Activity activity) {
        activity.startActivity(new Intent(ACTION_SPACE_CAPSULE_GUIDE));
    }

    public static void launchAroma(Activity activity) {
        launchIntentByData(activity, DATA_AROMA);
    }

    public static void launchFreezer(Activity activity) {
        launchIntentByData(activity, DATA_FREEZER);
    }

    public static void launchPower(Activity activity) {
        EventTrackingHelper.sendMolecast(PagesEnum.CAR_LIFE_TRUNK_POWER, EventEnum.OPEN_TRUNK_POWER, "1");
        launchServiceByAction(activity, ACTION_POWER, "com.xiaopeng.chargecontrol");
    }

    public static void launchSolarRoof(Activity activity) {
        EventTrackingHelper.sendMolecast(PagesEnum.CAR_LIFE_SOLAR_ROOF, EventEnum.OPEN_SOLAR_ROOF, "1");
        launchServiceByAction(activity, ACTION_SOLAR_ROOF, "com.xiaopeng.chargecontrol");
    }

    private static void launchIntentByData(Activity activity, String data) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268468224);
        intent.setData(Uri.parse(data));
        activity.startActivity(intent);
    }

    private static void launchServiceByAction(Activity activity, String action, String packageName) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setPackage(packageName);
        activity.startService(intent);
    }
}
