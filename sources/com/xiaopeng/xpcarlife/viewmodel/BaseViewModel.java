package com.xiaopeng.xpcarlife.viewmodel;

import android.app.Application;
import android.car.hardware.CarPropertyValue;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import com.xiaopeng.xpcarlife.carapi.CarClientWrapper;
import com.xiaopeng.xpcarlife.data.CarLifeVcuEventCallback;
import com.xiaopeng.xpcarlife.data.GearLevel;
import com.xiaopeng.xpcarlife.model.BaseModel;
import com.xiaopeng.xpcarlife.mvvm.IViewModel;
/* loaded from: classes.dex */
public abstract class BaseViewModel<M extends BaseModel> extends AndroidViewModel implements IViewModel {
    private final CarLifeVcuEventCallback mCarLifeVcuEventCallback;
    protected final MediatorLiveData<GearLevel> mGearLevelData;
    protected M mModel;

    public abstract M createModel();

    public /* synthetic */ void lambda$new$0$BaseViewModel(CarPropertyValue carPropertyValue) {
        if (carPropertyValue.getPropertyId() == 557847045) {
            getGearLevel();
        }
    }

    public MediatorLiveData<GearLevel> getGearLevelData() {
        return this.mGearLevelData;
    }

    public GearLevel getGearLevel() {
        GearLevel gearLevel = this.mModel.getGearLevel();
        this.mGearLevelData.postValue(gearLevel);
        return gearLevel;
    }

    public BaseViewModel(Application application) {
        super(application);
        this.mGearLevelData = new MediatorLiveData<>();
        this.mCarLifeVcuEventCallback = new CarLifeVcuEventCallback() { // from class: com.xiaopeng.xpcarlife.viewmodel.-$$Lambda$BaseViewModel$pXdIDM_V6XUmLOrEAcA0OuEW4P0
            @Override // com.xiaopeng.xpcarlife.data.CarLifeVcuEventCallback
            public final void onChangeEvent(CarPropertyValue carPropertyValue) {
                BaseViewModel.this.lambda$new$0$BaseViewModel(carPropertyValue);
            }
        };
        registerVcuEventCallback();
    }

    private void registerVcuEventCallback() {
        CarClientWrapper.getInstance().addVcuEventCallback(this.mCarLifeVcuEventCallback);
    }

    public void init() {
        initModel();
    }

    public void initModel() {
        this.mModel = createModel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        CarClientWrapper.getInstance().removeVcuEventCallback(this.mCarLifeVcuEventCallback);
        super.onCleared();
    }
}
