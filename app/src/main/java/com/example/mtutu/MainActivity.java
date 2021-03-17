package com.example.mtutu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BarChart stackedBar;
    int[] color = new int[] {Color.RED,Color.GREEN,Color.BLUE};
    UsageStatsManager usageStatsManager;
    List<UsageStats> queryUsageStats;
    RecyclerView recyclerView;
    SpendTimeStatictisAdapter mSpendTimeAdapter;
    List<AppModelSpendTime> mListAppSpendTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recy);
        mListAppSpendTime = new ArrayList<>();
        mListAppSpendTime.add(new AppModelSpendTime(30,70,200));
        mListAppSpendTime.add(new AppModelSpendTime(50,90,230));
        mListAppSpendTime.add(new AppModelSpendTime(10,70,290));
        mListAppSpendTime.add(new AppModelSpendTime(35,50,180));
        mSpendTimeAdapter = new SpendTimeStatictisAdapter(mListAppSpendTime);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayout.HORIZONTAL,false);
        recyclerView.setAdapter(mSpendTimeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    public static class SpendTimeStatictisAdapter extends RecyclerView.Adapter<SpendTimeStatictisAdapter.ItemAppHolder> {

        private List<AppModelSpendTime> mAppList;

        public SpendTimeStatictisAdapter(List<AppModelSpendTime> noteList) {
            mAppList = noteList;
        }

        @NonNull
        @Override
        public ItemAppHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemAppHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_item_time_use_app, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemAppHolder holder, int position) {
            holder.bindData(mAppList.get(position));
        }


        @Override
        public int getItemCount() {
            return mAppList != null ? mAppList.size() : 0;
        }

        class ItemAppHolder extends RecyclerView.ViewHolder {
            private ProgressBar pb1,pb2,pb3;
            private FrameLayout linearLayout;


            @SuppressLint("CutPasteId")
            public ItemAppHolder(View itemView) {
                super(itemView);
                pb1 = itemView.findViewById(R.id.progressBar);
                pb2 = itemView.findViewById(R.id.progressBar1);
                pb3 = itemView.findViewById(R.id.progressBar2);
                linearLayout = itemView.findViewById(R.id.frame);
            }

            public void bindData(AppModelSpendTime appModelSpendTime) {
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(80,appModelSpendTime.mPercentOfAll));
                linearLayout.setBackgroundColor(Color.WHITE);
                pb2.setProgress(appModelSpendTime.mPercentMore);
                pb3.setProgress(appModelSpendTime.mPercentSmall);

            }
        }
    }
}