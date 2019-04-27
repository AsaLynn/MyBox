package com.hornet.chart;

/**
 * 一条数据信息.
 */
public class MagnificentChartItem {

    public int color;
    public int value;
    public String title;
    public float fValue;

    public MagnificentChartItem(String title, int value, int color) {
        this.color = color;
        this.value = value;
        this.title = title;
    }

    public MagnificentChartItem(float fValue, int color) {
        this.fValue = fValue;
        this.color = color;
    }

}
