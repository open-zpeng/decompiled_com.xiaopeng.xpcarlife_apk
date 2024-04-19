package com.xiaopeng.xpcarlife.adapter.introduce;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.xpcarlife.R;
import com.xiaopeng.xpcarlife.data.IntroduceEntity;
import java.util.List;
/* loaded from: classes.dex */
public class IntroduceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "IntroduceAdapter";
    private List<IntroduceEntity> mList;

    public IntroduceAdapter(List<IntroduceEntity> list) {
        this.mList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != 0) {
            if (viewType != 1) {
                if (viewType == 2) {
                    return new IntroduceImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_introduce_img, parent, false));
                }
                Log.d(TAG, "type is error");
                return null;
            }
            return new IntroduceContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_introduce_content, parent, false));
        }
        return new IntroduceTitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_introduce_title, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int resourceID = this.mList.get(position).getResourceID();
        if (holder instanceof IntroduceTitleViewHolder) {
            ((IntroduceTitleViewHolder) holder).updateTitle(resourceID);
        } else if (holder instanceof IntroduceContentViewHolder) {
            ((IntroduceContentViewHolder) holder).updateContent(resourceID);
        } else if (holder instanceof IntroduceImageViewHolder) {
            ((IntroduceImageViewHolder) holder).updateImage(resourceID);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<IntroduceEntity> list = this.mList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return this.mList.get(position).getType();
    }
}
