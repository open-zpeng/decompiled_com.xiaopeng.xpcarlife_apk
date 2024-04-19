package com.xiaopeng.xpcarlife.viewmodel;

import android.app.Application;
import android.car.hardware.CarPropertyValue;
import androidx.lifecycle.MediatorLiveData;
import com.xiaopeng.xpcarlife.carapi.CarClientWrapper;
import com.xiaopeng.xpcarlife.data.CarLifeFeatureBean;
import com.xiaopeng.xpcarlife.data.CarLifeHvacEventCallback;
import com.xiaopeng.xpcarlife.data.CarLifeMcuEventCallback;
import com.xiaopeng.xpcarlife.data.CarLifeSpcEventCallback;
import com.xiaopeng.xpcarlife.data.HvacChannelStatus;
import com.xiaopeng.xpcarlife.data.HvacConcentrationStatus;
import com.xiaopeng.xpcarlife.data.HvacStatus;
import com.xiaopeng.xpcarlife.data.SpcStatus;
import com.xiaopeng.xpcarlife.data.TrunkPowerStatus;
import com.xiaopeng.xpcarlife.model.HomeModel;
/* loaded from: classes.dex */
public class HomeViewModel extends BaseViewModel<HomeModel> {
    private CarLifeFeatureBean mCarLifeFeatureBean;
    private final MediatorLiveData<CarLifeFeatureBean> mCarLifeFeatureData;
    private final CarLifeHvacEventCallback mCarLifeHvacEventCallback;
    private final CarLifeMcuEventCallback mCarLifeMcuEventCallback;
    private final CarLifeSpcEventCallback mCarLifeSpcEventCallback;
    private final MediatorLiveData<TrunkPowerStatus> mFridgeStatusData;
    private final MediatorLiveData<HvacChannelStatus> mHvacChannelStatusData;
    private final MediatorLiveData<HvacConcentrationStatus> mHvacConcentrationStatusData;
    private final MediatorLiveData<HvacStatus> mHvacStatusData;
    private final MediatorLiveData<SpcStatus> mSpcStatusData;
    private final MediatorLiveData<TrunkPowerStatus> mTrunkPowerStatusData;

    public /* synthetic */ void lambda$new$0$HomeViewModel(CarPropertyValue carPropertyValue) {
        if (carPropertyValue.getPropertyId() != 557847655) {
            return;
        }
        if (this.mCarLifeFeatureBean.isTrunkPowerEnable()) {
            getPowerStatus();
        }
        if (this.mCarLifeFeatureBean.isFridgeEnable()) {
            getFridgeStatus();
        }
    }

    public /* synthetic */ void lambda$new$1$HomeViewModel(CarPropertyValue carPropertyValue) {
        if (carPropertyValue.getPropertyId() == 557857793 && this.mCarLifeFeatureBean.isSPCEnable()) {
            getSpcStatus();
        }
    }

    public /* synthetic */ void lambda$new$2$HomeViewModel(CarPropertyValue carPropertyValue) {
        if (this.mCarLifeFeatureBean.isSFSEnable()) {
            int propertyId = carPropertyValue.getPropertyId();
            if (propertyId == 557849143) {
                getHvacStatus();
            } else if (propertyId == 557849145) {
                getHvacChannelStatus();
            } else if (propertyId != 557849150) {
            } else {
                getHvacSfsConcentrationState();
            }
        }
    }

    public MediatorLiveData<CarLifeFeatureBean> getCarLifeFeatureData() {
        return this.mCarLifeFeatureData;
    }

    public MediatorLiveData<HvacConcentrationStatus> hvacConcentrationStatusData() {
        return this.mHvacConcentrationStatusData;
    }

    public MediatorLiveData<HvacChannelStatus> hvacChannelStatusData() {
        return this.mHvacChannelStatusData;
    }

    public MediatorLiveData<HvacStatus> hvacStatusLiveData() {
        return this.mHvacStatusData;
    }

    public MediatorLiveData<SpcStatus> spcStatusLiveData() {
        return this.mSpcStatusData;
    }

    public MediatorLiveData<TrunkPowerStatus> getTrunkPowerStatusData() {
        return this.mTrunkPowerStatusData;
    }

    public MediatorLiveData<TrunkPowerStatus> getFridgeStatusData() {
        return this.mFridgeStatusData;
    }

