package com.example.mybox.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybox.R;
import com.example.mybox.adapter.RecyclerType2Adapter;
import com.example.mybox.base.BaseActivity;
import com.xbc.contacts.CharacterParser;
import com.xbc.contacts.ContactsInfo;
import com.xbc.contacts.ContactsUtils;
import com.xbc.contacts.LettersComparator;
import com.xbc.contacts.SideBar;
import com.zxning.library.listener.MyTextWatcher;
import com.zxning.library.tool.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 通讯录
 * 解决Andriod软键盘出现把原来的布局给顶上去的方法
 * <activityandroid:name=".activity.HomeActivity"Android:windowSoftInputMode="adjustPan|stateHidden"></activity>
 * 活动的主窗口如何与包含屏幕上的软键盘窗口交互。这个属性的设置将会影响两件事情:
 * <p/>
 * 1.软键盘的状态——是否它是隐藏或显示——当活动(Activity)成为用户关注的焦点。
 * 2.活动的主窗口调整——是否减少活动主窗口大小以便腾出空间放软键盘或是否当活动窗口的部分被软键盘覆盖
 * 时它的内容的当前焦点是可见的。它的设置必须是下面列表中的一个值
 * "stateUnspecified"
 * 软键盘的状态
 * (是否它是隐藏或可见
 * )没有被指定。系统将选择一个合适的状态或依赖于主题的设置。这个是为了软件盘行为默认的设置。
 * "stateUnchanged"
 * 软键盘被保持无论它上次是什么状态，是否可见或隐藏，当主窗口出现在前面时。
 * "stateHidden"
 * 当用户选择该Activity时，软键盘被隐藏——也就是，当用户确定导航到该Activity时，而不是返回到它由于离开另一个:
 * Activity。
 * "stateAlwaysHidden"
 * 软键盘总是被隐藏的，当该Activity主窗口获取焦点时。
 * "stateVisible"
 * 软键盘是可见的，当那个是正常合适的时Activity主窗口时
 * "stateAlwaysVisible"
 * 当用户选择这个Activity时，软键盘是可见的——也就是，也就是，当用户确定导航到该.Activity时，而不是返回
 * 到它由于离开另一个 Activity。
 * <p/>
 * "adjustUnspecified"
 * 它不被指定是否该"Activity主窗口调整大小以便留出软键盘的空间，或是否窗口上的内容得到屏幕上当前的焦点是可见的。
 * 系统将自动选择这些模式中一种主要依赖于是否窗口的内容有任何布局视图能够滚动他们的内容。如果有这样的一个视图，
 * 这个窗口将调整大小，这样的假设可以使滚动窗口的内容在一个较小的区域中可见的。这个是主窗口默认的行为设置。
 * <p/>
 * "adjustResize"
 * 该Activity主窗口总是被调整屏幕的大小以便留出软键盘的空间
 * "adjustPan"该Activity主窗口并不调整屏幕的大小以便留出软键盘的空间。
 * 相反，当前窗口的内容将自动移动以便当前焦点从不被键盘覆盖和用户能总是看到输入内容的部分。
 * 这个通常是不期望比调整大小，因为用户可能关闭软键盘以便获得与被覆盖内容的交互操作。
 */
public class ContactsListAActivity extends BaseActivity {

