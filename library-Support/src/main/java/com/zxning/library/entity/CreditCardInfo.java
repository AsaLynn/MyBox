package com.zxning.library.entity;


import com.zxning.library.db.domain.BaseColumn;

public class CreditCardInfo
        extends BaseColumn {

    public String name;//卡名称.

    public String cardNo;//卡号.

    public String user;//使用人.

    public String statementDate;//账单日

    public String repaymentDate;//还款日.

    public String creditLimit;//信用额度.

    public String currentArrears;//本期欠款

    public String totalDebt;//总欠款

    public String timesDebt;//分期额度.

    public String totalTimes;//分期总数.

    public String repaidTimes;//已经还款的次数.

    public String bankCode;//信用额度.

}