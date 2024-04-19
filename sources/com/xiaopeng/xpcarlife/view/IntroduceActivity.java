package com.xiaopeng.xpcarlife.view;

import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.xpcarlife.R;
import com.xiaopeng.xpcarlife.adapter.introduce.IntroduceAdapter;
import com.xiaopeng.xpcarlife.data.IntroduceEntity;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.app.delegate.XActivityBind;
import com.xiaopeng.xui.app.delegate.XActivityTemplate;
import com.xiaopeng.xui.widget.dialogview.XDialogView;
import java.util.ArrayList;
@XActivityBind(3)
/* loaded from: classes.dex */
public class IntroduceActivity extends XActivity {
    private ArrayList<IntroduceEntity> mIntroduceEntities;
    private RecyclerView mRecyclerView;
    private XDialogView mXDialogView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initDialogView();
        initView();
        initRecycleViewSetting();
    }

    private void initDialogView() {
        XDialogView xDialogView = new XDialogView(this, R.style.XDialogView_Large_Introduce);
        this.mXDialogView = xDialogView;
        setContentView(xDialogView.getContentView());
        this.mXDialogView.setCustomView(R.layout.view_introduce, false);
        this.mXDialogView.setTitle(R.string.introduce_dialog_title);
        ((XActivityTemplate.Dialog) getActivityTemplate()).useXDialogView(this.mXDialogView);
    }

    private void initData() {
        this.mIntroduceEntities = new ArrayList<>();
        createIntroduceItem(R.drawable.ic_fragrance, R.string.introduce_item_title_one, R.string.introduce_item_content_one);
        createIntroduceItem(R.drawable.ic_refrigerator, R.string.introduce_item_title_two, R.string.introduce_item_content_two);
        createIntroduceItem(R.drawable.ic_roof, R.string.introduce_item_title_three, R.string.introduce_item_content_three);
    }

    private void createIntroduceItem(int imgID, int titleID, int contentID) {
        IntroduceEntity introduceEntity = new IntroduceEntity(imgID, 2);
        IntroduceEntity introduceEntity2 = new IntroduceEntity(titleID, 0);
        IntroduceEntity introduceEntity3 = new IntroduceEntity(contentID, 1);
        this.mIntroduceEntities.add(introduceEntity);
        this.mIntroduceEntities.add(introduceEntity2);
        this.mIntroduceEntities.add(introduceEntity3);
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_introduce);
        this.mRecyclerView = recyclerView;
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
    }

    private void initRecycleViewSetting() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mRecyclerView.setAdapter(new IntroduceAdapter(this.mIntroduceEntities));
    }
}
