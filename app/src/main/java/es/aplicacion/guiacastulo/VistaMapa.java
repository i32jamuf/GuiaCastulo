package es.aplicacion.guiacastulo;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;

import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import es.aplicacion.guiacastulo.Utilidades.ProximityAlert;
import es.aplicacion.guiacastulo.Utilidades.Utils;
import es.aplicacion.guiacastulo.db.model.Coordenada;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.schema.Database;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

public class VistaMapa extends FragmentActivity  {

    private CameraUpdate mCamera;
    private GoogleMap mMap;
    final int RQS_GooglePlayServices = 1;
    Database database= new Database (this);
    LinkedList<Marcador> markers= new LinkedList<Marcador>();
    HashMap<String, Marcador> markerMap = new HashMap<String, Marcador>();
    private int thumbWidth=200;
    private int thumbHeight=200;
    private LinkedList<LatLng> latlong = new LinkedList<LatLng>();
    List<Coordenada> coords;
    private boolean gps_on=true;
    LocationManager locationManager;
    int nMarcadores=0;
    private static final int SATELLITE = 1;
    private static final int NORMAL = 2;
    private static final int HYBRID = 3;
    private static final int TERRAIN = 4;
    LinkedList<PendingIntent> pendingIntentsList= new LinkedList<PendingIntent>();
    //PendingIntent pendingIntent;
    boolean alertCreated=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setupActionBar();
        setContentView(R.layout.activity_vista_mapa);
        Bundle bundle = this.getIntent().getExtras();
        //sacamos las ids de los marcadores que tiene este recorrido
        long [] ids_marcadores = Utils.separarStringComasALong(bundle.getString("IDS_MARCADORES"));
        long id_recorrido = bundle.getLong("ID_RECORRIDO");
        database.open();
        //sacamos esos marcadores y los metemos en una lista
        for(int i=0; i<ids_marcadores.length;i++){
            Marcador marker= database.getMarcador(ids_marcadores[i]);
            markers.add(marker);
        }
        coords = database.getCoords(id_recorrido);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else { // Google Play Services are available

            setUpMapIfNeeded();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        gps_on= pref.getBoolean("gps_on_key", true);
        int tipoMapa = Integer.parseInt(pref.getString("map_key", "1"));
// Getting Google Play availability status

        switch (tipoMapa) {// Inicializa el tipo de mapa segun las preferencias
            case NORMAL:
                mMap.setMapType(MAP_TYPE_NORMAL);

                break;
            case SATELLITE:
                mMap.setMapType(MAP_TYPE_SATELLITE);

                break;
            case HYBRID:
                mMap.setMapType(MAP_TYPE_HYBRID);

                break;
            case TERRAIN:
                mMap.setMapType(MAP_TYPE_TERRAIN);

                break;
        }
        //registrar y desregistrar a alertproximity, pero salta siempre la alerta de donde estas ty
        // no te deja ver el mapa. cambio a poner una extincion a las 2h
        if(gps_on) {
            Log.d("setUpMap_VistaMapa", "gps_on");
            mMap.setMyLocationEnabled(true);

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            // mMap.setMyLocationEnabled(true);
            for (Marcador marcador : markers) {
                //Intent proximityIntent = new Intent(getApplicationContext(), FichaMarcador.class);
                Intent proximityIntent = new Intent("es.aplicacion.guiacastulo.proximityalert");
                proximityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                proximityIntent.putExtra("ID_MARCADOR", marcador.getId());
                //TODO PendingIntent

                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), nMarcadores,
                        proximityIntent, PendingIntent.FLAG_CANCEL_CURRENT);//FLAG_CANCEL_CURRENT
                nMarcadores++;
                Log.d("addPendigIntent_VistaMapa", pendingIntent.toString());
                locationManager.addProximityAlert(marcador.getLatitud(), marcador.getLongitud(), 10, -1, pendingIntent);
                pendingIntentsList.add(pendingIntent);
            }
        }else{
            mMap.setMyLocationEnabled(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

//TODO desregistrarse al LocationManager o proximitys

        for(int i=0;i< pendingIntentsList.size();i++){
            locationManager.removeProximityAlert(pendingIntentsList.get(i));
            Log.d("removePendigIntent_VistaMapa", pendingIntentsList.get(i).toString());
        }
        pendingIntentsList.clear();
    }

    private void setUpMapIfNeeded() {
        Log.d("setUpMapIfNeeded_VistaMapa", "setUpMapIfNeeded");
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            Log.d("setUpMapIfNeeded_VistaMapa", "mMap == null");
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                Log.d("setUpMapIfNeeded_VistaMapa", "mMap != null");
                //mMap.setMyLocationEnabled(true);
                // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //pintamos los marcadores al inicializar el mapa
        int i=0;
        mCamera = CameraUpdateFactory.newLatLngZoom(new LatLng(markers.getFirst().getLatitud(), markers.getFirst().getLongitud()), 15);
        mMap.animateCamera(mCamera);
        Log.d("setUpMap_VistaMapa", "setUpMap");

        Log.d("setUpMap_VistaMapa", "gps_off");
        // mMap.setMyLocationEnabled(false);
        for(Marcador marcador : markers){
            //si no esta activo el GPS ponemos los marcadores en el mapa de google y
            // mapeamos el id del marcador de google con
            // el id del marcador en la base de datos

            Marker marker =mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(marcador.getLatitud(),marcador.getLongitud()))
                    .title(marcador.getNombre()));
            markerMap.put(marker.getId(),marcador);
            nMarcadores++;
            Log.d("VistaMapa","Mapeo G: "+marker.getId()+" M: "+marcador.getId());
        }


        mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }
            @Override
            //establecemos los contenidos del InfoWindow del marcador de google maps
            public View getInfoContents(Marker marker) {
                View myContentView = getLayoutInflater().inflate(
                        R.layout.info_windows_mapa, null);
                //titulo
                TextView tvTitle = ((TextView) myContentView
                        .findViewById(R.id.titulo));
                tvTitle.setText(marker.getTitle());

                //imagen
                ImageView image = (ImageView) myContentView.findViewById(R.id.imagen_marcador);
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile((markerMap.get(marker.getId()).getUriImagen()[0])), thumbWidth, thumbHeight);
                image.setImageBitmap(ThumbImage);
                return myContentView;
            }
        });
        //al clickar en el marcador
        mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {

                Log.d("VistaMapa","onInfoWindowClick G: "+marker.getId()+" M: "+markerMap.get(marker.getId()));
                Intent intent = new Intent(VistaMapa.this,FichaMarcador.class);
                Bundle b = new Bundle();
                //sacamos el valor, mapeado con este marcador de google, del id de la base de datos
                b.putLong("ID_MARCADOR",markerMap.get(marker.getId()).getId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        //Transformamos las coordenadas a LatLong
        for(Coordenada coord : coords){
            latlong.add(new LatLng(coord.getLatitud(),
                    coord.getLongitud()));
        }

        // Dibujamos el recorrido
        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.addAll(latlong);
        mMap.addPolyline(lineOptions);
    }
}