package com.zxning.library.db.dphelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxning.library.constant.Name;
import com.zxning.library.entity.AccountInfo;
import com.zxning.library.entity.BillInfo;
import com.zxning.library.entity.CardSummaryInfo;
import com.zxning.library.entity.CreditCardInfo;
import com.zxning.library.entity.InstallmentInfo;
import com.zxning.library.entity.LendInfo;
import com.zxning.library.entity.MeInfo;
import com.zxning.library.entity.MeItemInfo;
import com.zxning.library.entity.ReimbursementInofo;
import com.zxning.library.tool.ZDateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 个人中心中我的整个模块的数据库操作，该数据库文件在sd卡根目录下的databases文件夹。
 */
public class MyDBHelper {

    //查询账单列表.
    public static List<BillInfo> selectAllBillInfo(Context context) {
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        updateCurrentBillInfo(db);
        List<BillInfo> list = new ArrayList<>();
        //String sql = "select * from table_bill_item where has_bill = 1 order by due_day";
        String sql = "select * from table_bill_item where has_bill = 1 order by repayment_date";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            BillInfo info = new BillInfo();
            info.cardId = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CARD_ID));
            info.monthBillDate = cursor.getString(cursor.getColumnIndex(Name.DB.Table.MONTH_BILL_DATE));
            info.bankName = cursor.getString(cursor.getColumnIndex(Name.DB.Table.NAME));
            CreditCardInfo creditCardInfo = selectSingleCreditCardInfoByCardId(context, info.cardId);
            info.bankCode = creditCardInfo.bankCode;
            info.billData = creditCardInfo.statementDate;
            info.residualAmount = cursor.getString(cursor.getColumnIndex(Name.DB.Table.RESIDUAL_AMOUNT));
            info.dueDay = cursor.getString(cursor.getColumnIndex(Name.DB.Table.DUE_DAY));
            info.shouldRepaymentAmount = cursor.getString(cursor.getColumnIndex(Name.DB.Table.SHOULD_REPAYMENT_AMOUNT));
            info.repaymentDate
                    = ZDateUtil.StringToString(cursor.getString(cursor.getColumnIndex(Name.DB.Table.REPAYMENT_DATE)),
                    ZDateUtil.DateStyle.YYYY_MM_DD
            );
            info.freeDay = cursor.getString(cursor.getColumnIndex(Name.DB.Table.FREE_DAY));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    //更新剩余还款天数!
    private static void updateCurrentBillInfo(SQLiteDatabase db) {
        Date todayDate = new Date();
        String sql = "select * from table_bill_item where has_bill = 1";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String cardId = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CARD_ID));
            String repaymentDate
                    = ZDateUtil.StringToString(cursor.getString(cursor.getColumnIndex(Name.DB.Table.REPAYMENT_DATE)),
                    ZDateUtil.DateStyle.YYYY_MM_DD
            );
            Date repayDate = ZDateUtil.StringToDate(repaymentDate);
            int intervalDays = ZDateUtil.getIntervalDays(todayDate, repayDate);
            String sql2 = "update table_bill_item set due_day = " + intervalDays +
                    " and has_bill = 1 " +
                    "and card_id = " + cardId;
            db.execSQL(sql2);
        }
    }

    //信用卡分期列表.zgydhgz
    public static List<InstallmentInfo> selectAllInstallmentInfo(Context context) {
        List<InstallmentInfo> list = new ArrayList<>();
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        String sql = "select * from table_installment where current_state = 1";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            InstallmentInfo info = new InstallmentInfo();
            info.title = cursor.getString(cursor.getColumnIndex(Name.DB.Table.TITLE));
            info.cardId = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CARD_ID));
            CreditCardInfo creditCardInfo = selectSingleCreditCardInfoByCardId(context, info.cardId);
            info.name = creditCardInfo.name;
            info.cardNo = creditCardInfo.cardNo;
            info.bankCode = creditCardInfo.bankCode;
            info.currentNum = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CURRENT_NUM));
            info.currentState = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CURRENT_STATE));

            info.currentTime
                    = ZDateUtil.StringToString(cursor.getString(cursor.getColumnIndex(Name.DB.Table.CURRENT_TIME)),
                    ZDateUtil.DateStyle.YYYY_MM_DD
            );

            info.startTime
                    = ZDateUtil.StringToString(cursor.getString(cursor.getColumnIndex(Name.DB.Table.START_TIME)),
                    ZDateUtil.DateStyle.YYYY_MM_DD
            );

            info.endTime
                    = ZDateUtil.StringToString(cursor.getString(cursor.getColumnIndex(Name.DB.Table.END_TIME)),
                    ZDateUtil.DateStyle.YYYY_MM_DD
            );

            info.installmentAmount = cursor.getString(cursor.getColumnIndex(Name.DB.Table.INSTALLMENT_AMOUNT));
            info.monthsPoundage = cursor.getString(cursor.getColumnIndex(Name.DB.Table.MONTHS_POUNDAGE));
            info.installmentNum = cursor.getString(cursor.getColumnIndex(Name.DB.Table.INSTALLMENT_NUM));
            info.monthsRepayment = cursor.getString(cursor.getColumnIndex(Name.DB.Table.MONTHS_REPAYMENT));
            info.poundageRate = cursor.getString(cursor.getColumnIndex(Name.DB.Table.POUNDAGE_RATE));
            info.sumPoundage = cursor.getString(cursor.getColumnIndex(Name.DB.Table.SUM_POUNDAGE));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    //借款人还款列表.
    public static List<ReimbursementInofo> selectReimbursementInofoByName(Context context, String name) {
        List<ReimbursementInofo> list = new ArrayList<>();
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        Cursor cursor = db.rawQuery("select * from table_borrower where lend_people = ?", new String[]{name});
        if (null != cursor) {
            while (cursor.moveToNext()) {
                ReimbursementInofo info = new ReimbursementInofo();
                info.name = cursor.getString(cursor.getColumnIndex(Name.DB.Table.LEND_PEOPLE));
                info.state = cursor.getString(cursor.getColumnIndex(Name.DB.Table.STATE));
                info.time = cursor.getString(cursor.getColumnIndex(Name.DB.Table.TIME_REIMBURSEMENT));
                info.monney = cursor.getString(cursor.getColumnIndex(Name.DB.Table.MONTH_REIMBURSEMENT));
                list.add(info);
            }
        }
        cursor.close();
        return list;
    }

    //借款人滑动列表.0,全部  1,未还,   2已经换
    public static List<LendInfo> selectAllLendInfo(Context context, int type) {
        List<LendInfo> list = new ArrayList<>();
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        Cursor cursor = null;
        if (0 == type) {
            cursor = db.rawQuery("select * from table_my_lend", null);
        } else if (1 == type) {
            cursor = db.rawQuery("select * from table_my_lend where state = '未还'", null);
        } else if (2 == type) {
            cursor = db.rawQuery("select * from table_my_lend where state = '已还'", null);
        } else {
            cursor = db.rawQuery("select * from table_my_lend", null);
        }
        while (cursor.moveToNext()) {
            LendInfo info = new LendInfo();
            info.lendPeople = cursor.getString(cursor.getColumnIndex(Name.DB.Table.LEND_PEOPLE));
            info.state = cursor.getString(cursor.getColumnIndex(Name.DB.Table.STATE));
            info.arrears = cursor.getString(cursor.getColumnIndex(Name.DB.Table.ARREARS));
            info.arrearsTime = cursor.getString(cursor.getColumnIndex(Name.DB.Table.ARREARS_TIME));
            info.startTime = cursor.getString(cursor.getColumnIndex(Name.DB.Table.START_TIME));
            info.sayTime = cursor.getString(cursor.getColumnIndex(Name.DB.Table.SAY_TIME));
            info.totalInterest = cursor.getString(cursor.getColumnIndex(Name.DB.Table.TOTAL_INTEREST));
            info.monthInterestRate = cursor.getString(cursor.getColumnIndex(Name.DB.Table.MONTH_INTEREST_RATE));
            info.monthReimbursement = cursor.getString(cursor.getColumnIndex(Name.DB.Table.MONTH_REIMBURSEMENT));
            info.monthPrincipal = cursor.getString(cursor.getColumnIndex(Name.DB.Table.MONTH_PRINCIPAL));
            info.monthInterest = cursor.getString(cursor.getColumnIndex(Name.DB.Table.MONTH_INTEREST));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    //根据信用卡汇总信息.
    public static CardSummaryInfo selectSingleCardSummaryInfo(Context context) {
        CardSummaryInfo info = new CardSummaryInfo();
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        Cursor cursor = db.rawQuery("select * from table_card_summary", null);
        if (cursor.moveToFirst()) {
            info.totalCreditLimit = cursor.getString(cursor.getColumnIndex(Name.DB.Table.TOTAL_CREDIT_LIMIT));
            info.totalNum = cursor.getString(cursor.getColumnIndex(Name.DB.Table.TOTAL_NUM));
            info.activatedNum = cursor.getString(cursor.getColumnIndex(Name.DB.Table.ACTIVATED_NUM));
            info.inactiveNum = cursor.getString(cursor.getColumnIndex(Name.DB.Table.INACTIVE_NUM));
            info.cardInfos = selectAllCreditCardInfo(context);
        }
        return info;
    }

    //根据CARD_ID查询银行卡信息
    public static CreditCardInfo selectSingleCreditCardInfoByCardId(Context context, String cardId) {
        List<CreditCardInfo> list = new ArrayList<>();
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        //String sql = "select name,card_no,bank_code,statement_date from table_credit_card where card_id = ?";
        String sql = "select name,card_no,bank_code,statement_date from table_credit_card where card_id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{cardId});
        if (cursor.moveToFirst()) {
            CreditCardInfo info = new CreditCardInfo();
            info.name = cursor.getString(cursor.getColumnIndex(Name.DB.Table.NAME));
            info.cardNo = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CARD_NO));
            info.bankCode = cursor.getString(cursor.getColumnIndex(Name.DB.Table.BANK_CODE));
            info.statementDate = cursor.getString(cursor.getColumnIndex(Name.DB.Table.STATEMENT_DATE));
            /*info.repaymentDate = cursor.getString(cursor.getColumnIndex(Name.DB.Table.REPAYMENT_DATE));
            info.creditLimit = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CREDIT_LIMIT));
            info.creditLimit = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CREDIT_LIMIT));*/
            return info;
        }
        cursor.close();
        return null;
    }

    //银行卡列表.
    public static List<CreditCardInfo> selectAllCreditCardInfo(Context context) {
        List<CreditCardInfo> list = new ArrayList<>();
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        Cursor cursor = db.rawQuery("select * from table_credit_card", null);
        while (cursor.moveToNext()) {
            CreditCardInfo info = new CreditCardInfo();
            info.name = cursor.getString(cursor.getColumnIndex(Name.DB.Table.NAME));
            info.cardNo = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CARD_NO));
            info.statementDate = cursor.getString(cursor.getColumnIndex(Name.DB.Table.STATEMENT_DATE));
            info.repaymentDate = cursor.getString(cursor.getColumnIndex(Name.DB.Table.REPAYMENT_DATE));
            info.creditLimit = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CREDIT_LIMIT));
            info.creditLimit = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CREDIT_LIMIT));
            info.bankCode = cursor.getString(cursor.getColumnIndex(Name.DB.Table.BANK_CODE));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    //根据id查询账户活期总金额.
    public static MeItemInfo selectSingleMeItemInfo(Context context, String id) {
        MeItemInfo info = new MeItemInfo();
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        Cursor cursor = db.rawQuery("select * from table_my_item where no = ?", new String[]{id});
        if (cursor.moveToFirst()) {
            info.name = cursor.getString(cursor.getColumnIndex(Name.DB.Table.NAME));
            info.content = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CONTENT));
        }
        return info;
    }

    //我的账户列表.
    public static List<AccountInfo> selectAllAccountInfo(Context context) {
        List<AccountInfo> list = new ArrayList<>();
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        String sql = "select * from table_account_item where has_monney = 1 " +
                "order by nearly_rate desc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            AccountInfo info = new AccountInfo();
            info.name = cursor.getString(cursor.getColumnIndex(Name.DB.Table.NAME));
            info.content = cursor.getString(cursor.getColumnIndex(Name.DB.Table.CONTENT));
            info.color = cursor.getInt(cursor.getColumnIndex(Name.DB.Table.COLOR));
            info.nearlyRate = cursor.getString(cursor.getColumnIndex(Name.DB.Table.NEARLY_RATE));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    //我的信息.
    public static MeInfo selectMeInfoAlone(Context context) {
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        MeInfo meInfo = new MeInfo();
        Cursor cursor = db.rawQuery("select * from table_my", null);
        if (cursor.moveToFirst()) {
            meInfo.name = cursor.getString(cursor.getColumnIndex(Name.DB.Table.NAME));
            meInfo.phone = cursor.getString(cursor.getColumnIndex(Name.DB.Table.PHONE));
            meInfo.email = cursor.getString(cursor.getColumnIndex(Name.DB.Table.EMAIL));
            meInfo.password = cursor.getString(cursor.getColumnIndex(Name.DB.Table.PASSWORD));
            meInfo.qq = cursor.getString(cursor.getColumnIndex(Name.DB.Table.QQ));
            meInfo.wx = cursor.getString(cursor.getColumnIndex(Name.DB.Table.WX));
        }
        cursor.close();
        return meInfo;
    }

    //我的列表模块.
    public static List<MeItemInfo> selectAllMeItemInfo(Context context) {
        List<MeItemInfo> list = new ArrayList<>();
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        updateAllMeItemInfo(context);
        String sql = "select * from table_my_item order by no";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            MeItemInfo meInfo = new MeItemInfo();
            meInfo.name = cursor.getString(cursor.getColumnIndex("name"));
            meInfo.content = cursor.getString(cursor.getColumnIndex("content"));
            list.add(meInfo);
        }
        cursor.close();
        return list;
    }

    /**
     * //修改表table_my_item
     * SQLiteDatabase的update方法签名为update(String table,ContentValues values,String whereClause,String[] whereArgs)，这个更新方法的参数说明如下：
     * table：代表想要更新数据的表名。
     * values：代表想要更新的数据。
     * whereClause：满足该whereClause子句的记录将会被更新。
     * whereArgs：用于为whereArgs子句传递参数。
     * 例如我们想要更新person_inf表中所有主键大于20的人的人名，可调用如下方法：
     * ContentValues values=new ContentValues();
     * //存放更新后的人名
     * values.put("name","新人名");
     * int result=db.update("person_inf",values,"_id>?",new Integer[]{20});
     *
     * @param context
     */
    public static void updateAllMeItemInfo(Context context) {
        SQLiteDatabase db = OutlayDBHelper.openDatabaseOnSDCrad(context, Name.DB.ZXN_APP);
        updateAccountSum(context, db);
        updateInstallmentSum(context, db);
        updateBillSum(context, db);
    }

    private static void updateBillSum(Context context, SQLiteDatabase db) {
        List<BillInfo> billInfos = selectAllBillInfo(context);
        double sum = 0;
        for (int i = 0; i < billInfos.size(); i++) {
            double num = Double.parseDouble(billInfos.get(i).shouldRepaymentAmount);
            sum += num;
        }
        String sql = "update table_my_item set content = " + sum + " where no =4";
        db.execSQL(sql);
    }

    //更新分期总额度.
    private static void updateInstallmentSum(Context context, SQLiteDatabase db) {
        List<InstallmentInfo> installmentInfos = selectAllInstallmentInfo(context);
        double sum = 0;
        for (int i = 0; i < installmentInfos.size(); i++) {
            double num = Double.parseDouble(installmentInfos.get(i).installmentAmount);
            sum += num;
        }
        String sql = "update table_my_item set content = " + sum + " where no =6";
        db.execSQL(sql);
    }

    //更新账户活期总额度.
    private static void updateAccountSum(Context context, SQLiteDatabase db) {
        List<AccountInfo> accountInfos = selectAllAccountInfo(context);
        double sum = 0;
        for (int i = 0; i < accountInfos.size(); i++) {
            double num = Double.parseDouble(accountInfos.get(i).content);
            sum += num;
        }
        String sql = "update table_my_item set content = " + sum + " where no = 0";
        db.execSQL(sql);
    }

}


