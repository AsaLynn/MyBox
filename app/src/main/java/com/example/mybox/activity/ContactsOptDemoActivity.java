package com.example.mybox.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

public class ContactsOptDemoActivity extends BaseActivity {

    private TextView people_name_tv;
    private TextView people_phone_tv;
    private TextView open_phone_tv;
    private TextView save_phone_tv;
    private TextView open_msg_tv;
    private TextView open_call_tv;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_contacts_demo);
        people_name_tv = (TextView) view.findViewById(R.id.people_name_tv);
        people_phone_tv = (TextView) view.findViewById(R.id.people_phone_tv);
        open_phone_tv = (TextView) view.findViewById(R.id.open_phone_tv);
        save_phone_tv = (TextView) view.findViewById(R.id.save_phone_tv);
        open_msg_tv = (TextView) view.findViewById(R.id.open_msg_tv);
        open_call_tv = (TextView) view.findViewById(R.id.open_call_tv);
        open_call_tv.setOnClickListener(this);
        open_phone_tv.setOnClickListener(this);
        save_phone_tv.setOnClickListener(this);
        open_msg_tv.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_call_tv:
                /*Intent telIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13716071676"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    UIUtils.showMsg("请在设置-应用权限管理中找到开薪点,拨打电话权限选择允许");
                    return;
                }
                startActivity(telIntent);*/
                break;
            case R.id.open_msg_tv:
                //打开手机短息发信息.
                Uri smsToUri = Uri.parse("smsto:13716071676");
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                smsIntent.putExtra("sms_body", "测试发送短信");
                startActivity(smsIntent);
                break;
            case R.id.open_phone_tv:
                //打开手机通讯录.
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 0);
                break;
            case R.id.save_phone_tv:
                String name = people_name_tv.getText().toString();
                String phone = people_phone_tv.getText().toString();
                //打开新建联系页面.
                Intent intent2 = new Intent(Intent.ACTION_INSERT);
                intent2.setType("vnd.android.cursor.dir/person");
                intent2.setType("vnd.android.cursor.dir/contact");
                intent2.setType("vnd.android.cursor.dir/raw_contact");
                // 添加姓名
                intent2.putExtra(ContactsContract.Intents.Insert.NAME, name);
                // 添加手机
                intent2.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                intent2.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                intent2.putExtra(ContactsContract.Intents.Insert.IM_PROTOCOL, ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ);
                intent2.putExtra(ContactsContract.Intents.Insert.IM_PROTOCOL, "444255655");
                startActivityForResult(intent2, 0);
                break;
        }
    }
}
