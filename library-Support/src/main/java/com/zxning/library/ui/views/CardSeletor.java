package com.zxning.library.ui.views;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxning.library.R;
import com.zxning.library.tool.UIUtils;
import com.zxning.library.ui.selectdate.ScreenUtil;

import java.util.List;

/**
 *
 */
public class CardSeletor {

    private CardHandler cardHandler;
    private Activity activity;
    private List<String> list;
    private Dialog seletorDialog;
    private ImageView cancel_iv;
    private RecyclerView card_rv;
    CardRVAdapter mCardRVAdapter;
    int positionSelected = 0;

    public interface CardHandler {
        void selectedCard(String card, int position);

        void addCard();
    }

    public CardSeletor(Activity activity, CardHandler cardHandler) {
        this.activity = activity;
        this.cardHandler = cardHandler;
        initDialog();
        initView();
    }

    private void initView() {
        cancel_iv = (ImageView) seletorDialog.findViewById(R.id.cancel_iv);
        cancel_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seletorDialog.dismiss();
            }
        });
        card_rv = (RecyclerView) seletorDialog.findViewById(R.id.card_rv);
        card_rv.setLayoutManager(new LinearLayoutManager(activity));
        card_rv.setItemAnimator(new DefaultItemAnimator());
        card_rv.setHasFixedSize(true);
        card_rv.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
        mCardRVAdapter = new CardRVAdapter();
        card_rv.setAdapter(mCardRVAdapter);
    }

    public void setCardList(List<String> list, int positionSelected) {
        this.positionSelected = positionSelected;
        this.list = list;
        mCardRVAdapter.notifyDataSetChanged();
    }

    private void initDialog() {
        if (seletorDialog == null) {
            seletorDialog = new Dialog(activity, R.style.time_dialog);
            seletorDialog.setCancelable(false);
            seletorDialog.setContentView(R.layout.layout_dialog_card);
            Window window = seletorDialog.getWindow();
            window.setWindowAnimations(R.style.main_menu_animstyle);
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = ScreenUtil.getInstance(activity).getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
        }
    }

    public void show() {
        seletorDialog.show();
    }

    public void dismiss() {
        seletorDialog.dismiss();
    }


    public class CardRVAdapter extends RecyclerView.Adapter<CardRVAdapter.CardViewHolder> {

        public CardRVAdapter() {

        }

        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CardViewHolder(UIUtils.inflate(R.layout.item_card_choice));
        }

        @Override
        public void onBindViewHolder(CardViewHolder holder, int position) {
            if (position != list.size()) {
                holder.bank_iv.setVisibility(View.VISIBLE);
                //holder.bank_iv.setImageResource(cardIdByCode);
                holder.card_name_tv.setText(list.get(position));
                holder.choice_card_iv.setImageResource(R.mipmap.choice_card_iv);
                if (position == positionSelected)
                    holder.choice_card_iv.setVisibility(View.VISIBLE);
                else
                    holder.choice_card_iv.setVisibility(View.INVISIBLE);
            } else if (position == list.size()) {
                holder.bank_iv.setVisibility(View.INVISIBLE);
                holder.card_name_tv.setText("使用新卡支付");
                holder.choice_card_iv.setVisibility(View.VISIBLE);
                holder.choice_card_iv.setImageResource(R.mipmap.iv_enter);
            }
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size() + 1;
        }

        public class CardViewHolder extends RecyclerView.ViewHolder {

            ImageView bank_iv;
            TextView card_name_tv;
            ImageView choice_card_iv;

            CardViewHolder(View view) {
                super(view);
                bank_iv = (ImageView) view.findViewById(R.id.bank_iv);
                card_name_tv = (TextView) view.findViewById(R.id.card_name_tv);
                choice_card_iv = (ImageView) view.findViewById(R.id.choice_card_iv);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (getAdapterPosition() == list.size()) {
                            cardHandler.addCard();

                        } else {
                            cardHandler.selectedCard(list.get(getAdapterPosition()), getAdapterPosition());
                        }

                    }
                });
            }
        }
    }


}
