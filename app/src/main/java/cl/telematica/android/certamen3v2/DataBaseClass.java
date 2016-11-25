package cl.telematica.android.certamen3v2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.os.FileObserver.CREATE;

/**
 * Created by Sergiox on 25-11-2016.
 */

public class DataBaseClass extends SQLiteOpenHelper {
    private String sqlString = "CREATE TABLE scores (nombre TEXT, pts INT)";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ScoresDB";
    public DataBaseClass(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(sqlString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS alumnos");
        onCreate(sqLiteDatabase);
    }

}

