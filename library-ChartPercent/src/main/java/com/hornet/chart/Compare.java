package com.hornet.chart;

import java.util.Comparator;

public class Compare implements Comparator<ArcData> {

    @Override
    public int compare(ArcData lhs, ArcData rhs) {
        return (int) (rhs.getData() - lhs.getData());
    }


}
