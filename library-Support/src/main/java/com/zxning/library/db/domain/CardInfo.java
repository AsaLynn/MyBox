package com.zxning.library.db.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_card")
public class CardInfo
        extends BaseColumn {

    @DatabaseField(index = true, columnName = "name", unique = true, canBeNull = false)
    public String name;//卡名称.

    @DatabaseField(canBeNull = false, columnName = "cardNo")
    public String cardNo;//卡号.

    @DatabaseField(canBeNull = false, columnName = "user")
    public String user;//使用人.

    @DatabaseField(canBeNull = false, columnName = "statementDate")
    public String statementDate;//账单日

    @DatabaseField(canBeNull = false, columnName = "repaymentDate")
    public String repaymentDate;//还款日.

    @DatabaseField(canBeNull = false, columnName = "creditLimit")
    public String creditLimit;//信用额度.

    @DatabaseField(canBeNull = false, columnName = "currentArrears")
    public String currentArrears;//本期欠款

    @DatabaseField(canBeNull = false, columnName = "totalDebt")
    public String totalDebt;//总欠款

    @DatabaseField(canBeNull = false, columnName = "timesDebt")
    public String timesDebt;//分期额度.

    @DatabaseField(canBeNull = false, columnName = "totalTimes")
    public String totalTimes;//分期总数.

    @DatabaseField(canBeNull = false, columnName = "repaidTimes")
    public String repaidTimes;//已经还款的次数.

    /*@DatabaseField(canBeNull = false, columnName = "currentMoney")
    public String currentMoney;//余额.

    */

    /*@Override
    public String toString() {
        return "CardInfo{" +
                "name='" + name + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", currentMoney='" + currentMoney + '\'' +
                ", user='" + user + '\'' +
                ", creditLimit='" + creditLimit + '\'' +
                ", repaymentDate='" + repaymentDate + '\'' +
                ", statementDate='" + statementDate + '\'' +
                ", timesDebt='" + timesDebt + '\'' +
                ", totalDebt='" + totalDebt + '\'' +
                ", totalTimes='" + totalTimes + '\'' +
                ", repaidTimes='" + repaidTimes + '\'' +
                ", currentArrears='" + currentArrears + '\'' +
                '}';
    }*/
}