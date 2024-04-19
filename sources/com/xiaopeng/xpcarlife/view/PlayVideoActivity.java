package com.xiaopeng.xpcarlife.view;

import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xpcarlife.R;
import com.xiaopeng.xpcarlife.data.GearLevel;
import com.xiaopeng.xpcarlife.viewmodel.VideoViewModel;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
/* loaded from: classes.dex */
public class PlayVideoActivity extends BaseActivity<VideoViewModel> implements Player.EventListener {
    private static final String TAG = "PlayVideoActivity";
    public static final float VOLUME_DEFAULT = 1.0f;
    public static final float VOLUME_MUTE_HALF = 0.5f;
    private AudioManager mAudioManager;
    private ImageView mImageClose;
    private View mLoadingView;
    private PlayerView mPlayerView;
    private SimpleExoPlayer mSimpleExoPlayer;
    private TextView mTvTitle;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaopeng.xpcarlife.view.PlayVideoActivity.1
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int focusChange) {
            Log.i(PlayVideoActivity.TAG, "onAudioFocusChange : " + focusChange);
            if (focusChange == -1 || focusChange == -2) {
                PlayVideoActivity.this.pauseVideo();
            } else if (focusChange == -3) {
                PlayVideoActivity.this.setVideoVolume(0.5f, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
            } else if (focusChange == 1) {
                PlayVideoActivity.this.setVideoVolume(1.0f, "AUDIOFOCUS_GAIN");
                PlayVideoActivity.this.resumeVideo();
            }
        }
    };
    private Observer<GearLevel> mGearLevelObserver = new Observer() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$PlayVideoActivity$UQ3lNMC0gEbyGHgsK7Fcn574vIA
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            PlayVideoActivity.this.lambda$new$0$PlayVideoActivity((GearLevel) obj);
        }
    };

    public /* synthetic */ void lambda$new$0$PlayVideoActivity(GearLevel gearLevel) {
        if (gearLevel != GearLevel.GEAR_LEVEL_P) {
            XToast.show((int) R.string.introduce_video_break_toast);
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoVolume(float volume, String from) {
        Log.i(TAG, "setVideoVolume:" + volume + ",from=" + from);
        SimpleExoPlayer simpleExoPlayer = this.mSimpleExoPlayer;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setVolume(volume);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpcarlife.view.BaseActivity
    public VideoViewModel createViewModel() {
        return (VideoViewModel) new ViewModelProvider(this).get(VideoViewModel.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpcarlife.view.BaseActivity, com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_play_video);
        initView();
        initListener();
        showLoading();
        preparePlayer();
    }

    @Override // com.xiaopeng.xpcarlife.view.BaseActivity
    public void bindLiveData() {
        setLiveDataObserver(((VideoViewModel) this.mViewModel).getGearLevelData(), this.mGearLevelObserver);
    }

    private void initListener() {
        this.mImageClose.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$PlayVideoActivity$7FBaMK0D5CRtU52M5LyiGxXnfyg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PlayVideoActivity.this.lambda$initListener$1$PlayVideoActivity(view);
            }
        });
        this.mPlayerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() { // from class: com.xiaopeng.xpcarlife.view.-$$Lambda$PlayVideoActivity$5ioGd2dy0aHWjjC4cdb8iMZRVdw
            @Override // com.google.android.exoplayer2.ui.PlayerControlView.VisibilityListener
            public final void onVisibilityChange(int i) {
                PlayVideoActivity.this.lambda$initListener$2$PlayVideoActivity(i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$PlayVideoActivity(View v) {
        finish();
    }

    public /* synthetic */ void lambda$initListener$2$PlayVideoActivity(int visibility) {
        if (visibility == 0) {
            this.mTvTitle.setVisibility(0);
        } else {
            this.mTvTitle.setVisibility(4);
        }
    }

    private void preparePlayer() {
        SimpleExoPlayer build = new SimpleExoPlayer.Builder(this).build();
        this.mSimpleExoPlayer = build;
        build.addListener(this);
        this.mPlayerView.setPlayer(this.mSimpleExoPlayer);
        this.mSimpleExoPlayer.prepare(getMediaSource(RawResourceDataSource.buildRawResourceUri(R.raw.video)));
        this.mSimpleExoPlayer.setPlayWhenReady(true);
    }

    private MediaSource getMediaSource(Uri uri) {
        return new ProgressiveMediaSource.Factory(new DefaultDataSourceFactory(this, Util.getUserAgent(this, getPackageName()))).createMediaSource(uri);
    }

    private void initView() {
        this.mImageClose = (ImageView) findViewById(R.id.btn_close);
        this.mPlayerView = (PlayerView) findViewById(R.id.exo_player_view);
        this.mTvTitle = (TextView) findViewById(R.id.tv_title);
        this.mLoadingView = findViewById(R.id.view_loading);
        this.mAudioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        VuiView vuiView = (VuiView) this.mPlayerView.findViewById(R.id.exo_play);
        VuiView vuiView2 = (VuiView) this.mPlayerView.findViewById(R.id.exo_pause);
        VuiUtils.addCanVoiceControlProp(vuiView);
        VuiUtils.addCanVoiceControlProp(vuiView2);
        VuiUtils.addHasFeedbackProp(vuiView);
        VuiUtils.addHasFeedbackProp(vuiView2);
        VuiUtils.addCanVoiceControlProp((VuiView) this.mPlayerView.findViewById(R.id.exo_rew));
        VuiUtils.addCanVoiceControlProp((VuiView) this.mPlayerView.findViewById(R.id.exo_ffwd));
    }

    private void showLoading() {
        if (isShowLoading()) {
            return;
        }
        this.mLoadingView.setVisibility(0);
    }

    private boolean isShowLoading() {
        return this.mLoadingView.getVisibility() == 0;
    }

    private void hideLoading() {
        if (isShowLoading()) {
            this.mLoadingView.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        pauseVideo();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        resumeVideo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resumeVideo() {
        SimpleExoPlayer simpleExoPlayer = this.mSimpleExoPlayer;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pauseVideo() {
        SimpleExoPlayer simpleExoPlayer = this.mSimpleExoPlayer;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        SimpleExoPlayer simpleExoPlayer = this.mSimpleExoPlayer;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            this.mSimpleExoPlayer = null;
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Log.d(TAG, "onPlayerStateChanged , playWhenReady : " + playWhenReady + " , playbackState :" + playbackState);
        if (playbackState != 3) {
            if (playbackState != 4) {
                return;
            }
            abandonAudioFocus(this.mAudioFocusChangeListener);
            finish();
        } else if (playWhenReady) {
            hideLoading();
            requestAudioFocus(this.mAudioFocusChangeListener, 3, 2);
        } else {
            abandonAudioFocus(this.mAudioFocusChangeListener);
        }
    }

    @Override // com.xiaopeng.xpcarlife.view.BaseActivity, com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        Log.d(TAG, "onInterceptVuiEvent ");
        if (view != null) {
            VuiFloatingLayerManager.show(view);
            switch (view.getId()) {
                case R.id.exo_pause /* 2131296495 */:
                    if (!isPlaying()) {
                        tryVuiFeedBack(view, R.string.vui_pause_feedback);
                        return true;
                    }
                    Log.d(TAG, "onInterceptVuiEvent pause,  playing , not intercept");
                    break;
                case R.id.exo_play /* 2131296496 */:
                    if (isPlaying()) {
                        tryVuiFeedBack(view, R.string.vui_playing_feedback);
                        return true;
                    }
                    Log.d(TAG, "onInterceptVuiEvent play, not playing , not intercept");
                    break;
            }
        }
        return super.onInterceptVuiEvent(view, vuiEvent);
    }

    private void tryVuiFeedBack(View view, int p) {
        VuiEngine.getInstance(this).vuiFeedback(view, new VuiFeedback.Builder().content(getString(p)).build());
    }

    private boolean isPlaying() {
        SimpleExoPlayer simpleExoPlayer = this.mSimpleExoPlayer;
        return simpleExoPlayer != null && simpleExoPlayer.getPlayWhenReady() && this.mSimpleExoPlayer.getPlaybackState() == 3;
    }

    @Override // com.xiaopeng.xpcarlife.view.BaseActivity, com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        Log.d(TAG, "onVuiEvent " + vuiEvent);
        if (view != null) {
            VuiFloatingLayerManager.show(view);
        }
    }

    private int requestAudioFocus(AudioManager.OnAudioFocusChangeListener listener, int stream, int gain) {
        if (isAudioManagerNull()) {
            return 0;
        }
        return this.mAudioManager.requestAudioFocus(listener, stream, gain);
    }

    private int abandonAudioFocus(AudioManager.OnAudioFocusChangeListener listener) {
        if (isAudioManagerNull()) {
            return 0;
        }
        return this.mAudioManager.abandonAudioFocus(listener);
    }

    public boolean isAudioManagerNull() {
        return this.mAudioManager == null;
    }
}
