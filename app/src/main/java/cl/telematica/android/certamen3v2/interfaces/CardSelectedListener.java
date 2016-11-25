package cl.telematica.android.certamen3v2.interfaces;

import cl.telematica.android.certamen3v2.models.CardModel;

/**
 * Created by franciscocabezas on 11/24/16.
 */

public interface CardSelectedListener {
    void onCardSelected(CardModel card, int position);
}
