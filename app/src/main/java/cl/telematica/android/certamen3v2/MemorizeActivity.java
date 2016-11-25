package cl.telematica.android.certamen3v2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cl.telematica.android.certamen3v2.adapters.GridAdapter;
import cl.telematica.android.certamen3v2.interfaces.CardSelectedListener;
import cl.telematica.android.certamen3v2.interfaces.DialogListener;
import cl.telematica.android.certamen3v2.models.CardModel;
import cl.telematica.android.certamen3v2.presenter.MemorizePresenterImpl;
import cl.telematica.android.certamen3v2.utils.MessageFactory;
import cl.telematica.android.certamen3v2.utils.Utils;

public class MemorizeActivity extends AppCompatActivity implements MemorizeView, CardSelectedListener {

    private GridView gridView;
    private TextView scoreText;
    private List<CardModel> cards;
    private int counter = 0;
    private int lastPosition = 0;
    private int pairsCounter = 0;
    private int score = 0;
    private CardModel lastCard;
    private GridAdapter adapter;
    private MemorizePresenterImpl mPresenter;



    private static final String KEY_COUNTER = "Memorize::Counter";
    private static final String KEY_LAST_POSITION = "Memorize::LastPosition";
    private static final String KEY_PAIRS_COUNTER = "Memorize::PairsCounter";
    private static final String KEY_SCORE = "Memorize::Score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorize);

        if ((savedInstanceState != null)){
            if(savedInstanceState.containsKey(KEY_COUNTER)) {
                counter = savedInstanceState.getInt(KEY_COUNTER);
            }
            if(savedInstanceState.containsKey(KEY_LAST_POSITION)) {
                lastPosition = savedInstanceState.getInt(KEY_LAST_POSITION);
            }
            if(savedInstanceState.containsKey(KEY_PAIRS_COUNTER)) {
                pairsCounter = savedInstanceState.getInt(KEY_PAIRS_COUNTER);
            }
            if(savedInstanceState.containsKey(KEY_SCORE)) {
                score = savedInstanceState.getInt(KEY_SCORE);
            }
        }

        if (mPresenter == null){
            Context c = getApplicationContext();
            mPresenter = new MemorizePresenterImpl(this, c, this, this);
            mPresenter.createVariables();
            mPresenter.setGridData();


        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memorize, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.scoreButton:
                /**
                 * Call to the ranking
                 */
                Intent intent = new Intent(MemorizeActivity.this, RankingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateVariables(GridView grid, TextView txt){
        gridView = grid;
        scoreText = txt;
    }

    @Override
    public void updateGrid(int counter, int lastposition, int pairs, int score, List<CardModel> cards, GridAdapter adapter, String scoreString) {
        this.counter = counter;
        this.lastPosition = lastposition;
        this.pairsCounter = pairs;
        this.score = score;
        this.cards = cards;
        this.adapter = adapter;
        this.gridView.setAdapter(this.adapter);
        this.scoreText.setText(scoreString);

    }

    @Override
    public void onCardSelected(CardModel card, int position) {
        if(++counter == 2 && lastCard != null){
            if(lastCard.getCard() == card.getCard()){
                if(++pairsCounter == 8){
                    Dialog dialog = MessageFactory.createDialog(MemorizeActivity.this, new DialogListener() {

                        @Override
                        public void onCancelPressed(DialogInterface dialog) {
                            dialog.dismiss();
                            mPresenter.setGridData();
                        }

                        @Override
                        public void onAcceptPressed(DialogInterface dialog, String name) {
                            if(!Utils.isEmptyOrNull(name)){
                                dialog.dismiss();
                                /**
                                 * Call to the ranking
                                 */
                                Intent intent = new Intent(MemorizeActivity.this, RankingActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("score", score);
                                mPresenter.setGridData();
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        getString(R.string.dialog_down_text),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.winned_text),
                            Toast.LENGTH_SHORT).show();
                }
                score = score + 2;
                counter = 0;
                lastCard = null;

                String scoreString = String.format(getString(R.string.score_title),
                        String.valueOf(score));

                scoreText.setText(scoreString);
            } else {
                scoreText.setText(getString(R.string.bad_text));
                executePostDelayed(card, position);
            }
        } else {
            lastCard = card;
            lastPosition = position;
        }
    }

    private void executePostDelayed(final CardModel card, final int position){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                lastCard.setSelected(false);
                card.setSelected(false);

                cards.set(lastPosition, lastCard);
                cards.set(position, card);

                adapter.notifyDataSetChanged();

                counter = 0;
                lastCard = null;

                String scoreString = String.format(getString(R.string.score_title),
                        String.valueOf(--score));

                scoreText.setText(scoreString);
            }
        }, 500);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNTER, counter);
        outState.putInt(KEY_LAST_POSITION, lastPosition);
        outState.putInt(KEY_PAIRS_COUNTER, pairsCounter);
        outState.putInt(KEY_SCORE, score);
    }

}
