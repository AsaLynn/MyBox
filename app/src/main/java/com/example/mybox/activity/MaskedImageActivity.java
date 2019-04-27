package com.example.mybox.activity;

import android.view.View;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;
import com.zxning.library.ui.circularimage.CircularImage;

/**
 * 圆形头像展示.
 * CircularImage的使用.
 */
public class MaskedImageActivity extends BaseActivity {

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_masked_image);
        CircularImage cover_user_photo = (CircularImage) view.findViewById(R.id.cover_user_photo);
        cover_user_photo.setImageResource(R.mipmap.iv_m_img_face);
        return view;
    }
}
