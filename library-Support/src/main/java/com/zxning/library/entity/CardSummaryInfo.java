package com.zxning.library.entity;

import java.util.List;

/**
 * 信用卡总信息.
 */
public class CardSummaryInfo {
    public String totalCreditLimit;
    public String totalNum;
    public String activatedNum;
    public String inactiveNum;
    public List<CreditCardInfo> cardInfos;
}
