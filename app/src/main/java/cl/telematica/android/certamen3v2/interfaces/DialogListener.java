package cl.telematica.android.certamen3v2.interfaces;

import android.content.DialogInterface;

/**
 * Created by franciscocabezas on 11/24/16.
 */

public interface DialogListener {
    void onAcceptPressed(DialogInterface dialog, String name);
    void onCancelPressed(DialogInterface dialog);
}
