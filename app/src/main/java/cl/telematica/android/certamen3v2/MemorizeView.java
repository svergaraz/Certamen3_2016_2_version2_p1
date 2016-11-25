package cl.telematica.android.certamen3v2;

import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import cl.telematica.android.certamen3v2.adapters.GridAdapter;
import cl.telematica.android.certamen3v2.models.CardModel;

/**
 * Created by Sergiox on 25-11-2016.
 */

public interface MemorizeView {

    void updateVariables(GridView grid, TextView text);

    void updateGrid(int counter, int lastposition, int pairs, int score, List<CardModel> cards, GridAdapter adapter, String scoreString);
}
