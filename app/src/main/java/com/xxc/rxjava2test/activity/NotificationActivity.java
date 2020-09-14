package com.xxc.rxjava2test.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.xxc.rxjava2test.R;
import com.xxc.rxjava2test.adapter.HomeMovieAdapter;
import com.xxc.rxjava2test.adapter.StackAdapter;
import com.xxc.rxjava2test.widget.CustomLayoutManager;
import com.xxc.rxjava2test.widget.StackLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.rv_main_content)
    RecyclerView rvMainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            dataList.add(i + "");
        }
        HomeMovieAdapter adapter = new HomeMovieAdapter(this, dataList);
        rvMainContent.setAdapter(adapter);
        rvMainContent.setLayoutManager(new CustomLayoutManager(this));

//        StackAdapter stackAdapter = new StackAdapter(this, dataList);
//        rvMainContent.setAdapter(stackAdapter);
//        rvMainContent.setLayoutManager(new StackLayoutManager(this));
    }
}
