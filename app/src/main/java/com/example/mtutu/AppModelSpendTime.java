package com.example.mtutu;

import java.util.Comparator;

public class AppModelSpendTime implements Comparator<AppModelSpendTime> {
    public String mPackageName;
    public String mAppName;
    public int mPercentOfAll;
    public String mHourSpend;

    public AppModelSpendTime() {
    }

    public AppModelSpendTime(String mPackageName, String mAppName, int mPercentOfAll, String mHourSpend) {
        this.mPackageName = mPackageName;
        this.mAppName = mAppName;
        this.mPercentOfAll = mPercentOfAll;
        this.mHourSpend = mHourSpend;
    }

    public String getmPackageName() {
        return mPackageName;
    }

    public void setmPackageName(String PackageName) {
        this.mPackageName = PackageName;
    }

    public String getmAppName() {
        return mAppName;
    }

    public void setmAppName(String AppName) {
        this.mAppName = AppName;
    }

    public int getmPercentOfAll() {
        return mPercentOfAll;
    }

    public void setmPercentOfAll(int PercentOfAll) {
        this.mPercentOfAll = PercentOfAll;
    }

    public String getmHourSpend() {
        return mHourSpend;
    }

    public void setmHourSpend(String HourSpend) {
        this.mHourSpend = HourSpend;
    }

    @Override
    public int compare(AppModelSpendTime o1, AppModelSpendTime o2) {
        return o1.getmPercentOfAll() - o2.getmPercentOfAll();
    }
}

