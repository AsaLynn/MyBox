package com.zxning.library.db.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_user")
public class UserInfo
        extends BaseColumn {

    @DatabaseField(index = true, columnName = "username", unique = true, canBeNull = false)
    public String username;

    @DatabaseField(canBeNull = false, columnName = "password")
    public String password;

    @DatabaseField(canBeNull = false, columnName = "currentmoney")
    public String currentMoney;//余额.

    @DatabaseField(canBeNull = false, columnName = "creditCardNum")
    public String creditCardNum;//信用卡数量.

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", currentMoney='" + currentMoney + '\'' +
                '}';
    }


}

/*@Override
    public String toString() {
        return "UserInfo [username=" + username + ", id=" + password + "]";
    }*/

/*@DatabaseField(dataType = DataType.BOOLEAN,canBeNull = false, columnName = "pwdStatus")
    public boolean pwdStatus;

    @DatabaseField(canBeNull = false, columnName = "handpswd")
    public String handpswd;

    @DatabaseField(canBeNull = false, columnName = "usertype")
    public String usertype;

   public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getHandpswd() {
        return handpswd;
    }

    public void setHandpswd(String handpswd) {
        this.handpswd = handpswd;
    }

    public boolean isPwdStatus() {
        return pwdStatus;
    }

    public void setPwdStatus(boolean pwdStatus) {
        this.pwdStatus = pwdStatus;
    }*/

/*public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/