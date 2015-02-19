package es.aplicacion.guiacastulo.Utilidades;

import android.os.Environment;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import es.aplicacion.guiacastulo.db.model.Coordenada;
import es.aplicacion.guiacastulo.db.model.Informacion;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.model.PuntoInteres;
import es.aplicacion.guiacastulo.db.model.Recorrido;
import es.aplicacion.guiacastulo.db.schema.Database;

/**
 * Created by Enmanuel on 10/02/2015.
 */
public class dBTest {

    Database database;
    public static void llenarDB( Database database,int n_recor,int n_mark, int n_pois) {
        String uri_img = Environment.getExternalStorageDirectory().toString()+"/GuiaCastulo/Imagenes/";
        LinkedList<Coordenada> listCoord = new LinkedList<Coordenada> ();
        //String img_PoI="PoI_01.jpg";

        for(int i=0;i<3;i++){
            Coordenada coord =new Coordenada();
            switch (i) {
                case 0:
                    coord.setLatitud(38.034786);
                    coord.setLongitud(-3.624908);
                    break;
                case 1:
                    coord.setLatitud(38.036157);
                    coord.setLongitud(-3.623432);
                    break;
                case 2:
                    coord.setLatitud(38.040128);
                    coord.setLongitud(-3.62515);
                    break;
            }
            listCoord.add(coord);
        }
       // database = new Database();
        database.open();

        crearInfo(database,"Calle los pinchos", "98733221,99882", "9:40-17:30", "www.castulo.es", "Visitas concertadas");

        for(int k =0;k < n_recor;k++) {
            long[] id_markers = new long[n_mark];
            for (int j = 0; j < n_mark; j++) {
                long[] id_pois = new long[n_pois];
                for (int i = 0; i < n_pois; i++) {
                    //creamos tantos puntos de interes por marcador como diga n_pois

                    id_pois[i] = crearPoI(database,"casa_" + i + "mar_" + j+"rec_"+k, "La ciudad iberorromana de Cástulo se encuentra localizada en " +
                                    "el Alto Guadalquivir, en la provincia de Jaén, en las estribaciones de Sierra Morena, " +
                                    "configurándose como nexo de unión con la meseta peninsular. Plinio el Viejo la utiliza " +
                                    "como marca para definir parte de las dimensiones de Hispania.3 4 Se emplaza en una de " +
                                    "las terrazas de la margen derecha del río Guadalimar, habiéndose estudiado vestigios " +
                                    "del asentamiento tanto en las laderas como en una meseta amurallada ubicada en los " +
                                    "cerros de Plaza de Armas y de La Muela, a unos 300 msnm, controlando un ámbito visual " +
                                    "extenso que domina la vega del río." + i + "mar_" + j+"rec_"+k,
                            1.05 + i, 2.05 + i, uri_img+"PoI_0"+(i)+"_m_0"+(j)+".jpg,"+uri_img+"PoI_0"+(i)+"_m_0"+(j)+".jpg,"+uri_img+"PoI_0"+(i)+"_m_0"+(j)+".jpg","uri_audio_" + i + "mar_" + j+"rec_"+k,
                            "uri_video_" + i + "mar_" + j+"rec_"+k);

                    //   Log.d("PrefActivi_LLenar DB")database.getPuntoInteres(id_pois[i]).toString();
                }
                //creamos tantos marcadores como diga n_mark
                /**
                 * //marcadores en castulo
                switch (j) {
                    case 0:
                        id_markers[j] = crearMarcador(database,"Mosaico de los Amores", "La ciudad iberorromana de Cástulo se encuentra localizada en " +
                 "el Alto Guadalquivir, en la provincia de Jaén, en las estribaciones de Sierra Morena, " +
                 "configurándose como nexo de unión con la meseta peninsular. Plinio el Viejo la utiliza " +
                 "como marca para definir parte de las dimensiones de Hispania.3 4 Se emplaza en una de " +
                 "las terrazas de la margen derecha del río Guadalimar, habiéndose estudiado vestigios " +
                 "del asentamiento tanto en las laderas como en una meseta amurallada ubicada en los " +
                 "cerros de Plaza de Armas y de La Muela, a unos 300 msnm, controlando un ámbito visual " +
                 "extenso que domina la vega del río." + j+"rec_"+k,38.034786, -3.624908, id_pois, uri_img+"marcador_0"+(j)+".jpg");
                        break;
                    case 1:
                        id_markers[j] = crearMarcador(database,"Termas", "La ciudad iberorromana de Cástulo se encuentra localizada en " +
                 "el Alto Guadalquivir, en la provincia de Jaén, en las estribaciones de Sierra Morena, " +
                 "configurándose como nexo de unión con la meseta peninsular. Plinio el Viejo la utiliza " +
                 "como marca para definir parte de las dimensiones de Hispania.3 4 Se emplaza en una de " +
                 "las terrazas de la margen derecha del río Guadalimar, habiéndose estudiado vestigios " +
                 "del asentamiento tanto en las laderas como en una meseta amurallada ubicada en los " +
                 "cerros de Plaza de Armas y de La Muela, a unos 300 msnm, controlando un ámbito visual " +
                 "extenso que domina la vega del río." + j+"rec_"+k,38.036157, -3.623432, id_pois, uri_img+"marcador_0"+(j)+".jpg");
                        break;
                    case 2:
                        id_markers[j] = crearMarcador(database,"Centro de Interpretación", "La ciudad iberorromana de Cástulo se encuentra localizada en " +
                 "el Alto Guadalquivir, en la provincia de Jaén, en las estribaciones de Sierra Morena, " +
                 "configurándose como nexo de unión con la meseta peninsular. Plinio el Viejo la utiliza " +
                 "como marca para definir parte de las dimensiones de Hispania.3 4 Se emplaza en una de " +
                 "las terrazas de la margen derecha del río Guadalimar, habiéndose estudiado vestigios " +
                 "del asentamiento tanto en las laderas como en una meseta amurallada ubicada en los " +
                 "cerros de Plaza de Armas y de La Muela, a unos 300 msnm, controlando un ámbito visual " +
                 "extenso que domina la vega del río." + j+"rec_"+k,38.040128, -3.62515, id_pois, uri_img+"marcador_0"+(j)+".jpg");
                        break;
                    default:     id_markers[j] = crearMarcador(database,"marcador_" + j+"rec_"+k, "descrp_" + j+"rec_"+k, 0 + (j), 0 + j, id_pois, uri_img+"marcador_0"+(j)+".jpg");
                }
                **/
            //marcadores en linares
                switch (j) {
                    case 0:
                        id_markers[j] = crearMarcador(database,"Mosaico de los Amores", "La ciudad iberorromana de Cástulo se encuentra localizada en " +
                                "el Alto Guadalquivir, en la provincia de Jaén, en las estribaciones de Sierra Morena, " +
                                "configurándose como nexo de unión con la meseta peninsular. Plinio el Viejo la utiliza " +
                                "como marca para definir parte de las dimensiones de Hispania.3 4 Se emplaza en una de " +
                                "las terrazas de la margen derecha del río Guadalimar, habiéndose estudiado vestigios " +
                                "del asentamiento tanto en las laderas como en una meseta amurallada ubicada en los " +
                                "cerros de Plaza de Armas y de La Muela, a unos 300 msnm, controlando un ámbito visual " +
                                "extenso que domina la vega del río." + j+"rec_"+k,38.09945, -3.6318, id_pois, uri_img+"marcador_0"+(j)+".jpg");
                        break;
                    case 1:
                        id_markers[j] = crearMarcador(database,"Termas", "La ciudad iberorromana de Cástulo se encuentra localizada en " +
                                "el Alto Guadalquivir, en la provincia de Jaén, en las estribaciones de Sierra Morena, " +
                                "configurándose como nexo de unión con la meseta peninsular. Plinio el Viejo la utiliza " +
                                "como marca para definir parte de las dimensiones de Hispania.3 4 Se emplaza en una de " +
                                "las terrazas de la margen derecha del río Guadalimar, habiéndose estudiado vestigios " +
                                "del asentamiento tanto en las laderas como en una meseta amurallada ubicada en los " +
                                "cerros de Plaza de Armas y de La Muela, a unos 300 msnm, controlando un ámbito visual " +
                                "extenso que domina la vega del río." + j+"rec_"+k,38.09988, -3.63252, id_pois, uri_img+"marcador_0"+(j)+".jpg");
                        break;
                    case 2:
                        id_markers[j] = crearMarcador(database,"Centro de Interpretación", "La ciudad iberorromana de Cástulo se encuentra localizada en " +
                                "el Alto Guadalquivir, en la provincia de Jaén, en las estribaciones de Sierra Morena, " +
                                "configurándose como nexo de unión con la meseta peninsular. Plinio el Viejo la utiliza " +
                                "como marca para definir parte de las dimensiones de Hispania.3 4 Se emplaza en una de " +
                                "las terrazas de la margen derecha del río Guadalimar, habiéndose estudiado vestigios " +
                                "del asentamiento tanto en las laderas como en una meseta amurallada ubicada en los " +
                                "cerros de Plaza de Armas y de La Muela, a unos 300 msnm, controlando un ámbito visual " +
                                "extenso que domina la vega del río." + j+"rec_"+k,38.10028, -3.63327, id_pois, uri_img+"marcador_0"+(j)+".jpg");
                        break;
                    default:     id_markers[j] = crearMarcador(database,"marcador_" + j+"rec_"+k, "descrp_" + j+"rec_"+k, 0 + (j), 0 + j, id_pois, uri_img+"marcador_0"+(j)+".jpg");
                }
                //  id_markers[j] = crearMarcador("marcador_" + j+"rec_"+k, "descrp_" + j+"rec_"+k, 0 + (j), 0 + j, id_pois, uri_img+"marcador_0"+(j)+".jpg");
            }
            database.setCoords(listCoord, crearRecorrido(database,"Recorrido_" + k, "La ciudad iberorromana de Cástulo se encuentra localizada en " +
                            "el Alto Guadalquivir, en la provincia de Jaén, en las estribaciones de Sierra Morena, " +
                            "configurándose como nexo de unión con la meseta peninsular. Plinio el Viejo la utiliza " +
                            "como marca para definir parte de las dimensiones de Hispania.3 4 Se emplaza en una de " +
                            "las terrazas de la margen derecha del río Guadalimar, habiéndose estudiado vestigios " +
                            "del asentamiento tanto en las laderas como en una meseta amurallada ubicada en los " +
                            "cerros de Plaza de Armas y de La Muela, a unos 300 msnm, controlando un ámbito visual " +
                            "extenso que domina la vega del río." + k, "20 min" + k, "200 metros" + k, uri_img + "recorrido_0" + (k) + ".jpg",
                    id_markers));
        }
    }


