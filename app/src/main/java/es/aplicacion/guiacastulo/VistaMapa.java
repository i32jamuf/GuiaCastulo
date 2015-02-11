package es.aplicacion.guiacastulo;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
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

import es.aplicacion.guiacastulo.Utilidades.Utils;
import es.aplicacion.guiacastulo.db.model.Coordenada;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.schema.Database;

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
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        //pintamos los marcadores al inicializar el mapa
        int i=0;
        for(Marcador marcador : markers){
            if(i==0){
                mCamera = CameraUpdateFactory.newLatLngZoom(new LatLng(marcador.getLatitud(), marcador.getLongitud()), 15);
                mMap.animateCamera(mCamera);
                i++;
            }

            //ponemos los marcadores en el mapa de google y mapeamos el id del marcador de google con
            // el id del marcador en la base de datos

            Marker marker =mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(marcador.getLatitud(),marcador.getLongitud()))
                    .title(marcador.getNombre())
                    .snippet(marcador.getDescripcion()));
            markerMap.put(marker.getId(),marcador);
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
                //descripcion
                TextView tvSnippet = ((TextView) myContentView
                        .findViewById(R.id.subtitulo));
                tvSnippet.setText(marker.getSnippet());
                //imagen
                ImageView image = (ImageView) myContentView.findViewById(R.id.imagen_marcador);
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(Utils.crearStringComas(markerMap.get(marker.getId()).getUriImagen())), thumbWidth, thumbHeight);
                image.setImageBitmap(ThumbImage);
                return myContentView;
            }
        });
        //al clickar en el marcador
        mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {


            @Override
            public void onInfoWindowClick(Marker marker) {
                // TODO Auto-generated method stub
                Log.d("VistaMapa","onInfoWindowClick G: "+marker.getId()+" M: "+markerMap.get(marker.getId()));
                Intent intent = new Intent(VistaMapa.this,FichaPuntosInteres.class);
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

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_mapa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (resultCode == ConnectionResult.SUCCESS){
            //  Toast.makeText(getApplicationContext(),"isGooglePlayServicesAvailable SUCCESS",Toast.LENGTH_LONG).show();
        }else{
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.MenuOpcion1:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // Establecemos el mapa normal
                return true;

            case R.id.MenuOpcion2:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); // Establecemos el mapa satelite
                return true;

            case R.id.MenuOpcion3:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN); // Establecemos el mapa terrestre
                return true;

            case R.id.MenuOpcion4:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID); // Establecemos el mapa hibrido
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}