    public HomeViewModel(Application application) {
        super(application);
        this.mHvacStatusData = new MediatorLiveData<>();
        this.mHvacConcentrationStatusData = new MediatorLiveData<>();
        this.mHvacChannelStatusData = new MediatorLiveData<>();
        this.mCarLifeFeatureData = new MediatorLiveData<>();
        this.mSpcStatusData = new MediatorLiveData<>();
        this.mTrunkPowerStatusData = new MediatorLiveData<>();
        this.mFridgeStatusData = new MediatorLiveData<>();
        this.mCarLifeFeatureBean = new CarLifeFeatureBean();
        this.mCarLifeMcuEventCallback = new CarLifeMcuEventCallback() { // from class: com.xiaopeng.xpcarlife.viewmodel.-$$Lambda$HomeViewModel$DLm-iovduEblvaB2M3BPi8W-cqQ
            @Override // com.xiaopeng.xpcarlife.data.CarLifeMcuEventCallback
            public final void onChangeEvent(CarPropertyValue carPropertyValue) {
                HomeViewModel.this.lambda$new$0$HomeViewModel(carPropertyValue);
            }
        };
        this.mCarLifeSpcEventCallback = new CarLifeSpcEventCallback() { // from class: com.xiaopeng.xpcarlife.viewmodel.-$$Lambda$HomeViewModel$TZuoLiyh3lLVVtxHLV7wQS7N_xI
            @Override // com.xiaopeng.xpcarlife.data.CarLifeSpcEventCallback
            public final void onChangeEvent(CarPropertyValue carPropertyValue) {
                HomeViewModel.this.lambda$new$1$HomeViewModel(carPropertyValue);
            }
        };
        this.mCarLifeHvacEventCallback = new CarLifeHvacEventCallback() { // from class: com.xiaopeng.xpcarlife.viewmodel.-$$Lambda$HomeViewModel$K94kX6aqK9kk7MHwkJ21_bUspSw
            @Override // com.xiaopeng.xpcarlife.data.CarLifeHvacEventCallback
            public final void onChangeEvent(CarPropertyValue carPropertyValue) {
                HomeViewModel.this.lambda$new$2$HomeViewModel(carPropertyValue);
            }
        };
        registerHvacEventCallback();
        registerSpcEventCallback();
        registerMcuEventCallback();
    }

    @Override // com.xiaopeng.xpcarlife.viewmodel.BaseViewModel
    public HomeModel createModel() {
        return new HomeModel();
    }

    public void getHvacStatus() {
        this.mHvacStatusData.postValue(((HomeModel) this.mModel).getHvacSfsState());
    }

    public void getHvacSfsConcentrationState() {
        this.mHvacConcentrationStatusData.postValue(((HomeModel) this.mModel).getHvacSfsConcentrationState());
    }

    public void getHvacChannelStatus() {
        this.mHvacChannelStatusData.postValue(((HomeModel) this.mModel).getHvacSfsChannel());
    }

    public void getCarLifeFeatureEnable() {
        CarLifeFeatureBean carLifeFeature = ((HomeModel) this.mModel).getCarLifeFeature();
        this.mCarLifeFeatureBean = carLifeFeature;
        this.mCarLifeFeatureData.postValue(carLifeFeature);
    }

    public void getSpcStatus() {
        this.mSpcStatusData.postValue(((HomeModel) this.mModel).getSpcStatus());
    }

    public void getPowerStatus() {
        this.mTrunkPowerStatusData.postValue(((HomeModel) this.mModel).getTrunkPowerStatus());
    }

    public void getFridgeStatus() {
        this.mFridgeStatusData.postValue(((HomeModel) this.mModel).getFridgeStatus());
    }

    private void registerHvacEventCallback() {
        CarClientWrapper.getInstance().addHvacEventCallback(this.mCarLifeHvacEventCallback);
    }

    private void registerSpcEventCallback() {
        CarClientWrapper.getInstance().addSpcEventCallback(this.mCarLifeSpcEventCallback);
    }

    private void registerMcuEventCallback() {
        CarClientWrapper.getInstance().addMcuEventCallback(this.mCarLifeMcuEventCallback);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpcarlife.viewmodel.BaseViewModel, androidx.lifecycle.ViewModel
    public void onCleared() {
        CarClientWrapper.getInstance().removeHvacEventCallback(this.mCarLifeHvacEventCallback);
        CarClientWrapper.getInstance().removeSpcEventCallback(this.mCarLifeSpcEventCallback);
        CarClientWrapper.getInstance().removeMcuEventCallback(this.mCarLifeMcuEventCallback);
        super.onCleared();
    }
}
