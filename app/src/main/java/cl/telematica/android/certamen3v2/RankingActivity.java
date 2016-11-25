package cl.telematica.android.certamen3v2;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sergiox on 25-11-2016.
 */

public class RankingActivity extends AppCompatActivity {
    private RecyclerView recView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DataBaseClass dbInstance = null;

    private String name = null;
    private String score = null;


    private ArrayList<Jugador> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


        name = this.getIntent().getExtras().getString("name");
        score = this.getIntent().getExtras().getString("score");

        //Adquisicion de los datos por el servicio de GIT

        dbInstance = new DataBaseClass(this);

        recView = (RecyclerView) findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recView.setLayoutManager(layoutManager);



        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute(){

            }

            @Override
            protected String doInBackground(Void... params) {

                return "True";

            }

            @Override
            protected void onPostExecute(String result) {
                if(result != null){
                    System.out.println(result);

                    // specify an adapter (see also next example)

                    adapter = new MyAdapter(RankingActivity.this, getLista());
                    recView.setAdapter(adapter);

                }
            }
        };

        task.execute();
    }

    private ArrayList<Jugador> getLista(){

        //JSONArray lista = new JSONArray(result);
        ArrayList<Jugador> lista = new ArrayList<Jugador>();
        if (name != null){
            SQLiteDatabase db = dbInstance.getWritableDatabase();
            Cursor d = db.rawQuery("INSERT INTO scores VALUES ('"+name+"', "+score+")", null);
            Cursor c = db.rawQuery("SELECT * FROM scores ORDER BY pts DESC", null);

            if(c.moveToFirst()){
                do{
                    String tname = c.getString(0);
                    String tpts = c.getString(1);
                    lista.add(new Jugador(tname, tpts));
                } while (c.moveToNext());
            }
            c.close();
        }
        else {
            SQLiteDatabase db = dbInstance.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM scores ORDER BY pts DESC", null);
            if(c.moveToFirst()){
                do{
                    String tname = c.getString(0);
                    String tpts = c.getString(1);
                    lista.add(new Jugador(tname, tpts));
                } while (c.moveToNext());
            }
            c.close();

        }
        return lista;
    }
}

