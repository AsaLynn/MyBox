package com.example.mybox.activity;

import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.FileUtils;
import com.zxning.library.tool.UIUtils;

import java.io.IOException;

public class FileOptionActivity extends BaseActivity {

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_file_option);
        Button btn = (Button) view.findViewById(R.id.btn);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                /*try {
                     FileUtils.getFileFromAssetToSDcard(this, "kxd_logo.png");
                    *//*AssetManager am = getAssets();
                    //得到数据库的输入流
                    InputStream is = am.open("kxd_logo.png");
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    FileUtils.saveFile(bitmap, Environment.getExternalStorageDirectory() + "/kxd_download/",
                            "kxd_logo.png");*//*
                    UIUtils.showMsg("保存成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    UIUtils.showMsg("保存失败");
                }*/

                if (FileUtils.isFileExists("", "kxd_logo.png")) {
                    UIUtils.showMsg("文件存在");
                } else {
                    try {
                        FileUtils.getPicFromAssetToSDcard(this, "kxd_logo.png");
                        UIUtils.showMsg("保存成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.btn2:
                try {
                    FileUtils.deleteFile(Environment.getExternalStorageDirectory().getPath(), "kxd_logo.png");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
