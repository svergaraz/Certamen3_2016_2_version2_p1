package cl.telematica.android.certamen3v2.presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import cl.telematica.android.certamen3v2.MemorizeActivity;
import cl.telematica.android.certamen3v2.MemorizeView;
import cl.telematica.android.certamen3v2.R;
import cl.telematica.android.certamen3v2.adapters.GridAdapter;
import cl.telematica.android.certamen3v2.interfaces.CardSelectedListener;
import cl.telematica.android.certamen3v2.models.CardModel;
import cl.telematica.android.certamen3v2.presenter.contract.MemorizePresenter;
import cl.telematica.android.certamen3v2.utils.Utils;

/**
 * Created by Sergiox on 25-11-2016.
 */

public class MemorizePresenterImpl implements MemorizePresenter {

    private Activity activity;
    private Context c;
    private MemorizeActivity view;
    private CardSelectedListener cardSelected;

    private GridView gridView;
    private TextView scoreText;
    private List<CardModel> cards;
    private int counter;
    private int lastPosition = 0;
    private int pairsCounter = 0;
    private int score = 0;
    private CardModel lastCard;
    private GridAdapter adapter;

    public MemorizePresenterImpl(Activity activity, Context c,  MemorizeActivity view, CardSelectedListener card){
        this.activity = activity;
        this.view = view;
        this.c = c;
        this.cardSelected = card;
    }

    @Override
    public void createVariables() {
        gridView = (GridView) activity.findViewById(R.id.cardsGrid);
        scoreText = (TextView) activity.findViewById(R.id.scoreText);
        view.updateVariables(gridView, scoreText);
    }

    @Override
    public void setGridData() {
        counter = 0;
        lastPosition = 0;
        pairsCounter = 0;
        score = 0;

        cards = Utils.createCards();

        adapter = new GridAdapter(c,
                R.id.scoreText,
                cards,
                cardSelected);
        gridView.setAdapter(adapter);

        String scoreString = String.format(activity.getString(R.string.score_title),
                String.valueOf(score));

        scoreText.setText(scoreString);
        view.updateGrid(counter, lastPosition, pairsCounter, score, cards, adapter, scoreString);
    }


}
