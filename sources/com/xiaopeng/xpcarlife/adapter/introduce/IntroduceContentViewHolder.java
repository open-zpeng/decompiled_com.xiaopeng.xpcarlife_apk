package com.xiaopeng.xpcarlife.adapter.introduce;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.xpcarlife.R;
/* loaded from: classes.dex */
public class IntroduceContentViewHolder extends RecyclerView.ViewHolder {
    private TextView mTvContent;

    public IntroduceContentViewHolder(View itemView) {
        super(itemView);
        this.mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
    }

    public void updateContent(int resourceID) {
        this.mTvContent.setText(resourceID);
    }
}
