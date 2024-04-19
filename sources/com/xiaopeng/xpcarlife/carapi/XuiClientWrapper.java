package com.xiaopeng.xpcarlife.carapi;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.xiaopeng.xpcarlife.CarLifeApplication;
import com.xiaopeng.xuimanager.XUIManager;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.userscenario.UserScenarioManager;
/* loaded from: classes.dex */
public class XuiClientWrapper {
    private static final String TAG = "XuiClientWrapper";
    private volatile boolean mIsXuiServiceConnected;
    private final UserScenarioManager.UserScenarioListener mListener;
    private UserScenarioManager mUserScenarioManager;
    private XUIManager mXUIManager;
    private final ServiceConnection mXuiConnectionCb;

    private XuiClientWrapper() {
        this.mListener = new UserScenarioManager.UserScenarioListener() { // from class: com.xiaopeng.xpcarlife.carapi.XuiClientWrapper.1
            public void onUserScenarioStateChanged(String scenario, int status) {
                Log.d(XuiClientWrapper.TAG, "UserScenarioListener , scenario = " + scenario + " ,status = " + status);
            }
        };
        this.mIsXuiServiceConnected = false;
        this.mXuiConnectionCb = new ServiceConnection() { // from class: com.xiaopeng.xpcarlife.carapi.XuiClientWrapper.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(XuiClientWrapper.TAG, "onServiceConnected");
                XuiClientWrapper.this.initUserScenarioManager();
                XuiClientWrapper.this.mIsXuiServiceConnected = true;
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                Log.i(XuiClientWrapper.TAG, "onServiceDisconnected");
                XuiClientWrapper.this.mIsXuiServiceConnected = false;
            }
        };
    }

    public static XuiClientWrapper getInstance() {
        return SingleHolder.sInstance;
    }

    public void connectToXuiService() {
        if (this.mIsXuiServiceConnected) {
            return;
        }
        this.mXUIManager = XUIManager.createXUIManager(CarLifeApplication.getInstance(), this.mXuiConnectionCb);
        Log.i(TAG, "Start to connect Car service");
        this.mXUIManager.connect();
    }

    public String enterMovieMode() {
        UserScenarioManager userScenarioManager = this.mUserScenarioManager;
        if (userScenarioManager != null) {
            String enterUserScenario = userScenarioManager.enterUserScenario("spacecapsule_mode_movie", "activity");
            Log.d(TAG, "enterMovieMode , response : " + enterUserScenario);
            return enterUserScenario;
        }
        Log.d(TAG, "enterSleepMode is null ");
        return null;
    }

    public String enterSleepMode() {
        UserScenarioManager userScenarioManager = this.mUserScenarioManager;
        if (userScenarioManager != null) {
            String enterUserScenario = userScenarioManager.enterUserScenario("spacecapsule_mode_sleep", "activity");
            Log.d(TAG, "enterSleepMode , response : " + enterUserScenario);
            return enterUserScenario;
        }
        Log.d(TAG, "enterSleepMode is null ");
        return null;
    }

    public void disconnect() {
        XUIManager xUIManager;
        if (!this.mIsXuiServiceConnected || (xUIManager = this.mXUIManager) == null) {
            return;
        }
        xUIManager.disconnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initUserScenarioManager() {
        UserScenarioManager userScenarioManager = getUserScenarioManager();
        if (userScenarioManager != null) {
            userScenarioManager.registerListener(this.mListener);
        } else {
            Log.d(TAG, "initHvacManager = null");
        }
    }

    private UserScenarioManager getUserScenarioManager() {
        try {
            this.mUserScenarioManager = (UserScenarioManager) this.mXUIManager.getXUIServiceManager("userscenario");
        } catch (XUIServiceNotConnectedException e) {
            e.printStackTrace();
        }
        return this.mUserScenarioManager;
    }

    /* loaded from: classes.dex */
    private static class SingleHolder {
        private static final XuiClientWrapper sInstance = new XuiClientWrapper();

        private SingleHolder() {
        }
    }
}