    private SideBar sideBar;
    private ImageView ivClearText;
    private EditText etSearch;
    private RecyclerView rv;
    //private LinearLayoutManager mLayoutManager;
    private RecyclerType2Adapter mAdapter;
    private RecyclerView people_rv;
    private CharacterParser characterParser;
    private LettersComparator pinyinComparator;
    private ArrayList<ContactsInfo> phones;
    private List<ContactsInfo> infos;
    private LinearLayoutManager layoutManager;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_contacts2);
        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        TextView dialog = (TextView) view.findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        ivClearText = (ImageView) view.findViewById(R.id.ivClearText);
        etSearch = (EditText) view.findViewById(R.id.et_search);
        people_rv = (RecyclerView) view.findViewById(R.id.people_rv);

        //设置固定大小
        people_rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        people_rv.setLayoutManager(layoutManager);
        infos = new ArrayList<>();
        List<ContactsInfo> groups = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ContactsInfo info = new ContactsInfo();
            info.setPeople(false);
            info.setUseState(String.valueOf(5 + i));
            if (0 == i) {
                info.setName("产品部");
            } else if (1 == i) {
                info.setName("开发部");
            } else if (2 == i) {
                info.setName("运营部");
            } else {
                info.setName("为分配部门人员");
            }

            groups.add(info);
        }
        infos.addAll(0, groups);
        /*List<ContactsInfo> peoples = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ContactsInfo info = new ContactsInfo();
            info.setPeople(true);
            info.setName("王小" + i);
            info.setUseState("未激活");
            info.setIsSelected(false);
            if (i == 8) {
                info.setJob("主管");
            } else if (i == 4) {
                info.setJob("经理");
            } else {
                info.setJob("");
            }
            peoples.add(info);
        }
        infos.addAll(peoples);*/

        characterParser = CharacterParser.getInstance();
        pinyinComparator = new LettersComparator();

        initListener();
        loadContacts();
        return view;
    }

    private void loadContacts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ContentResolver resolver = getApplicationContext().getContentResolver();
                    Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, "sort_key"}, null, null, "sort_key COLLATE LOCALIZED ASC");
                    if (phoneCursor == null || phoneCursor.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "未获得读取联系人权限 或 未获得联系人数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int PHONES_NUMBER_INDEX = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int PHONES_DISPLAY_NAME_INDEX = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int SORT_KEY_INDEX = phoneCursor.getColumnIndex("sort_key");
                    if (phoneCursor.getCount() > 0) {
                        phones = new ArrayList<ContactsInfo>();
                        while (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                            if (TextUtils.isEmpty(phoneNumber))
                                continue;
                            String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                            String sortKey = phoneCursor.getString(SORT_KEY_INDEX);
                            //System.out.println(sortKey);
                            ContactsInfo sortModel = new ContactsInfo(contactName, phoneNumber, sortKey);
                            sortModel.setPeople(true);
                            // sortModel.setName(contactName);
                            sortModel.setLastName("小2");
//                            String s = contactName.substring(contactName.length() - 2, contactName.length());
//                            sortModel.setLastName(s);
                            sortModel.setUseState("未激活");
                            sortModel.setIsSelected(false);
                            sortModel.setJob("经理");

                            //优先使用系统sortkey取,取不到再使用工具取
                            String sortLetters = ContactsUtils.getSortLetterBySortKey(sortKey);
                            if (sortLetters == null) {
                                sortLetters = ContactsUtils.getSortLetter(contactName);
                            }
                            sortModel.sortLetters = sortLetters;
                            sortModel.sortToken = ContactsUtils.parseSortKey(sortKey);
                            phones.add(sortModel);
                        }
                    }
                    phoneCursor.close();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Collections.sort(phones, pinyinComparator);
                            infos.addAll(phones);
                            mAdapter = new RecyclerType2Adapter(ContactsListAActivity.this, infos);
                            mAdapter.setJustContacts(true);
                            people_rv.setAdapter(mAdapter);
                            //mAdapter.updateListView(infos);

                        }
                    });
                } catch (Exception e) {
                    Log.e("xbc", e.getLocalizedMessage());
                }
            }
        }).start();
    }


    private void initListener() {

        /**清除输入字符**/
//        ivClearText.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                etSearch.setText("");
//            }
//        });

        etSearch.addTextChangedListener(new MyTextWatcher() {

            @Override
            public void afterTextChanged(Editable e) {

                String content = etSearch.getText().toString();
                if ("".equals(content)) {
                    ivClearText.setVisibility(View.INVISIBLE);
                } else {
                    ivClearText.setVisibility(View.VISIBLE);
                }

                if (content.length() > 0) {
                    ArrayList<ContactsInfo> fileterList = (ArrayList<ContactsInfo>) ContactsUtils.search(content, phones);
                    mAdapter.updateListView(fileterList);
                } else {
                    mAdapter.updateListView(infos);
                }
                //mListView.setSelection(0);
                layoutManager.scrollToPosition(0);
            }

        });

        //设置右侧[A-Z]快速导航栏触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    //mListView.setSelection(position);
                    //layoutManager.scrollToPosition(position);
                    layoutManager.scrollToPositionWithOffset(position, 0);
                }
            }
        });
        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                ContactsSortAdapter.ViewHolder viewHolder = (ContactsSortAdapter.ViewHolder) view.getTag();
                viewHolder.cbChecked.performClick();
                com.jtoushou.mytooldemo.adapter.toggleChecked(position);
            }
        });*/
    }

}


