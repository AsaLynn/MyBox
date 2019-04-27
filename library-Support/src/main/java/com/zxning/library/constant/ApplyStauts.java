package com.zxning.library.constant;

/**
 * Created by Administrator on 2016/9/1.
 */
public interface ApplyStauts {
    public enum Type {
        APPLY_ON("1"), APPLY_NO("2"), APPLY_OK("3");
        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    interface Msg {
        String APPLY_GO_MSG = "审批中";
        String APPLY_ON_MSG = "待审批";
        String APPLY_NO_MSG = "审批驳回";
        String APPLY_OK_MSG = "审批通过";
    }
}
