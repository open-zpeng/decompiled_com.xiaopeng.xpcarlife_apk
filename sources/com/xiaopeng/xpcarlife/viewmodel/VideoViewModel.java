package com.xiaopeng.xpcarlife.viewmodel;

import android.app.Application;
import com.xiaopeng.xpcarlife.model.VideoModel;
/* loaded from: classes.dex */
public class VideoViewModel extends BaseViewModel<VideoModel> {
    public VideoViewModel(Application application) {
        super(application);
    }

    @Override // com.xiaopeng.xpcarlife.viewmodel.BaseViewModel
    public VideoModel createModel() {
        return new VideoModel();
    }
}
