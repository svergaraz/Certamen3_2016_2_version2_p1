package cl.telematica.android.certamen3v2;

/**
 * Created by Sergiox on 25-11-2016.
 */

public class Jugador {
    private String name;
    private String puntaje;


    public Jugador(String nam, String pts){
        name = nam;
        puntaje = pts;
    }

    public String getName(){
        return name;
    }

    public String getPuntaje(){
        return puntaje;
    }
}
