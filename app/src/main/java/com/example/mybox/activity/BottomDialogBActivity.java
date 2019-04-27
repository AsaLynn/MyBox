package com.example.mybox.activity;

import android.view.View;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.UIUtils;
import com.zxning.library.ui.views.CardSeletor;
import com.zxning.library.ui.views.MeItemView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BottomDialogBActivity extends BaseActivity {

    private MeItemView card_miv;
    private CardSeletor seletor;
    private int positionSelected;
    private List<String> list;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_bank_card_choice);
        card_miv = (MeItemView) view.findViewById(R.id.card_miv);
        card_miv.setOnClickListener(this);
        list = new ArrayList<>();
        String[] bank_names = UIUtils.getStringArray(R.array.bank_names);
        for (int i = 0; i <bank_names.length ; i++) {
            list.add(bank_names[i]);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_miv:
                showSelectCardsDialog();
                break;
        }

    }

    private void showSelectCardsDialog() {

        if (seletor == null) {
            seletor = new CardSeletor(this, new CardSeletor.CardHandler() {
                @Override
                public void selectedCard(String card, int position) {
                    seletor.dismiss();
                    card_miv.setMeItemName(list.get(position));
                    positionSelected = position;
                }

                @Override
                public void addCard() {
                    seletor.dismiss();
                }
            });
        }
        seletor.setCardList(list, positionSelected);
        seletor.show();
    }

}
