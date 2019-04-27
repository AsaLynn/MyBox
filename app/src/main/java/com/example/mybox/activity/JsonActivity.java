package com.example.mybox.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonActivity extends BaseActivity {


    private TextView textView;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_json);
        textView = (TextView) view.findViewById(R.id.tv);
        String text = "{\n" +
                "    \"msgContent\": \"http://panshi.oss-cn-qingdao.aliyuncs.com/images/acitivities/3/image.jpg?data=tanzhibang%3A//activity/3\",\n" +
                "    \"contentType\": \"activity\"\n" +
                "}";
        try {
            JSONObject object = new JSONObject(text);
            String s1 = object.getString("msgContent");
            String s2 = object.getString("contentType");
            String msg = "--" + s1 + "--" + s2;
            Log.i(this.getClass().getName(), msg);
            textView.setText(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }
}