    public static void leerDb( Database database){

        //leer info
        //Informacion infoDb = database.getInformacion(0);
        // Log.d("Info_DB",infoDb.toString());

        //leer recorridos de la DB
        List<Recorrido> listRecordB;
        listRecordB=database.getAllRecorridos();
        for(Recorrido recorr : listRecordB){
            Log.d("Recorridos_DB", recorr.toString());
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



    private static long crearRecorrido(Database database,String nombre, String descrp, String duracion,
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


    private static long crearMarcador(Database database,String nombre, String descrp,double latitud, double longitud,long [] id_poi,
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

    private static long crearPoI(Database database, String nombre, String descripcion, double latit, double longit,
                          String uri_img, String uri_audio, String uri_video){

        PuntoInteres PoI = new PuntoInteres();
        PoI.setNombre(nombre);
        PoI.setDescripcion(descripcion);
        PoI.setLatitud(latit);
        PoI.setLongitud(longit);
        PoI.setUriAudio((uri_audio));
        PoI.setUriImagen(Utils.separarStringComasAString(uri_img));
        PoI.setUriVideo((uri_video));

        //   Log.d("PoI_Pre_DB",PoI.toString());

        return database.addPuntoInteres(PoI);
    }

    public static long crearInfo(Database database, String direcc, String tlf, String horario, String web, String mas_info){
        //cargar informacion
        Informacion info= new Informacion();
        info.setDireccion(direcc);
        info.setTelefono(tlf);
        info.setHorario(horario);
        info.setWeb(web);
        info.setMasInfo(mas_info);
        //  Log.d("Info_Pre_DB", info.toString());
        //para primera informacion crea
        return database.addInformacion(info);
        //para las siguientes informaciones edita
       // return database.editInformacion(info);
    }
}
