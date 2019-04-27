package com.xbc.contacts;

/**
 * 通讯录列表题目bean.
 */
public class ContactsInfo extends Contact {
    //private String name;
    private String lastName;
    private String job;
    private String useState;
    private boolean isSelected;
    private boolean isPeople;
    public String sortLetters; //显示数据拼音的首字母
    public SortToken sortToken = new SortToken();

    public ContactsInfo() {

    }

    public ContactsInfo(String name, String number, String sortKey) {
        super(name, number, sortKey);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getUseState() {
        return useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isPeople() {
        return isPeople;
    }

    public void setPeople(boolean people) {
        isPeople = people;
    }

}
