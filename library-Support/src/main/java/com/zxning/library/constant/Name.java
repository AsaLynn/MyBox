package com.zxning.library.constant;

/**
 * 常量字段.
 */
public interface Name {

    interface CardId {
        String GDB_ID = "4571";//广发尾号.
        String SHB_ID = "5139";//上银尾号.
        String ABC_ID = "2470";//农行尾号
        String CEB_ID = "3596";//光大尾号
        String CMB_ID = "5241";//招商尾号
        String BOC_ID = "0988";//中行尾号
    }

    interface DB {
        String ZXN_APP = "zxn_app.db";
        //        String ZXN_APP_A = "zxn_app.db";
        String DATA_BASES = "databases";

        interface Table {
            String NAME = "name";
            String PHONE = "phone";
            String EMAIL = "email";
            String PASSWORD = "password";
            String QQ = "qq";
            String WX = "wx";
            String CONTENT = "content";
            String COLOR = "color";
            String CARD_NO = "card_no";
            String STATEMENT_DATE = "statement_date";
            String REPAYMENT_DATE = "repayment_date";
            String CREDIT_LIMIT = "credit_limit";
            String USER = "user";
            String BANK_CODE = "bank_code";
            String TOTAL_CREDIT_LIMIT = "total_credit_limit";
            String TOTAL_NUM = "total_num";
            String ACTIVATED_NUM = "activated_num";
            String INACTIVE_NUM = "inactive_num";
            String LEND_PEOPLE = "lend_people";
            String STATE = "state";
            String TIME_REIMBURSEMENT = "time_reimbursement";
            String MONTH_REIMBURSEMENT = "month_reimbursement";
            String ARREARS = "arrears";
            String ARREARS_TIME = "arrears_time";
            String START_TIME = "start_time";
            String SAY_TIME = "say_time";
            String TOTAL_INTEREST = "total_interest";
            String MONTH_INTEREST_RATE = "month_interest_rate";
            String MONTH_INTEREST = "month_interest";
            String MONTH_PRINCIPAL = "month_principal";
            String TITLE = "title";
            String CURRENT_TIME = "current_time";
            String CURRENT_NUM = "current_num";
            String CURRENT_STATE = "current_state";
            String END_TIME = "end_time";
            String INSTALLMENT_AMOUNT = "installment_amount";
            String MONTHS_POUNDAGE = "months_poundage";
            String INSTALLMENT_NUM = "installment_num";
            String MONTHS_REPAYMENT = "months_repayment";
            String POUNDAGE_RATE = "poundage_rate";
            String SUM_POUNDAGE = "sum_poundage";
            String CARD_ID = "card_id";
            String RESIDUAL_AMOUNT = "residual_amount";
            String DUE_DAY = "due_day";
            String FREE_DAY = "free_day";
            String SHOULD_REPAYMENT_AMOUNT = "should_repayment_amount";
            String MONTH_BILL_DATE = "month_bill_date";
            String NEARLY_RATE = "nearly_rate";
        }
    }

    interface Intent {
        String NUMBER = "number";
        String BILL_SUM = "bill_sum";
        String TOTAL_CURRENT_SUM = "total_current_sum";
    }

    interface RequestCode {
        int CURRENT = 101;

    }

    interface ResultCode {
        int CURRENT_ACCONT_NUM = 201;
        int ADD_CARD_SUCCESS = 202;
    }

}
