package cl.telematica.android.certamen3v2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Sergiox on 30-09-2016.
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.JugadorViewHolder>{

    private ArrayList<Jugador> datos;
    private static Activity act;

    public MyAdapter(Activity act, ArrayList<Jugador> datos) { this.datos = datos; MyAdapter.act = act; }

    @Override
    public JugadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ranking_item, viewGroup, false);

        JugadorViewHolder tvh = new JugadorViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(JugadorViewHolder viewHolder, int pos) {
        Jugador item = datos.get(pos);

        viewHolder.bindJugador(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class JugadorViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtPuntaje;
        private LinearLayout layJugador;

        public JugadorViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView)itemView.findViewById(R.id.nameText);
            txtPuntaje = (TextView)itemView.findViewById(R.id.scoreText);
            layJugador = (LinearLayout)itemView.findViewById(R.id.rankingItemLayout);


        }

        public void bindJugador(final Jugador t) {
            txtName.setText(t.getName());
            txtPuntaje.setText(t.getPuntaje());


            /*layRepo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(t.getUrl()));
                    act.startActivity(intent);
                }
            });*/
        }
    }

}
