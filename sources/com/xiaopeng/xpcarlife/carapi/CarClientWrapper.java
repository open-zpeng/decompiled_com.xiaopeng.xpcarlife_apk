package com.xiaopeng.xpcarlife.carapi;

import android.car.Car;
import android.car.CarManagerBase;
import android.car.CarNotConnectedException;
import android.car.hardware.CarEcuManager;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.hvac.CarHvacManager;
import android.car.hardware.mcu.CarMcuManager;
import android.car.hardware.spc.CarSpcManager;
import android.car.hardware.vcu.CarVcuManager;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.xiaopeng.xpcarlife.CarLifeApplication;
import com.xiaopeng.xpcarlife.carapi.CarClientWrapper;
import com.xiaopeng.xpcarlife.data.CarLifeHvacEventCallback;
import com.xiaopeng.xpcarlife.data.CarLifeMcuEventCallback;
import com.xiaopeng.xpcarlife.data.CarLifeSpcEventCallback;
import com.xiaopeng.xpcarlife.data.CarLifeVcuEventCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
/* loaded from: classes.dex */
public class CarClientWrapper {
    private static final String TAG = "CarClientWrapper";
    private Car mCarClient;
    private final ServiceConnection mCarConnectionCb;
    private final CarHvacManager.CarHvacEventCallback mCarHvacEventCallback;
    private CarHvacManager mCarHvacManager;
    private final ArrayList<CarLifeHvacEventCallback> mCarLifeHvacEventCallbacks;
    private final ArrayList<CarLifeMcuEventCallback> mCarLifeMcuEventCallbacks;
    private final ArrayList<CarLifeSpcEventCallback> mCarLifeSpcEventCallbacks;
    private final ArrayList<CarLifeVcuEventCallback> mCarLifeVcuEventCallbacks;
    private final CarEcuManager.CarEcuEventCallback mCarMcuEventCallback;
    private CarMcuManager mCarMcuManager;
    private final CarEcuManager.CarEcuEventCallback mCarSpcEventCallback;
    private CarSpcManager mCarSpcManager;
    private final CarVcuManager.CarVcuEventCallback mCarVcuEventCallback;
    private CarVcuManager mCarVcuManager;
    private final List<Integer> mHvacPropertyIds;
    private volatile boolean mIsCarServiceConnected;
    private final List<Integer> mMcuPropertyIds;
    private final List<Integer> mSpcPropertyIds;
    private final List<Integer> mVcuPropertyIds;

