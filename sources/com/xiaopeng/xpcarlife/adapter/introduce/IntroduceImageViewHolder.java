package com.xiaopeng.xpcarlife.adapter.introduce;

import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.xpcarlife.R;
/* loaded from: classes.dex */
public class IntroduceImageViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;

    public IntroduceImageViewHolder(View itemView) {
        super(itemView);
        this.mImageView = (ImageView) itemView.findViewById(R.id.img_introduce);
    }

    public void updateImage(int resourceId) {
        this.mImageView.setImageResource(resourceId);
    }
}
