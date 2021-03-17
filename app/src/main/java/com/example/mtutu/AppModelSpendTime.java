package com.example.mtutu;

import java.util.Comparator;

public class AppModelSpendTime implements Comparator<AppModelSpendTime> {
    public int mPercentSmall;
    public int mPercentMore;
    public int mPercentOfAll;

    public AppModelSpendTime(int mPercentSmall, int mPercentMore, int mPercentOfAll) {
        this.mPercentSmall = mPercentSmall;
        this.mPercentMore = mPercentMore;
        this.mPercentOfAll = mPercentOfAll;
    }

    public int getmPercentSmall() {
        return mPercentSmall;
    }

    public void setmPercentSmall(int mPercentSmall) {
        this.mPercentSmall = mPercentSmall;
    }

    public int getmPercentMore() {
        return mPercentMore;
    }

    public void setmPercentMore(int mPercentMore) {
        this.mPercentMore = mPercentMore;
    }

    public int getmPercentOfAll() {
        return mPercentOfAll;
    }

    public void setmPercentOfAll(int mPercentOfAll) {
        this.mPercentOfAll = mPercentOfAll;
    }

    public AppModelSpendTime() {
    }


    @Override
    public int compare(AppModelSpendTime o1, AppModelSpendTime o2) {
        return o1.getmPercentOfAll() - o2.getmPercentOfAll();
    }
}

