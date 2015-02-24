package es.aplicacion.guiacastulo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import es.aplicacion.guiacastulo.Utilidades.Utils;
import es.aplicacion.guiacastulo.Utilidades.dBTest;
import es.aplicacion.guiacastulo.db.model.Coordenada;
import es.aplicacion.guiacastulo.db.model.Informacion;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.model.PuntoInteres;
import es.aplicacion.guiacastulo.db.model.Recorrido;
import es.aplicacion.guiacastulo.db.schema.Database;


public class PreferenceActivity extends android.preference.PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_preference);


        addPreferencesFromResource(R.xml.ajustes);

        Database database= new Database(getApplicationContext());
        dBTest.llenarDB(database,3, 3, 3);
      //  dBTest.leerDb(database);
        database.open();
        dBTest.crearInfo(database,"Calle cieza", "Tlf: 98733221, Fax: 99882", "9:40-17:30", "www.castulo.es", "Visitas concertadas");
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
        if (id == R.id.action_info) {
            startActivity(new Intent(PreferenceActivity.this, Info.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
