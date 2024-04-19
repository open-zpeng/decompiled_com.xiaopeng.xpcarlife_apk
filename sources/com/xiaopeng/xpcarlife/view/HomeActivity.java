package com.xiaopeng.xpcarlife.view;

import android.car.Car;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.rastermill.FrameSequenceUtil;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xpcarlife.R;
import com.xiaopeng.xpcarlife.broadcast.DialogChangeReceiver;
import com.xiaopeng.xpcarlife.broadcast.IDialogChangeListener;
import com.xiaopeng.xpcarlife.data.CarLifeFeatureBean;
import com.xiaopeng.xpcarlife.data.FontType;
import com.xiaopeng.xpcarlife.data.GearLevel;
import com.xiaopeng.xpcarlife.data.HvacChannelStatus;
import com.xiaopeng.xpcarlife.data.HvacConcentrationStatus;
import com.xiaopeng.xpcarlife.data.HvacStatus;
import com.xiaopeng.xpcarlife.data.SpcStatus;
import com.xiaopeng.xpcarlife.data.TrunkPowerStatus;
import com.xiaopeng.xpcarlife.eventtracking.EventEnum;
import com.xiaopeng.xpcarlife.eventtracking.EventTrackingHelper;
import com.xiaopeng.xpcarlife.eventtracking.PagesEnum;
import com.xiaopeng.xpcarlife.util.AppLauncherUtil;
import com.xiaopeng.xpcarlife.viewmodel.HomeViewModel;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class HomeActivity extends BaseActivity<HomeViewModel> implements View.OnClickListener {
    private static final String ANIM_FILE_NAME_DAY = "light_day_demo.webp";
    private static final String ANIM_FILE_NAME_NIGHT = "light_night_demo.webp";
    private static final int SCHEMA_OPEN_PAGE_DELAY = 500;
    private static final String TAG = "HomeActivity";
    private static final int WEBP_COUNT = -1;
    private ViewStub mAllViewStub;
    private TextView mAromaState;
    private View mAromaStateBg;
    DialogChangeReceiver mBroadcastReceiver;
    private Observer<CarLifeFeatureBean> mCarLifeFeatureObserver;
    IDialogChangeListener mDialogChangeListener = new IDialogChangeListener() { // from class: com.xiaopeng.xpcarlife.view.HomeActivity.1
        @Override // com.xiaopeng.xpcarlife.broadcast.IDialogChangeListener
        public void onDialogShow() {
            if (TextUtils.isEmpty(HomeActivity.this.getSceneId()) || HomeActivity.this.getSceneId().contains("Local") || HomeActivity.this.getSceneId().contains("local")) {
                return;
            }
            VuiEngine.getInstance(HomeActivity.this).exitScene(HomeActivity.this.getSceneId());
        }

        @Override // com.xiaopeng.xpcarlife.broadcast.IDialogChangeListener
        public void onDialogHidden() {
            if (TextUtils.isEmpty(HomeActivity.this.getSceneId()) || HomeActivity.this.getSceneId().contains("Local") || HomeActivity.this.getSceneId().contains("local")) {
                return;
            }
            VuiEngine.getInstance(HomeActivity.this).enterScene(HomeActivity.this.getSceneId());
        }
    };
    private XTextView mFreezerState;
    private View mFreezerStateBg;
    private Observer<TrunkPowerStatus> mFridgeStatusObserver;
    private Observer<HvacChannelStatus> mHvacChannelStatusObserver;
    private Observer<HvacConcentrationStatus> mHvacConcentrationStatusObserver;
    private Observer<HvacStatus> mHvacStatusObserver;
    private ImageView mImNotFacility;
    private ImageView mImgTitle;
    private boolean mIsD55A;
    private XLinearLayout mLlAroma;
    private LinearLayout mLlCockpitParticulars;
    private XLinearLayout mLlFreezer;
    private XLinearLayout mLlPlayMode;
    private View mLlPower;
    private XLinearLayout mLlSleepMode;
    private XLinearLayout mLlSunroof;
    private ViewStub mOnlyViewStub;
    private XLinearLayout mOperatingGuide;
    int mPowerDrawable;
    private XTextView mPowerState;
    private View mPowerStateBg;
    private XRelativeLayout mSmartFacility;
    private Observer<SpcStatus> mSpcStatusObserver;
    private XTextView mSunroofState;
    private View mSunroofStateBg;
    private Observer<TrunkPowerStatus> mTrunkPowerStatusObserver;
    private TextView mTvNotFacility;
    private ViewStub mTwoViewStub;
    private View mView;
    private ImageView mWebpImg;

    public HomeActivity() {
        boolean equals = "Q3A".equals(Car.getXpCduType());
        this.mIsD55A = equals;
        this.mPowerDrawable = equals ? R.drawable.img_power_d55a : R.drawable.img_power;
        this.mHvacConcentrationStatusObserver = new Observer() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$HomeActivity$pOk6UZC-G2QwggX_LSQLWUzEuic
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HomeActivity.lambda$new$0((HvacConcentrationStatus) obj);
            }
        };
        this.mHvacChannelStatusObserver = new Observer() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$HomeActivity$MO9-Ac4zr2B57AJjfhWRh8Kwz50
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HomeActivity.lambda$new$1((HvacChannelStatus) obj);
            }
        };
        this.mCarLifeFeatureObserver = new Observer() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$HomeActivity$sV-hxDWEUwUFSZ0TX1mU6ZQHhrQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HomeActivity.this.lambda$new$2$HomeActivity((CarLifeFeatureBean) obj);
            }
        };
        this.mHvacStatusObserver = new Observer() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$HomeActivity$J-qWPPasaMP0gypzHeTznx83kpc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HomeActivity.this.lambda$new$3$HomeActivity((HvacStatus) obj);
            }
        };
        this.mSpcStatusObserver = new Observer() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$HomeActivity$XP6k6dKfB5kv7BmQaVm9b6jlfeQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HomeActivity.this.lambda$new$4$HomeActivity((SpcStatus) obj);
            }
        };
        this.mTrunkPowerStatusObserver = new Observer() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$HomeActivity$_CdXH2CosvubvugeUWSeo8KUlDY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HomeActivity.this.lambda$new$5$HomeActivity((TrunkPowerStatus) obj);
            }
        };
        this.mFridgeStatusObserver = new Observer() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$HomeActivity$no2Fp8HZtsMHF-aaJVYf9zZ8JUw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                HomeActivity.this.lambda$new$6$HomeActivity((TrunkPowerStatus) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.xpcarlife.view.HomeActivity$2  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$xpcarlife$data$FontType;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$xpcarlife$data$HvacChannelStatus;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$xpcarlife$data$HvacConcentrationStatus;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$xpcarlife$data$HvacStatus;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$xpcarlife$data$SpcStatus;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$xpcarlife$data$TrunkPowerStatus;

        static {
            int[] iArr = new int[HvacConcentrationStatus.values().length];
            $SwitchMap$com$xiaopeng$xpcarlife$data$HvacConcentrationStatus = iArr;
            try {
                iArr[HvacConcentrationStatus.HVAC_CONCENTRATION_CLOSED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$xpcarlife$data$HvacConcentrationStatus[HvacConcentrationStatus.HVAC_CONCENTRATION_LOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$xpcarlife$data$HvacConcentrationStatus[HvacConcentrationStatus.HVAC_CONCENTRATION_HIGH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[HvacChannelStatus.values().length];
            $SwitchMap$com$xiaopeng$xpcarlife$data$HvacChannelStatus = iArr2;
            try {
                iArr2[HvacChannelStatus.HVAC_CONCENTRATION_CHANNEL_1.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$xpcarlife$data$HvacChannelStatus[HvacChannelStatus.HVAC_CONCENTRATION_CHANNEL_2.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$xpcarlife$data$HvacChannelStatus[HvacChannelStatus.HVAC_CONCENTRATION__CHANNEL_3.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr3 = new int[HvacStatus.values().length];
            $SwitchMap$com$xiaopeng$xpcarlife$data$HvacStatus = iArr3;
            try {
                iArr3[HvacStatus.HVAC_STATUS_OFF.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$xpcarlife$data$HvacStatus[HvacStatus.HVAC_STATUS_ON.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$xiaopeng$xpcarlife$data$HvacStatus[HvacStatus.HVAC_STATUS_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            int[] iArr4 = new int[SpcStatus.values().length];
            $SwitchMap$com$xiaopeng$xpcarlife$data$SpcStatus = iArr4;
            try {
                iArr4[SpcStatus.SPC_STATUS_ON.ordinal()] = 1;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$xiaopeng$xpcarlife$data$SpcStatus[SpcStatus.SPC_STATUS_OFF.ordinal()] = 2;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr5 = new int[TrunkPowerStatus.values().length];
            $SwitchMap$com$xiaopeng$xpcarlife$data$TrunkPowerStatus = iArr5;
            try {
                iArr5[TrunkPowerStatus.TRUNK_POWER_STATUS_ON.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$xiaopeng$xpcarlife$data$TrunkPowerStatus[TrunkPowerStatus.TRUNK_POWER_STATUS_OFF.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$xiaopeng$xpcarlife$data$TrunkPowerStatus[TrunkPowerStatus.TRUNK_POWER_STATUS_FAULT.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            int[] iArr6 = new int[FontType.values().length];
            $SwitchMap$com$xiaopeng$xpcarlife$data$FontType = iArr6;
            try {
                iArr6[FontType.FONT_EXQUISITE.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$xiaopeng$xpcarlife$data$FontType[FontType.FONT_READABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$new$0(HvacConcentrationStatus hvacStatus) {
        int i = AnonymousClass2.$SwitchMap$com$xiaopeng$xpcarlife$data$HvacConcentrationStatus[hvacStatus.ordinal()];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$new$1(HvacChannelStatus hvacStatus) {
        int i = AnonymousClass2.$SwitchMap$com$xiaopeng$xpcarlife$data$HvacChannelStatus[hvacStatus.ordinal()];
    }

    public /* synthetic */ void lambda$new$2$HomeActivity(CarLifeFeatureBean carLifeFeatureBean) {
        updateSpaceCapsuleState(carLifeFeatureBean.isSpaceCapsuleEnable() || this.mIsD55A);
        if (carLifeFeatureBean.allDisable()) {
            showDefault(carLifeFeatureBean.allDisable());
            return;
        }
        initFacilityView(carLifeFeatureBean);
        updateAroma(carLifeFeatureBean.isSFSEnable());
        updatePower(carLifeFeatureBean.isTrunkPowerEnable());
        updateFreezer(carLifeFeatureBean.isFridgeEnable());
        updateSunroof(carLifeFeatureBean.isSPCEnable());
    }

    public /* synthetic */ void lambda$new$3$HomeActivity(HvacStatus hvacStatus) {
        TextView textView = this.mAromaState;
        if (textView == null || this.mAromaStateBg == null) {
            return;
        }
        textView.setBackground(null);
        Log.d(TAG, "Karl log about mHvacStatusObserver hvacStatus = " + hvacStatus);
        int i = AnonymousClass2.$SwitchMap$com$xiaopeng$xpcarlife$data$HvacStatus[hvacStatus.ordinal()];
        if (i == 1) {
            this.mAromaState.setText(getString(R.string.smart_facility_close));
            this.mAromaStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_close_bg));
        } else if (i == 2) {
            this.mAromaState.setText(getString(R.string.smart_facility_open));
            this.mAromaStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_open_bg));
        } else if (i != 3) {
        } else {
            this.mAromaState.setText(getString(R.string.smart_facility_error));
            this.mAromaStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_close_bg));
        }
    }

    public /* synthetic */ void lambda$new$4$HomeActivity(SpcStatus spcStatus) {
        XTextView xTextView = this.mSunroofState;
        if (xTextView == null || this.mSunroofStateBg == null) {
            return;
        }
        xTextView.setBackground(null);
        Log.d(TAG, "Karl log about mSpcStatusObserver spcStatus = " + spcStatus);
        int i = AnonymousClass2.$SwitchMap$com$xiaopeng$xpcarlife$data$SpcStatus[spcStatus.ordinal()];
        if (i == 1) {
            this.mSunroofState.setText(getString(R.string.smart_facility_open));
            this.mSunroofStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_open_bg));
        } else if (i != 2) {
        } else {
            this.mSunroofState.setText(getString(R.string.smart_facility_close));
            this.mSunroofStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_close_bg));
        }
    }

    public /* synthetic */ void lambda$new$5$HomeActivity(TrunkPowerStatus trunkPowerStatus) {
        XTextView xTextView = this.mPowerState;
        if (xTextView == null || this.mPowerStateBg == null) {
            return;
        }
        xTextView.setBackground(null);
        Log.d(TAG, "Karl log about mTrunkPowerStatusObserver trunkPowerStatus = " + trunkPowerStatus);
        int i = AnonymousClass2.$SwitchMap$com$xiaopeng$xpcarlife$data$TrunkPowerStatus[trunkPowerStatus.ordinal()];
        if (i == 1) {
            this.mPowerState.setText(getString(R.string.smart_facility_open));
            this.mPowerStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_open_bg));
        } else if (i == 2) {
            this.mPowerState.setText(getString(R.string.smart_facility_close));
            this.mPowerStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_close_bg));
        } else if (i != 3) {
        } else {
            this.mPowerState.setText(getString(R.string.smart_facility_power_abnormal));
            this.mPowerStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_close_bg));
        }
    }

    public /* synthetic */ void lambda$new$6$HomeActivity(TrunkPowerStatus fridgeStatus) {
        XTextView xTextView = this.mFreezerState;
        if (xTextView == null || this.mFreezerStateBg == null) {
            return;
        }
        xTextView.setBackground(null);
        Log.d(TAG, "Karl log about mFridgeStatusObserver fridgeStatus = " + fridgeStatus);
        int i = AnonymousClass2.$SwitchMap$com$xiaopeng$xpcarlife$data$TrunkPowerStatus[fridgeStatus.ordinal()];
        if (i == 1) {
            this.mFreezerState.setText(getString(R.string.smart_facility_open));
            this.mFreezerStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_open_bg));
        } else if (i == 2) {
            this.mFreezerState.setText(getString(R.string.smart_facility_close));
            this.mFreezerStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_close_bg));
        } else if (i != 3) {
        } else {
            this.mFreezerState.setText(getString(R.string.smart_facility_freezer_abnormal));
            this.mFreezerStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_close_bg));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000e, code lost:
        if (r3.isFridgeEnable() != false) goto L38;
     */
    /* JADX WARN: Type inference failed for: r0v0, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void initFacilityView(com.xiaopeng.xpcarlife.data.CarLifeFeatureBean r3) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.xpcarlife.view.HomeActivity.initFacilityView(com.xiaopeng.xpcarlife.data.CarLifeFeatureBean):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpcarlife.view.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.life_activity);
        initView();
        ((HomeViewModel) this.mViewModel).getCarLifeFeatureEnable();
        this.mBroadcastReceiver = new DialogChangeReceiver(this.mDialogChangeListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mBroadcastReceiver.setDialogChangeListener(null);
    }

    private void initView() {
        this.mImgTitle = (ImageView) findViewById(R.id.img_smart);
        setTitleByFontScale();
        XLinearLayout xLinearLayout = (XLinearLayout) findViewById(R.id.bt_operating_guide_layout);
        this.mOperatingGuide = xLinearLayout;
        xLinearLayout.setOnClickListener(this);
        this.mLlCockpitParticulars = (LinearLayout) findViewById(R.id.ll_cockpit_particulars);
        XLinearLayout xLinearLayout2 = (XLinearLayout) findViewById(R.id.ll_sleep_mode);
        this.mLlSleepMode = xLinearLayout2;
        xLinearLayout2.setOnClickListener(this);
        XLinearLayout xLinearLayout3 = (XLinearLayout) findViewById(R.id.ll_play_mode);
        this.mLlPlayMode = xLinearLayout3;
        xLinearLayout3.setOnClickListener(this);
        if (this.mIsD55A) {
            this.mLlPlayMode.setVisibility(8);
            this.mOperatingGuide.setVisibility(8);
            this.mLlSleepMode.setGravity(17);
        } else {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setSize(getResources().getDimensionPixelSize(R.dimen.play_mode_ml), 0);
            this.mLlCockpitParticulars.setDividerDrawable(gradientDrawable);
        }
        this.mSmartFacility = (XRelativeLayout) findViewById(R.id.smart_facility);
        this.mAllViewStub = (ViewStub) findViewById(R.id.smart_facility_all_stub);
        this.mTwoViewStub = (ViewStub) findViewById(R.id.smart_facility_two_stub);
        this.mOnlyViewStub = (ViewStub) findViewById(R.id.smart_facility_only_stub);
        this.mImNotFacility = (ImageView) findViewById(R.id.im_not_facility);
        this.mTvNotFacility = (TextView) findViewById(R.id.tv_not_facility);
        this.mWebpImg = (ImageView) findViewById(R.id.im_bg_car_webp);
        initVuiView();
    }

    private void initVuiView() {
        VuiUtils.addGeneralActProp((IVuiElement) findViewById(R.id.btn_close), getString(R.string.vui_general_action_system_close_return));
        VuiUtils.addHasFeedbackProp(this.mLlSleepMode);
        if (this.mIsD55A) {
            return;
        }
        VuiUtils.addHasFeedbackProp(this.mOperatingGuide);
        VuiUtils.addHasFeedbackProp(this.mLlPlayMode);
    }

    @Override // com.xiaopeng.xpcarlife.view.BaseActivity, com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        if (view.getId() == this.mLlSleepMode.getId() || view.getId() == this.mLlPlayMode.getId()) {
            vuiFeedbackIfNecessary(R.string.vui_feedback_sleep_play_mode_need_p_gear, view);
        } else if (view.getId() == this.mOperatingGuide.getId()) {
            vuiFeedbackIfNecessary(R.string.vui_feedback_introduce_video_need_p_gear, view);
        }
        return super.onInterceptVuiEvent(view, vuiEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        registerReceiver(this.mBroadcastReceiver, new IntentFilter(DialogChangeReceiver.ACTION_DIALOG_CHANGED));
        showWebp();
        handleSchema(getIntent());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        unregisterReceiver(this.mBroadcastReceiver);
        hideWebp();
    }

    @Override // com.xiaopeng.xpcarlife.view.BaseActivity
    public void bindLiveData() {
        setLiveDataObserver(((HomeViewModel) this.mViewModel).hvacStatusLiveData(), this.mHvacStatusObserver);
        setLiveDataObserver(((HomeViewModel) this.mViewModel).hvacConcentrationStatusData(), this.mHvacConcentrationStatusObserver);
        setLiveDataObserver(((HomeViewModel) this.mViewModel).hvacChannelStatusData(), this.mHvacChannelStatusObserver);
        setLiveDataObserver(((HomeViewModel) this.mViewModel).getCarLifeFeatureData(), this.mCarLifeFeatureObserver);
        setLiveDataObserver(((HomeViewModel) this.mViewModel).spcStatusLiveData(), this.mSpcStatusObserver);
        setLiveDataObserver(((HomeViewModel) this.mViewModel).getTrunkPowerStatusData(), this.mTrunkPowerStatusObserver);
        setLiveDataObserver(((HomeViewModel) this.mViewModel).getFridgeStatusData(), this.mFridgeStatusObserver);
    }

    private boolean isGearLevelP() {
        GearLevel value = ((HomeViewModel) this.mViewModel).getGearLevelData().getValue();
        if (value == null || value == GearLevel.GEAR_LEVEL_INVALID) {
            value = ((HomeViewModel) this.mViewModel).getGearLevel();
        }
        Log.i(TAG, "isGearLevelP getGearLevel:" + value);
        return value == GearLevel.GEAR_LEVEL_P;
    }

    private void vuiFeedbackIfNecessary(int feedbackRes, View view) {
        if (isGearLevelP()) {
            return;
        }
        vuiFeedback(feedbackRes, view);
    }

    private void vuiFeedback(int feedbackRes, View view) {
        VuiEngine.getInstance(this).vuiFeedback(view, new VuiFeedback.Builder().content(getString(feedbackRes)).build());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpcarlife.view.BaseActivity
    public HomeViewModel createViewModel() {
        return (HomeViewModel) new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_operating_guide_layout /* 2131296391 */:
                if (isGearLevelP()) {
                    EventTrackingHelper.sendMolecast(PagesEnum.CAR_LIFE_VERSATILE_SPACE, EventEnum.OPEN_HIGHLIGHT_INTRODUCE, "1");
                    AppLauncherUtil.launchIntroduce(this);
                    return;
                }
                EventTrackingHelper.sendMolecast(PagesEnum.CAR_LIFE_VERSATILE_SPACE, EventEnum.OPEN_HIGHLIGHT_INTRODUCE, "2");
                XToast.show((int) R.string.vui_feedback_introduce_video_need_p_gear);
                return;
            case R.id.btn_close /* 2131296392 */:
                finish();
                return;
            case R.id.ll_fragrance /* 2131296584 */:
                AppLauncherUtil.launchAroma(this);
                return;
            case R.id.ll_freezer /* 2131296586 */:
                AppLauncherUtil.launchFreezer(this);
                return;
            case R.id.ll_play_mode /* 2131296588 */:
                handlerResponseMode(false, AppLauncherUtil.launchMovieMode());
                return;
            case R.id.ll_power /* 2131296589 */:
                AppLauncherUtil.launchPower(this);
                return;
            case R.id.ll_sleep_mode /* 2131296591 */:
                handlerResponseMode(true, AppLauncherUtil.launchSleepMode());
                return;
            case R.id.ll_sunroof /* 2131296592 */:
                AppLauncherUtil.launchSolarRoof(this);
                return;
            default:
                return;
        }
    }

    private void handlerResponseMode(boolean sleepMode, String response) {
        response.hashCode();
        if (response.equals("success")) {
            Log.d(TAG, "enter mode success");
        } else if (response.equals("gearNotP")) {
            XToast.showShort(getString(R.string.fail_gear_not_p));
        }
        PagesEnum pagesEnum = PagesEnum.CAR_LIFE_VERSATILE_SPACE;
        EventEnum eventEnum = EventEnum.OPEN_PLAY_SLEEP_SPACE;
        Object[] objArr = new Object[2];
        objArr[0] = sleepMode ? "1" : "2";
        objArr[1] = convertResponseState2Mole(response);
        EventTrackingHelper.sendMolecast(pagesEnum, eventEnum, objArr);
    }

    public void updateSpaceCapsuleState(boolean isVisible) {
        if (isVisible) {
            this.mLlCockpitParticulars.setVisibility(0);
        } else {
            this.mLlCockpitParticulars.setVisibility(8);
        }
    }

    private String convertResponseState2Mole(String responseState) {
        responseState.hashCode();
        return !responseState.equals("success") ? !responseState.equals("gearNotP") ? "" : "2" : "1";
    }

    private void showWebp() {
        boolean isDay = isDay(getResources().getConfiguration());
        Log.d(TAG, "showWebp isDay :" + isDay);
        FrameSequenceUtil.with(this.mWebpImg).loopCount(-1).asset(isDay ? ANIM_FILE_NAME_DAY : ANIM_FILE_NAME_NIGHT).loopBehavior(1).apply();
        this.mWebpImg.setVisibility(0);
    }

    private void hideWebp() {
        FrameSequenceUtil.destroy(this.mWebpImg);
        this.mWebpImg.setVisibility(4);
    }

    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ThemeManager.isThemeChanged(newConfig)) {
            this.mLlPower.setBackground(getDrawable(this.mPowerDrawable));
            Log.i(TAG, "onConfigurationChanged==> isDayMode:" + isDay(newConfig));
            if (this.mWebpImg.getVisibility() == 0) {
                resetWebp();
            }
        }
        setTitleByFontScale();
        facilityStateThemeChanged();
    }

    private void setTitleByFontScale() {
        FontType fontType = getFontType(getResources().getConfiguration());
        Log.d(TAG, "setTitleByFontScale : " + fontType.name());
        int i = AnonymousClass2.$SwitchMap$com$xiaopeng$xpcarlife$data$FontType[fontType.ordinal()];
        if (i == 1) {
            this.mImgTitle.setImageResource(R.drawable.ic_smart_3rd_space_small);
        } else if (i == 2) {
            this.mImgTitle.setImageResource(R.drawable.ic_smart_3rd_space);
        } else {
            this.mImgTitle.setImageResource(R.drawable.ic_smart_3rd_space_small);
        }
    }

    private FontType getFontType(Configuration configuration) {
        float f = configuration.fontScale;
        Log.d(TAG, "getFontType : " + f);
        if (f == 0.9f) {
            return FontType.FONT_EXQUISITE;
        }
        if (f == 1.0f) {
            return FontType.FONT_READABLE;
        }
        return FontType.FONT_EXQUISITE;
    }

    private void facilityStateThemeChanged() {
        TextView textView = this.mAromaState;
        if (textView != null && this.mAromaStateBg != null) {
            if (textView.getText().equals(getString(R.string.smart_facility_open))) {
                this.mAromaStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_open_bg));
            } else {
                this.mAromaStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_close_bg));
            }
        }
        XTextView xTextView = this.mFreezerState;
        if (xTextView != null && this.mFreezerStateBg != null) {
            if (xTextView.getText().equals(getString(R.string.smart_facility_open))) {
                this.mFreezerStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_open_bg));
            } else {
                this.mFreezerStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_close_bg));
            }
        }
        XTextView xTextView2 = this.mPowerState;
        if (xTextView2 != null && this.mPowerStateBg != null) {
            if (xTextView2.getText().equals(getString(R.string.smart_facility_open))) {
                this.mPowerStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_open_bg));
            } else {
                this.mPowerStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_close_bg));
            }
        }
        XTextView xTextView3 = this.mSunroofState;
        if (xTextView3 == null || this.mSunroofStateBg == null) {
            return;
        }
        if (xTextView3.getText().equals(getString(R.string.smart_facility_open))) {
            this.mSunroofStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_open_bg));
        } else {
            this.mSunroofStateBg.setBackground(getDrawable(R.drawable.shape_smart_facility_close_bg));
        }
    }

    private void resetWebp() {
        hideWebp();
        showWebp();
    }

    private boolean isDay(Configuration configuration) {
        return (configuration.uiMode & 48) == 16;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void handleSchema(Intent intent) {
        XLinearLayout xLinearLayout;
        if (intent == null || !"xiaopeng".equals(intent.getScheme())) {
            return;
        }
        Uri data = intent.getData();
        Log.d(TAG, "handleSchema uri:" + data);
        if (data == null) {
            return;
        }
        if ("/home".equals(data.getPath()) && !TextUtils.isEmpty(data.getQueryParameter(VuiConstants.ELEMENT_POSITION))) {
            String queryParameter = data.getQueryParameter(VuiConstants.ELEMENT_POSITION);
            queryParameter.hashCode();
            if (queryParameter.equals("movie_mode")) {
                XLinearLayout xLinearLayout2 = this.mLlPlayMode;
                if (xLinearLayout2 != null) {
                    xLinearLayout2.post(new Runnable() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$HomeActivity$Kh07gAgJo4j_iPBAM0XX1vb-_4I
                        @Override // java.lang.Runnable
                        public final void run() {
                            HomeActivity.this.lambda$handleSchema$8$HomeActivity();
                        }
                    });
                }
            } else if (queryParameter.equals("sleep_mode") && (xLinearLayout = this.mLlSleepMode) != null) {
                xLinearLayout.post(new Runnable() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$HomeActivity$CGQZgQspWRVfQS3vnRl6ZuB-MHs
                    @Override // java.lang.Runnable
                    public final void run() {
                        HomeActivity.this.lambda$handleSchema$7$HomeActivity();
                    }
                });
            }
        }
        intent.setData(null);
    }

    public /* synthetic */ void lambda$handleSchema$7$HomeActivity() {
        VuiFloatingLayerManager.show(this.mLlSleepMode, 1);
    }

    public /* synthetic */ void lambda$handleSchema$8$HomeActivity() {
        VuiFloatingLayerManager.show(this.mLlPlayMode, 1);
    }

    private void updateAroma(boolean isVisible) {
        XLinearLayout xLinearLayout = this.mLlAroma;
        if (xLinearLayout != null) {
            if (isVisible) {
                xLinearLayout.setVisibility(0);
                this.mAromaState.setText((CharSequence) null);
                this.mAromaState.setBackground(getDrawable(R.drawable.smart_facility_state_loading));
                ((HomeViewModel) this.mViewModel).getHvacStatus();
                return;
            }
            xLinearLayout.setVisibility(8);
        }
    }

    private void updatePower(boolean isVisible) {
        View view = this.mLlPower;
        if (view != null) {
            if (isVisible) {
                view.setVisibility(0);
                this.mPowerState.setText((CharSequence) null);
                this.mPowerState.setBackground(getDrawable(R.drawable.smart_facility_state_loading));
                ((HomeViewModel) this.mViewModel).getPowerStatus();
                return;
            }
            view.setVisibility(8);
        }
    }

    private void updateFreezer(boolean isVisible) {
        XLinearLayout xLinearLayout = this.mLlFreezer;
        if (xLinearLayout != null) {
            if (isVisible) {
                xLinearLayout.setVisibility(0);
                this.mFreezerState.setText((CharSequence) null);
                this.mFreezerState.setBackground(getDrawable(R.drawable.smart_facility_state_loading));
                ((HomeViewModel) this.mViewModel).getFridgeStatus();
                return;
            }
            xLinearLayout.setVisibility(8);
        }
    }

    private void updateSunroof(boolean isVisible) {
        XLinearLayout xLinearLayout = this.mLlSunroof;
        if (xLinearLayout != null) {
            if (isVisible) {
                xLinearLayout.setVisibility(0);
                this.mSunroofState.setText((CharSequence) null);
                this.mSunroofState.setBackground(getDrawable(R.drawable.smart_facility_state_loading));
                ((HomeViewModel) this.mViewModel).getSpcStatus();
                return;
            }
            xLinearLayout.setVisibility(8);
        }
    }

    private void showDefault(boolean isVisible) {
        if (isVisible) {
            this.mImNotFacility.setVisibility(0);
            this.mTvNotFacility.setVisibility(0);
            return;
        }
        this.mImNotFacility.setVisibility(8);
        this.mTvNotFacility.setVisibility(8);
    }
}