    /* synthetic */ CarClientWrapper(AnonymousClass1 anonymousClass1) {
        this();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class SingleHolder {
        private static final CarClientWrapper sInstance = new CarClientWrapper(null);

        private SingleHolder() {
        }
    }

    public static CarClientWrapper getInstance() {
        return SingleHolder.sInstance;
    }

    /* renamed from: com.xiaopeng.xpcarlife.carapi.CarClientWrapper$1  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements CarHvacManager.CarHvacEventCallback {
        public void onErrorEvent(int propertyId, int zone) {
        }

        AnonymousClass1() {
        }

        public void onChangeEvent(final CarPropertyValue carPropertyValue) {
            Log.d(CarClientWrapper.TAG, "karl log mCarHvacEventCallback car property change event " + carPropertyValue);
            CarClientWrapper.this.mCarLifeHvacEventCallbacks.forEach(new Consumer() { // from class: com.xiaopeng.xpcarlife.carapi.-$$Lambda$CarClientWrapper$1$JCaC8dQl9P7YHkq0i0J7SwPJ3BU
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CarClientWrapper.AnonymousClass1.lambda$onChangeEvent$0(carPropertyValue, (CarLifeHvacEventCallback) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$onChangeEvent$0(final CarPropertyValue carPropertyValue, CarLifeHvacEventCallback callback) {
            if (callback != null) {
                callback.onChangeEvent(carPropertyValue);
            }
        }
    }

    /* renamed from: com.xiaopeng.xpcarlife.carapi.CarClientWrapper$2  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass2 implements CarEcuManager.CarEcuEventCallback {
        public void onErrorEvent(int i, int i1) {
        }

        AnonymousClass2() {
        }

        public void onChangeEvent(final CarPropertyValue carPropertyValue) {
            Log.d(CarClientWrapper.TAG, "karl log mCarSpcEventCallback car property change event " + carPropertyValue);
            CarClientWrapper.this.mCarLifeSpcEventCallbacks.forEach(new Consumer() { // from class: com.xiaopeng.xpcarlife.carapi.-$$Lambda$CarClientWrapper$2$WJEViC-upyVp0MySO6FNf1mAMsM
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((CarLifeSpcEventCallback) obj).onChangeEvent(carPropertyValue);
                }
            });
        }
    }

    /* renamed from: com.xiaopeng.xpcarlife.carapi.CarClientWrapper$3  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass3 implements CarEcuManager.CarEcuEventCallback {
        public void onErrorEvent(int i, int i1) {
        }

        AnonymousClass3() {
        }

        public void onChangeEvent(final CarPropertyValue carPropertyValue) {
            Log.d(CarClientWrapper.TAG, "karl log mCarMcuEventCallback car property change event " + carPropertyValue);
            CarClientWrapper.this.mCarLifeMcuEventCallbacks.forEach(new Consumer() { // from class: com.xiaopeng.xpcarlife.carapi.-$$Lambda$CarClientWrapper$3$ZBH5EB5rD-ts8zv68mV4IfZOPUM
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((CarLifeMcuEventCallback) obj).onChangeEvent(carPropertyValue);
                }
            });
        }
    }

    /* renamed from: com.xiaopeng.xpcarlife.carapi.CarClientWrapper$4  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass4 implements CarVcuManager.CarVcuEventCallback {
        public void onErrorEvent(int i, int i1) {
        }

        AnonymousClass4() {
        }

        public void onChangeEvent(final CarPropertyValue carPropertyValue) {
            Log.d(CarClientWrapper.TAG, "karl log mCarVcuEventCallback car property change event " + carPropertyValue);
            CarClientWrapper.this.mCarLifeVcuEventCallbacks.forEach(new Consumer() { // from class: com.xiaopeng.xpcarlife.carapi.-$$Lambda$CarClientWrapper$4$z4A3hHVtb876HVNDXbxlwHtZGTg
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((CarLifeVcuEventCallback) obj).onChangeEvent(carPropertyValue);
                }
            });
        }
    }

    private CarClientWrapper() {
        this.mIsCarServiceConnected = false;
        this.mHvacPropertyIds = new ArrayList(Arrays.asList(557849143, 557849145, 557849150));
        this.mCarLifeHvacEventCallbacks = new ArrayList<>();
        this.mCarHvacEventCallback = new AnonymousClass1();
        this.mSpcPropertyIds = new ArrayList(Arrays.asList(557857793));
        this.mCarLifeSpcEventCallbacks = new ArrayList<>();
        this.mCarSpcEventCallback = new AnonymousClass2();
        this.mMcuPropertyIds = new ArrayList(Arrays.asList(557847655));
        this.mCarLifeMcuEventCallbacks = new ArrayList<>();
        this.mCarMcuEventCallback = new AnonymousClass3();
        this.mVcuPropertyIds = new ArrayList(Collections.singletonList(557847045));
        this.mCarLifeVcuEventCallbacks = new ArrayList<>();
        this.mCarVcuEventCallback = new AnonymousClass4();
        this.mCarConnectionCb = new ServiceConnection() { // from class: com.xiaopeng.xpcarlife.carapi.CarClientWrapper.5
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(CarClientWrapper.TAG, "onServiceConnected");
                CarClientWrapper.this.initHvacManager();
                CarClientWrapper.this.initSpcManager();
                CarClientWrapper.this.initMcuManager();
                CarClientWrapper.this.initVcuManager();
                CarClientWrapper.this.mIsCarServiceConnected = true;
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                Log.i(CarClientWrapper.TAG, "onServiceDisconnected");
                CarClientWrapper.this.mIsCarServiceConnected = false;
            }
        };
    }

    public void addHvacEventCallback(CarLifeHvacEventCallback callback) {
        this.mCarLifeHvacEventCallbacks.add(callback);
    }

    public void removeHvacEventCallback(CarLifeHvacEventCallback callback) {
        this.mCarLifeHvacEventCallbacks.remove(callback);
    }

    public void addSpcEventCallback(CarLifeSpcEventCallback callback) {
        this.mCarLifeSpcEventCallbacks.add(callback);
    }

    public void removeSpcEventCallback(CarLifeSpcEventCallback callback) {
        this.mCarLifeSpcEventCallbacks.remove(callback);
    }

    public void addMcuEventCallback(CarLifeMcuEventCallback callback) {
        this.mCarLifeMcuEventCallbacks.add(callback);
    }

    public void removeMcuEventCallback(CarLifeMcuEventCallback callback) {
        this.mCarLifeMcuEventCallbacks.remove(callback);
    }

    public void addVcuEventCallback(CarLifeVcuEventCallback callback) {
        this.mCarLifeVcuEventCallbacks.add(callback);
    }

    public void removeVcuEventCallback(CarLifeVcuEventCallback callback) {
        this.mCarLifeVcuEventCallbacks.remove(callback);
    }

    public CarHvacManager getCarHvacManager() {
        return this.mCarHvacManager;
    }

    public CarSpcManager getCarSpcManager() {
        return this.mCarSpcManager;
    }

    public CarMcuManager getCarMcuManager() {
        return this.mCarMcuManager;
    }

    public CarVcuManager getCarVcuManager() {
        return this.mCarVcuManager;
    }

    public void connectToCar() {
        if (this.mIsCarServiceConnected) {
            return;
        }
        this.mCarClient = Car.createCar(CarLifeApplication.getInstance(), this.mCarConnectionCb);
        Log.i(TAG, "Start to connect Car service");
        this.mCarClient.connect();
    }

    public void disconnect() {
        Car car;
        if (!this.mIsCarServiceConnected || (car = this.mCarClient) == null) {
            return;
        }
        car.disconnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initHvacManager() {
        CarHvacManager carManagerByName = getCarManagerByName("hvac");
        if (carManagerByName != null) {
            CarHvacManager carHvacManager = carManagerByName;
            this.mCarHvacManager = carHvacManager;
            try {
                carHvacManager.registerPropCallback(this.mHvacPropertyIds, this.mCarHvacEventCallback);
                return;
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
                return;
            }
        }
        Log.d(TAG, "initHvacManager = null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initSpcManager() {
        CarSpcManager carManagerByName = getCarManagerByName("xp_spc");
        if (carManagerByName != null) {
            CarSpcManager carSpcManager = carManagerByName;
            this.mCarSpcManager = carSpcManager;
            try {
                carSpcManager.registerPropCallback(this.mSpcPropertyIds, this.mCarSpcEventCallback);
                return;
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
                return;
            }
        }
        Log.d(TAG, "initHvacManager = null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initMcuManager() {
        CarMcuManager carManagerByName = getCarManagerByName("xp_mcu");
        if (carManagerByName != null) {
            CarMcuManager carMcuManager = carManagerByName;
            this.mCarMcuManager = carMcuManager;
            try {
                carMcuManager.registerPropCallback(this.mMcuPropertyIds, this.mCarMcuEventCallback);
                return;
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
                return;
            }
        }
        Log.d(TAG, "initHvacManager = null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initVcuManager() {
        CarVcuManager carManagerByName = getCarManagerByName("xp_vcu");
        if (carManagerByName instanceof CarVcuManager) {
            CarVcuManager carVcuManager = carManagerByName;
            this.mCarVcuManager = carVcuManager;
            try {
                carVcuManager.registerPropCallback(this.mVcuPropertyIds, this.mCarVcuEventCallback);
                return;
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
                return;
            }
        }
        Log.d(TAG, "initVcuManager = null");
    }

    private CarManagerBase getCarManagerByName(String serviceName) {
        try {
            return (CarManagerBase) this.mCarClient.getCarManager(serviceName);
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