//-------------

//    /**
//     * 取sort_key的首字母
//     *
//     * @param sortKey
//     * @return
//     */
//    private String getSortLetterBySortKey(String sortKey) {
//        if (sortKey == null || "".equals(sortKey.trim())) {
//            return null;
//        }
//        String letter = "#";
//        //汉字转换成拼音
//        String sortString = sortKey.trim().substring(0, 1).toUpperCase(Locale.CHINESE);
//        // 正则表达式，判断首字母是否是英文字母
//        if (sortString.matches("[A-Z]")) {
//            letter = sortString.toUpperCase(Locale.CHINESE);
//        }
//        return letter;
//    }


//    /**
//     * 名字转拼音,取首字母
//     *
//     * @param name
//     * @return
//     */
//    private String getSortLetter(String name) {
//        String letter = "#";
//        if (name == null) {
//            return letter;
//        }
//        //汉字转换成拼音
//        String pinyin = characterParser.getSelling(name);
//        String sortString = pinyin.substring(0, 1).toUpperCase(Locale.CHINESE);
//
//        // 正则表达式，判断首字母是否是英文字母
//        if (sortString.matches("[A-Z]")) {
//            letter = sortString.toUpperCase(Locale.CHINESE);
//        }
//        return letter;
//    }

//    String chReg = "[\\u4E00-\\u9FA5]+";//中文字符串匹配
//    /**
//     * 解析sort_key,封装简拼,全拼
//     *
//     * @param sortKey
//     * @return
//     */
//    public SortToken parseSortKey(String sortKey) {
//        SortToken token = new SortToken();
//        if (sortKey != null && sortKey.length() > 0) {
//            //其中包含的中文字符
//            String[] enStrs = sortKey.replace(" ", "").split(chReg);
//            for (int i = 0, length = enStrs.length; i < length; i++) {
//                if (enStrs[i].length() > 0) {
//                    //拼接简拼
//                    token.simpleSpell += enStrs[i].charAt(0);
//                    token.wholeSpell += enStrs[i];
//                }
//            }
//        }
//        return token;
//    }

//    /**
//     * 模糊查询
//     *
//     * @param str
//     * @return
//     */
//    private List<ContactsInfo> search(String str) {
//        List<ContactsInfo> filterList = new ArrayList<ContactsInfo>();// 过滤后的list
//        //if (str.matches("^([0-9]|[/+])*$")) {// 正则表达式 匹配号码
//        if (str.matches("^([0-9]|[/+]).*")) {// 正则表达式 匹配以数字或者加号开头的字符串(包括了带空格及-分割的号码)
//            String simpleStr = str.replaceAll("\\-|\\s", "");
//            for (ContactsInfo contact : phones) {
//                if (contact.number != null && contact.name != null) {
//                    if (contact.simpleNumber.contains(simpleStr) || contact.name.contains(str)) {
//                        if (!filterList.contains(contact)) {
//                            filterList.add(contact);
//                        }
//                    }
//                }
//            }
//        } else {
//            for (ContactsInfo contact : phones) {
//                if (contact.number != null && contact.name != null) {
//                    //姓名全匹配,姓名首字母简拼匹配,姓名全字母匹配
//                    if (contact.name.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))
//                            || contact.sortKey.toLowerCase(Locale.CHINESE).replace(" ", "").contains(str.toLowerCase(Locale.CHINESE))
//                            || contact.sortToken.simpleSpell.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))
//                            || contact.sortToken.wholeSpell.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))) {
//                        if (!filterList.contains(contact)) {
//                            filterList.add(contact);
//                        }
//                    }
//                }
//            }
//        }
//        return filterList;
//    }
