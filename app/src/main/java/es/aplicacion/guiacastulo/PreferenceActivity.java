package es.aplicacion.guiacastulo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;
import java.util.List;

import es.aplicacion.guiacastulo.Utilidades.Utils;
import es.aplicacion.guiacastulo.db.model.Informacion;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.model.PuntoInteres;
import es.aplicacion.guiacastulo.db.model.Recorrido;
import es.aplicacion.guiacastulo.db.schema.Database;


public class PreferenceActivity extends ActionBarActivity {

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        llenarDB(3,3);
        leerDb();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preference, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void llenarDB(int n_mark, int n_pois) {
        database = new Database(getApplicationContext());
        database.open();

       // crearInfo("Calle los pinchos", "Tlf: 98733221, Fax: 99882", "9:40-17:30", "www.castulo.es", "Visitas concertadas");


        long[] id_markers = new long[n_mark];
        for (int j = 0; j < n_mark; j++) {
            long[] id_pois = new long[n_pois];
            for (int i = 0; i < n_pois; i++) {
                //creamos tantos puntos de interes por marcador como diga n_pois
                id_pois[i] = crearPoI("casa_" + i + "mar_" + j, "grande_" + i + "mar_" + j,
                        1.05 + i, 2.05 + i, "uri_imagen_" + i + "mar_" + j, "uri_audio_" + i + "mar_" + j,
                        "uri_video_" + i + "mar_" + j);
            }
            //creamos tantos marcadores como diga n_mark
            id_markers[j] = crearMarcador("marcador_" + j, "descrp_" + j,0.34+j,0.56+j, id_pois, "uri_image_" + j);
        }
        crearRecorrido("Recorrido 1", "descripcion 1", "20 min", "200 metros", "Uri_imagen_recorrido_1",
                id_markers);
    }


private void leerDb(){

     //leer info
     //Informacion infoDb = database.getInformacion(0);
    // Log.d("Info_DB",infoDb.toString());

     //leer recorridos de la DB
     List<Recorrido> listRecordB;
     listRecordB=database.getAllRecorridos();
     for(Recorrido recorr : listRecordB){
     Log.d("Recorridos_DB",recorr.toString());
     }

    //mostrar marcadores
    List<Marcador> listMarcador;
    listMarcador=database.getAllMarcadores();
    for(Marcador marcador : listMarcador){
        Log.d("Marcadores_DB",marcador.toString());
    }

     //mostrar POI
     List<PuntoInteres> listPoIdB;
     listPoIdB=database.getAllPuntoInteres();
     for(PuntoInteres Poi : listPoIdB){
     Log.d("PoI_DB",Poi.toString());
     }
}



    private long crearRecorrido(String nombre, String descrp, String duracion,
                                 String distancia, String uri_img, long [] id_markers){



        //Crear recorridos y meter en DB

            Recorrido recor = new Recorrido();
            recor.setNombre(nombre);
            recor.setDescripcion(descrp);
            recor.setDuracion(duracion);
            recor.setDistancia(distancia);
            recor.setUriImagen(Utils.separarStringComasAString(uri_img));
            recor.setIdsMarcadores(id_markers);
          //  Log.d("PoI_Pre_DB",recor.toString());
            return database.addRecorrido(recor);
    }


    private long crearMarcador(String nombre, String descrp,double latitud, double longitud,long [] id_poi,
                               String uri_img){
        //cargar marcador
        Marcador mark=new Marcador();
        mark.setNombre(nombre);
        mark.setDescripcion(descrp);
        mark.setLatitud(latitud);
        mark.setLongitud(longitud);
        mark.setUriImagen(Utils.separarStringComasAString(uri_img));
        mark.setIdsPoI(id_poi);
       // Log.d("PoI_Pre_DB", mark.toString());
        return database.addMarcador(mark);
    }

    private long crearPoI(String nombre, String descripcion, double latit, double longit,
                          String uri_img, String uri_audio, String uri_video){

            PuntoInteres PoI = new PuntoInteres();
            PoI.setNombre(nombre);
            PoI.setDescripcion(descripcion);
            PoI.setLatitud(latit);
            PoI.setLongitud(longit);
            PoI.setUriAudio(Utils.separarStringComasAString(uri_audio));
            PoI.setUriImagen(Utils.separarStringComasAString(uri_img));
            PoI.setUriVideo(Utils.separarStringComasAString(uri_video));

         //   Log.d("PoI_Pre_DB",PoI.toString());

        return database.addPuntoInteres(PoI);
    }

private long crearInfo(String direcc, String tlf, String horario, String web, String mas_info){
    //cargar informacion
    Informacion info= new Informacion();
    info.setDireccion(direcc);
    info.setTelefono(tlf);
    info.setHorario(horario);
    info.setWeb(web);
    info.setMasInfo(mas_info);
  //  Log.d("Info_Pre_DB", info.toString());
    return database.addInformacion(info);
}
}
