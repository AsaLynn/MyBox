package com.zxning.library.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 分期信息.可进行传递.
 */
public class InstallmentInfo implements Parcelable {

    public String name;
    public String title;
    public String installmentAmount;//installment_amount
    public String installmentNum;//installment_num
    public String poundageRate;//poundage_rate
    public String monthsPoundage;//months_poundage
    public String sumPoundage;//sum_poundage
    public String monthsRepayment;//months_repayment
    public String currentNum;//current_num
    public String currentState;//current_state
    public String currentTime;//current_data
    public String startTime;//start_time
    public String endTime;//end_time
    public String cardNo;//card_no
    public String bankCode;//bank_code
    public String cardId;

    public InstallmentInfo() {
    }

    public InstallmentInfo(String name, int image) {
        this.name = name;
    }

    protected InstallmentInfo(Parcel in) {
        name = in.readString();
        title = in.readString();
        installmentAmount = in.readString();
        installmentNum = in.readString();
        poundageRate = in.readString();
        monthsPoundage = in.readString();
        sumPoundage = in.readString();
        monthsRepayment = in.readString();
        currentNum = in.readString();
        currentState = in.readString();
        currentTime = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        cardNo = in.readString();
        bankCode = in.readString();
        cardId= in.readString();
    }

    public static final Creator<InstallmentInfo> CREATOR = new Creator<InstallmentInfo>() {
        @Override
        public InstallmentInfo createFromParcel(Parcel in) {
            return new InstallmentInfo(in);
        }

        @Override
        public InstallmentInfo[] newArray(int size) {
            return new InstallmentInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(installmentAmount);
        dest.writeString(installmentNum);
        dest.writeString(poundageRate);
        dest.writeString(monthsPoundage);
        dest.writeString(sumPoundage);
        dest.writeString(monthsRepayment);
        dest.writeString(currentNum);
        dest.writeString(currentState);
        dest.writeString(currentTime);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(cardNo);
        dest.writeString(bankCode);
        dest.writeString(cardId);
    }
}
