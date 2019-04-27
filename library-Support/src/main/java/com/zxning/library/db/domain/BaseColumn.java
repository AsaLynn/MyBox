package com.zxning.library.db.domain;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * bean基类.
 */
public abstract class BaseColumn implements Serializable {

    @DatabaseField(generatedId = true, columnName = "_id")
    private Long id;

    @DatabaseField(columnName = "create_date")
    private String createDate;

    @DatabaseField(columnName = "update_date")
    private String updateDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
