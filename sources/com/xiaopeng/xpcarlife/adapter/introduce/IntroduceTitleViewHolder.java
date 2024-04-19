package com.xiaopeng.xpcarlife.adapter.introduce;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.xpcarlife.R;
/* loaded from: classes.dex */
public class IntroduceTitleViewHolder extends RecyclerView.ViewHolder {
    private TextView mTvTitle;

    public IntroduceTitleViewHolder(View itemView) {
        super(itemView);
        this.mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
    }

    public void updateTitle(int resourceID) {
        this.mTvTitle.setText(resourceID);
    }
}
