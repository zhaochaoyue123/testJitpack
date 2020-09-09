package com.testAlipay.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.replugin.myplugin.mytestbeanlibrary.TestBean;
import com.replugin.myplugin.mytestgithublibrary.AbcdBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.img_zhangdan)
    ImageView mImgZhangdan;
    @BindView(R.id.img_zhangdan_txt)
    TextView mImgZhangdanTxt;
    @BindView(R.id.toolbar1)
    View toolbar1;
    @BindView(R.id.toolbar2)
    View toolbar2;
    @BindView(R.id.jiahao)
    ImageView mJiahao;
    @BindView(R.id.tongxunlu)
    ImageView mTongxunlu;
    @BindView(R.id.img_shaomiao)
    ImageView mImgShaomiao;
    @BindView(R.id.img_fukuang)
    ImageView mImgFukuang;
    @BindView(R.id.img_search)
    ImageView mImgSearch;
    @BindView(R.id.img_zhaoxiang)
    ImageView mImgZhaoxiang;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.activity_main)
    CoordinatorLayout mActivityMain;

    private MyAdapter adapter;

    @BindView(R.id.fragment_tab_smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new MyAdapter(this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(adapter);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                System.out.println("verticalOffset = [" + verticalOffset + "]" + "{" + Math.abs(verticalOffset) + "}" + "{:" + appBarLayout.getTotalScrollRange() + "}");
                if (verticalOffset == 0) {
                    //完全展开
                    toolbar1.setVisibility(View.VISIBLE);
                    toolbar2.setVisibility(View.GONE);
                    setToolbar1Alpha(255);
                } else if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    //appBarLayout.getTotalScrollRange() == 200
                    //完全折叠
                    toolbar1.setVisibility(View.GONE);
                    toolbar2.setVisibility(View.VISIBLE);
                    setToolbar2Alpha(255);
                } else {//0~200上滑下滑
                    if (toolbar1.getVisibility() == View.VISIBLE) {
//                        //操作Toolbar1
                        int alpha = 300 - 155 - Math.abs(verticalOffset);
                        Log.i("alpha:", alpha + "");
                        setToolbar1Alpha(alpha);

                    } else if (toolbar2.getVisibility() == View.VISIBLE) {
                        if (Math.abs(verticalOffset) > 0 && Math.abs(verticalOffset) < 200) {
                            toolbar1.setVisibility(View.VISIBLE);
                            toolbar2.setVisibility(View.GONE);
                            setToolbar1Alpha(255);
                        }
//                        //操作Toolbar2
                        int alpha = (int) (255 * (Math.abs(verticalOffset) / 100f));
                        setToolbar2Alpha(alpha);
                    }
                }
            }
        });

        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (adapter.getItemCount() == 0) {

                } else {

                }
                smartRefreshLayout.finishLoadMore(500);
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });

        ArrayList<DepartmentTreeEntity> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DepartmentTreeEntity entity = new DepartmentTreeEntity();
            entity.setTitle(i + "名字");
            entity.setId(i + "");
            list.add(entity);
        }
        adapter.setData(list);
        //AbcdBean a = new AbcdBean();
        TestBean t = new TestBean();
        AbcdBean a = t.getBean();
    }

    public void dismissRefreshLoad() {
        if (adapter != null) {
            smartRefreshLayout.setVisibility(adapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
        }
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
    }


    private void setToolbar1Alpha(int alpha) {
        mImgZhangdan.getDrawable().setAlpha(alpha);
        mImgZhangdanTxt.setTextColor(Color.argb(alpha, 255, 255, 255));
        mTongxunlu.getDrawable().setAlpha(alpha);
        mJiahao.getDrawable().setAlpha(alpha);
    }

    private void setToolbar2Alpha(int alpha) {
        mImgShaomiao.getDrawable().setAlpha(alpha);
        mImgFukuang.getDrawable().setAlpha(alpha);
        mImgSearch.getDrawable().setAlpha(alpha);
        mImgZhaoxiang.getDrawable().setAlpha(alpha);
    }
}