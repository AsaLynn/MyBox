package com.bruce.pickerview.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.PopupWindow;

import com.bruce.pickerview.LoopListener;
import com.bruce.pickerview.LoopView;
import com.bruce.pickerview.R;

import java.util.List;

/**
 * PopWindow for Date Pick
 */
public class BankSelectPopWin extends PopupWindow implements OnClickListener {

    private static final int DEFAULT_MIN_YEAR = 1900;
//    public Button cancelBtn;
    public Button confirmBtn;
    public LoopView yearLoopView;
//    public LoopView monthLoopView;
//    public LoopView dayLoopView;
    public View pickerContainerV;
    public View contentView;//root view

    private List<String> listItems = null;
//    private String itemStart; // min year
//    private String itemEnd; // max year
    private int yearPos = 0;
//    private int monthPos = 0;
//    private int dayPos = 0;
    private Context mContext;
//    private String textCancel;
    private String textConfirm;
//    private int colorCancel;
    private int colorConfirm;
    private int btnTextsize;//text btnTextsize of cancel and confirm button
    private int viewTextSize;

//    List<String> yearList = new ArrayList();
//    List<String> monthList = new ArrayList();
//    List<String> dayList = new ArrayList();

    public static class Builder{

        //Required
        private Context context;
        private OnDatePickedListener listener;
        public Builder(Context context,OnDatePickedListener listener){
            this.context = context;
            this.listener = listener;
        }

        //Option
        private List<String> listItems = null;
//        private String minYear = "start";
//        private String maxYear = "end";
//        private String textCancel = "Cancel";
        private String textConfirm = "完成";
        private String dateChose = "";
//        private int colorCancel = Color.parseColor("#474747");
        private int colorConfirm = Color.parseColor("#ffffff");
        private int btnTextSize = 16;//text btnTextsize of cancel and confirm button
        private int viewTextSize = 20;

        public Builder listItems (List<String> listItems) {
            this.listItems = listItems;
            return this;
        }
//
//        public Builder minYear(String minYear){
//            this.minYear = minYear;
//            return this;
//        }
//
//        public Builder maxYear(String maxYear){
//            this.maxYear = maxYear;
//            return this;
//        }

//        public Builder textCancel(String textCancel){
//            this.textCancel = textCancel;
//            return this;
//        }

        public Builder textConfirm(String textConfirm){
            this.textConfirm = textConfirm;
            return this;
        }

        public Builder dateChose(String dateChose){
            this.dateChose = dateChose;
            return this;
        }

//        public Builder colorCancel(int colorCancel){
//            this.colorCancel = colorCancel;
//            return this;
//        }

        public Builder colorConfirm(int colorConfirm){
            this.colorConfirm = colorConfirm;
            return this;
        }

        /**
         * set btn text btnTextSize
         * @param textSize dp
         */
        public Builder btnTextSize(int textSize){
            this.btnTextSize = textSize;
            return this;
        }

        public Builder viewTextSize(int textSize){
            this.viewTextSize = textSize;
            return this;
        }

        public BankSelectPopWin build(){
//            if(minYear > maxYear){
//                throw new IllegalArgumentException();
//            }
            return new BankSelectPopWin(this);
        }
    }

    public BankSelectPopWin(Builder builder){
        this.listItems = builder.listItems;
//        this.itemStart = builder.minYear;
//        this.itemEnd = builder.maxYear;
//        this.textCancel = builder.textCancel;
        this.textConfirm = builder.textConfirm;
        this.mContext = builder.context;
        this.mListener = builder.listener;
//        this.colorCancel = builder.colorCancel;
        this.colorConfirm = builder.colorConfirm;
        this.btnTextsize = builder.btnTextSize;
        this.viewTextSize = builder.viewTextSize;
        setSelectedDate(builder.dateChose);
        initView();
    }

    private OnDatePickedListener mListener;

    private void initView() {

        contentView = LayoutInflater.from(mContext).inflate(
                R.layout.layout_bank_picker, null);
//        cancelBtn = (Button) contentView.findViewById(R.id.btn_cancel);
        confirmBtn = (Button) contentView.findViewById(R.id.btn_confirm);
        yearLoopView = (LoopView) contentView.findViewById(R.id.picker_year);
        pickerContainerV = contentView.findViewById(R.id.container_picker);

//        cancelBtn.setText(textCancel);
        confirmBtn.setText(textConfirm);
//        cancelBtn.setTextColor(colorCancel);
        confirmBtn.setTextColor(colorConfirm);
//        cancelBtn.setTextSize(btnTextsize);
        confirmBtn.setTextSize(btnTextsize);

        //do not loop,default can loop
//        yearLoopView.setNotLoop();

        //set loopview text btnTextsize
        yearLoopView.setTextSize(viewTextSize);

        //set checked listen
        yearLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                yearPos = item;
//                initDayPickerView();
            }
        });
        initPickerViews(); // init year and month loop view
//        initDayPickerView(); //init day loop view

//        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        contentView.setOnClickListener(this);

        setTouchable(true);
        setFocusable(true);
        // setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.FadeInPopWin);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * Init year and month loop view,
     * Let the day loop view be handled separately
     */
    private void initPickerViews() {

//        String yearCount = itemEnd - itemStart;
//
//        for (int i = 0; i < yearCount; i++) {
//            yearList.add(format2LenStr(itemStart + i));
//        }

        yearLoopView.setArrayList(listItems);
        yearPos = yearPos == -1 ? (listItems.size() + 1)/2: yearPos;
        yearLoopView.setInitPosition(yearPos);

    }

    /**
     * set selected date position value when initView.
     *
     * @param dateStr
     */
    public void setSelectedDate(String dateStr) {

        if (!TextUtils.isEmpty(dateStr) && listItems != null && listItems.size() > 0) {
            yearPos = listItems.indexOf(dateStr) == -1 ? 0:listItems.indexOf(dateStr);

//            long milliseconds = getLongFromyyyyMMdd(dateStr);
//            Calendar calendar = Calendar.getInstance(Locale.CHINA);
//
//            if (milliseconds != -1) {
//
//                calendar.setTimeInMillis(milliseconds);
//                yearPos = calendar.get(Calendar.YEAR) - itemStart;
////                monthPos = calendar.get(Calendar.MONTH);
////                dayPos = calendar.get(Calendar.DAY_OF_MONTH) - 1;
//            }
        }
    }

    /**
     * Show date picker popWindow
     *
     * @param activity
     */
    public void showPopWin(Activity activity) {

        if (null != activity) {

            TranslateAnimation trans = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                    0, Animation.RELATIVE_TO_SELF, 1,
                    Animation.RELATIVE_TO_SELF, 0);

            showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM,
                    0, 0);
            trans.setDuration(200);
            trans.setInterpolator(new AccelerateDecelerateInterpolator());

            pickerContainerV.startAnimation(trans);
        }
    }

    /**
     * Dismiss date picker popWindow
     */
    public void dismissPopWin() {

        TranslateAnimation trans = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);

        trans.setDuration(200);
        trans.setInterpolator(new AccelerateInterpolator());
        trans.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                dismiss();
            }
        });

        pickerContainerV.startAnimation(trans);
    }

    @Override
    public void onClick(View v) {

        if (v == contentView /*|| v == cancelBtn*/) {

            dismissPopWin();
        } else if (v == confirmBtn) {

            if (null != mListener) {
                mListener.onDatePickCompleted(listItems.get(yearPos));
            }
            dismissPopWin();
        }
    }

    public interface OnDatePickedListener {
        /**
         * @param result
         */
        void onDatePickCompleted(String result);
    }
}
