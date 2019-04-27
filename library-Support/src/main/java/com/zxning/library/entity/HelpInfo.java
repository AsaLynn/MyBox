package com.zxning.library.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/1/25.
 */
public class HelpInfo implements Parcelable {

    private String id;
    private String title;
    private String content;
    private String update_time;
    private int help_order;
    private String parent_id;
    private String has_child;

    public HelpInfo() {
    }

    public HelpInfo(Parcel in) {
        id = in.readString();
        title = in.readString();
        content = in.readString();
        update_time = in.readString();
        help_order = in.readInt();
        parent_id = in.readString();
        has_child = in.readString();
    }

    public static final Creator<HelpInfo> CREATOR = new Creator<HelpInfo>() {
        @Override
        public HelpInfo createFromParcel(Parcel source) {
            HelpInfo info = new HelpInfo();
            info.setId(source.readString());
            info.setTitle(source.readString());
            info.setContent(source.readString());
            info.setHas_child(source.readString());
            info.setHelp_order(source.readInt());
            info.setParent_id(source.readString());
            return info;
        }

        @Override
        public HelpInfo[] newArray(int size) {
            return new HelpInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(update_time);
        dest.writeInt(help_order);
        dest.writeString(parent_id);
        dest.writeString(has_child);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getHelp_order() {
        return help_order;
    }

    public void setHelp_order(int help_order) {
        this.help_order = help_order;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getHas_child() {
        return has_child;
    }

    public void setHas_child(String has_child) {
        this.has_child = has_child;
    }
}
