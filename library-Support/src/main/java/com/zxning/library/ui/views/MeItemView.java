package com.zxning.library.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxning.library.R;

/**
 * Created by Administrator on 2015/12/28.
 */
public class MeItemView extends LinearLayout {

    // 命名空间，在引用这个自定义组件的时候，需要用到
    private String namespace = "http://schemas.android.com/apk/res/com.jtoushou.kxd";
    private ImageView item_iv;
    private TextView name_tv;
    private TextView state_tv;
    private int itemStateCorlor;
    private String itemName;
    private String itemState;
    private Drawable logoSrc;
    private ImageView enter_iv;
    private boolean enter;

    public MeItemView(Context context) {
        super(context);
        initView(context, null);
    }

    public MeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }


    public MeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.MeItemView);
        itemStateCorlor = typedArray.getColor(R.styleable.MeItemView_itemStateCorlor, Color.BLACK);
        itemName = typedArray.getString(R.styleable.MeItemView_itemName);
        itemState = typedArray.getString(R.styleable.MeItemView_itemState);*/
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.MeItemView);
            /*Drawable d = a.getDrawable(com.android.internal.R.styleable.ImageView_src);
            if (d != null) {

            }*/

            logoSrc = typedArray.getDrawable(R.styleable.MeItemView_logoSrc);     /*String(R.styleable.MeItemView_logoSrc);*/
            itemStateCorlor = typedArray.getColor(R.styleable.MeItemView_itemStateColor, Color.BLACK);
            itemName = typedArray.getString(R.styleable.MeItemView_itemName);
            itemState = typedArray.getString(R.styleable.MeItemView_itemState);
            enter = typedArray.getBoolean(R.styleable.MeItemView_enter, false);
        }

        View.inflate(context, R.layout.layout_item_me, this);
        item_iv = (ImageView) findViewById(R.id.item_iv);
        name_tv = (TextView) findViewById(R.id.name_tv);
        state_tv = (TextView) findViewById(R.id.state_tv);
        enter_iv = (ImageView) findViewById(R.id.enter_iv);

        enter_iv.setVisibility(View.GONE);
        item_iv.setVisibility(View.GONE);
        if (logoSrc != null) {
            item_iv.setVisibility(View.VISIBLE);
            item_iv.setImageDrawable(logoSrc);
            ;
        }
        if (!TextUtils.isEmpty(itemName)) {
            name_tv.setText(itemName);
        }
        if (!TextUtils.isEmpty(itemState)) {
            state_tv.setText(itemState);
        }
        if (itemStateCorlor != 0) {
            state_tv.setTextColor(itemStateCorlor);
        }
        if (enter) {
            enter_iv.setVisibility(View.VISIBLE);
        } else {
            enter_iv.setVisibility(View.GONE);
        }

    }

    //设置图标
    public void setMeItemLogo(int id) {
        item_iv.setImageResource(id);
    }

    //设置名称
    public void setMeItemName(String name) {
        name_tv.setText(name);
    }

    //设置状态
    public void setMeItemState(String state) {
        state_tv.setText(state);
    }

    //设置状态字体颜色
    public void setMeItemStateCorlor(int color) {
        state_tv.setTextColor(color);
    }


    public void setEnterVisibility(boolean isShow) {
        if (isShow) {
            enter_iv.setVisibility(View.VISIBLE);
        } else {
            enter_iv.setVisibility(View.INVISIBLE);
        }
    }

    public void setEnterVisibility(int isShow) {
        switch (isShow) {
            case 0:
                enter_iv.setVisibility(View.GONE);
                break;
            case 1:
                enter_iv.setVisibility(View.INVISIBLE);
                break;
            case 2:
                enter_iv.setVisibility(View.VISIBLE);
                break;
        }
    }

}
