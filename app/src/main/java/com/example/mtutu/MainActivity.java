package com.example.mtutu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    UsageStatsManager usageStatsManager;
    List<UsageStats> queryUsageStats;
    static long total = 0 ;
    static List<AppModelSpendTime> listApp,mListAppSpendTime;
    RecyclerView rvStatisticSpendTime;
    SpendTimeStatictisAdapter mSpendTimeAdapter;
    List<AppUsageInfo> smallInfoList;
    static String strMsg;



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
        mListAppSpendTime.add(new AppModelSpendTime(30,70,160));
        mListAppSpendTime.add(new AppModelSpendTime(50,90,120));
        mListAppSpendTime.add(new AppModelSpendTime(10,70,140));
        mListAppSpendTime.add(new AppModelSpendTime(35,50,110));
        mSpendTimeAdapter = new SpendTimeStatictisAdapter(mListAppSpendTime,getApplicationContext());
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayout.HORIZONTAL,false);
        recyclerView.setAdapter(mSpendTimeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public static class SpendTimeStatictisAdapter extends RecyclerView.Adapter<SpendTimeStatictisAdapter.ItemAppHolder> {

        private List<AppModelSpendTime> mAppList;
        Context mContext;

        public SpendTimeStatictisAdapter(List<AppModelSpendTime> noteList,Context context) {
            mAppList = noteList;
            mContext = context;
        }

        @NonNull
        @Override
        public ItemAppHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemAppHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_item_time_use_app, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemAppHolder holder, int position) {
            holder.bindData(mAppList.get(position),mContext);
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

            public void bindData(AppModelSpendTime appModelSpendTime,Context context) {
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(convertDPToPixel(24,context),convertDPToPixel(appModelSpendTime.getmPercentOfAll(),context)));
                linearLayout.setBackgroundColor(Color.WHITE);
                pb2.setProgress(appModelSpendTime.mPercentMore);
                pb3.setProgress(appModelSpendTime.mPercentSmall);

            }
        }
    }
    public static int convertDPToPixel(int dp,Context context) {
        return  (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static int getHeightForColumn(int coefficientTime,int totalSpendTime,int heightChart) {
        int heightOfPer = heightChart/100 ;
        return totalSpendTime/coefficientTime * heightOfPer;
    }

    public static void addToModel(int firtsColumn, int secondColumn, int totalSpend,List<AppUsageInfo> list1) {
        List<AppModelSpendTime>  list = new ArrayList<>();
        for(AppUsageInfo appUsageInfo : list1) {
            list.add(new AppModelSpendTime((firtsColumn/totalSpend)*100,
                    (secondColumn/totalSpend)*100 + (firtsColumn/totalSpend)*100,
                    getHeightForColumn(360,getAllSpendTime(list1),170)));
        }
    }

    public static int getAllSpendTime(List<AppUsageInfo> list) {
        int total = 0;
        for(AppUsageInfo s : list) {
            total += s.timeInForeground;
        }
        return total;
    }

    public static int getPerForColumn(long time,long total) {
        return (int) (time/total)*100;
    }

    class AppUsageInfo {
        Drawable appIcon; // You may add get this usage data also, if you wish.
        String appName, packageName;
        long timeInForeground;
        int launchCount;

        AppUsageInfo(String pName) {
            this.packageName=pName;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void getUsageStatistics(long start_time, long end_time,Context context) {

        UsageEvents.Event currentEvent;
        //  List<UsageEvents.Event> allEvents = new ArrayList<>();
        HashMap<String, AppUsageInfo> map = new HashMap<>();
        HashMap<String, List<UsageEvents.Event>> sameEvents = new HashMap<>();

        UsageStatsManager mUsageStatsManager = (UsageStatsManager)
                context.getSystemService(Context.USAGE_STATS_SERVICE);

        if (mUsageStatsManager != null) {
            // Get all apps data from starting time to end time
            UsageEvents usageEvents = mUsageStatsManager.queryEvents(start_time, end_time);

            // Put these data into the map
            while (usageEvents.hasNextEvent()) {
                currentEvent = new UsageEvents.Event();
                usageEvents.getNextEvent(currentEvent);
                if (currentEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED ||
                        currentEvent.getEventType() == UsageEvents.Event.ACTIVITY_PAUSED) {
                    //  allEvents.add(currentEvent);
                    String key = currentEvent.getPackageName();
                    if (map.get(key) == null) {
                        map.put(key, new AppUsageInfo(key));
                        sameEvents.put(key,new ArrayList<UsageEvents.Event>());
                    }
                    sameEvents.get(key).add(currentEvent);
                }
            }

            // Traverse through each app data which is grouped together and count launch, calculate duration
            for (Map.Entry<String,List<UsageEvents.Event>> entry : sameEvents.entrySet()) {
                int totalEvents = entry.getValue().size();
                if (totalEvents > 1) {
                    for (int i = 0; i < totalEvents - 1; i++) {
                        UsageEvents.Event E0 = entry.getValue().get(i);
                        UsageEvents.Event E1 = entry.getValue().get(i + 1);

                        if (E1.getEventType() == 1 || E0.getEventType() == 1) {
                            map.get(E1.getPackageName()).launchCount++;
                        }

                        if (E0.getEventType() == 1 && E1.getEventType() == 2) {
                            long diff = E1.getTimeStamp() - E0.getTimeStamp();
                            map.get(E0.getPackageName()).timeInForeground += diff;
                        }
                    }
                }

                // If First eventtype is ACTIVITY_PAUSED then added the difference of start_time and Event occuring time because the application is already running.
                if (entry.getValue().get(0).getEventType() == 2) {
                    long diff = entry.getValue().get(0).getTimeStamp() - start_time;
                    map.get(entry.getValue().get(0).getPackageName()).timeInForeground += diff;
                }

                // If Last eventtype is ACTIVITY_RESUMED then added the difference of end_time and Event occuring time because the application is still running .
                if (entry.getValue().get(totalEvents - 1).getEventType() == 1) {
                    long diff = end_time - entry.getValue().get(totalEvents - 1).getTimeStamp();
                    map.get(entry.getValue().get(totalEvents - 1).getPackageName()).timeInForeground += diff;
                }
            }

            smallInfoList = new ArrayList<>(map.values());
            strMsg = "";
            // Concatenating data to show in a text view. You may do according to your requirement
            Collections.sort(smallInfoList, new Comparator<AppUsageInfo>() {
                @Override
                public int compare(AppUsageInfo o1, AppUsageInfo o2) {
                    return o2.launchCount - o1.launchCount;
                }
            });
            getTotalSpendTime(smallInfoList);
            listApp = new ArrayList<>();
            for (int i = 0; i< 5;i++) {
                long x = smallInfoList.get(i).launchCount;
                long xx = (long) ((x*1.0/total)*100);
                listApp.add(new AppModelSpendTime(smallInfoList.get(i).packageName
                        ,smallInfoList.get(i).appName
                        , (int) xx
                        , smallInfoList.get(i).launchCount + " phút"
                ));
            }
//             for (AppUsageInfo appUsageInfo : smallInfoList) {
//                 Log.e("Đây là khung giờ từ :",  appUsageInfo.appName + " : "+appUsageInfo.launchCount );
//             }



        } else {
            Toast.makeText(context, "Sorry...", Toast.LENGTH_SHORT).show();
        }

    }
    public void getTotalSpendTime(List<AppUsageInfo> list) {
        if(total == 0 ) {
            for (AppUsageInfo stats : list) {
                total += stats.launchCount;
            }
        }
        Log.e("Tổng thời gian: ", String.valueOf(ConvertToHours(total)));
    }
    public String ConvertToHours(Long totalTime) {
        long x = totalTime;
        Long hours = TimeUnit.MILLISECONDS.toHours(x);
        x -= TimeUnit.HOURS.toMillis(hours);
        Long minutes = TimeUnit.MILLISECONDS.toMinutes(x);
        x -= TimeUnit.MINUTES.toMillis(minutes);
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(x);
        return hours+"h"+minutes+"min"+seconds+"s";
    }
}