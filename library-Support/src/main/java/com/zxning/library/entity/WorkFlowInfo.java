package com.zxning.library.entity;

/**
 * Created by Administrator on 2016/9/18.
 */
public class WorkFlowInfo {
    public String applyId;
    public CharSequence typeName;
    public CharSequence applyTime;
    public Object auditNode;

    public String getApplyId() {
        return applyId;
    }

    public CharSequence getTypeName() {
        return typeName;
    }

    public CharSequence getApplyTime() {
        return applyTime;
    }

    public Object getAuditNode() {
        return auditNode;
    }
}
