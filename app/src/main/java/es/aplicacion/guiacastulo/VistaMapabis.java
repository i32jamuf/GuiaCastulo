package es.aplicacion.guiacastulo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by PUESTO02_EJCI on 27/01/2015.
 */
public class VistaMapabis {

    public class VistaMapa extends FragmentActivity {



        //Para que aparezca la actionBAR hay que heredar de la clase ActionBarActivity


        private CameraUpdate mCamera;
        private GoogleMap mMap; // Might be null if Google Play services APK is not available.
        // Esto es un cambio de mapa de castulo
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // setupActionBar();
            setContentView(R.layout.activity_vista_mapa);
            setUpMapIfNeeded();





        }

        @Override
        protected void onResume() {
            super.onResume();
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

            Marker marcador=  mMap.addMarker(new MarkerOptions().position(new LatLng(38.03602777,-3.6233333)).title("Jaen")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

                    .snippet("Yacimiento de Castulo"));
            mCamera = CameraUpdateFactory.newLatLngZoom(new LatLng(38.03602777, -3.6233333), 14);
            mMap.animateCamera(mCamera);

            marcador.showInfoWindow();


            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {



                    Toast.makeText(VistaMapa.this, "Marcador presionado:\n" +
                            marker.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VistaMapa.this,FichaPuntosInteres.class);
                    Bundle b = new Bundle();
                    b.putLong("ID_FICHA",1000);// Cambiar cuando se haga
                    intent.putExtras(b);
                    startActivity(intent);

                    return false;
                }
            });
        }

        public boolean onCreateOptionsMenu(Menu menu){
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.menu_mapa, menu);
            return super.onCreateOptionsMenu(menu);
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

}
