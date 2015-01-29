package es.aplicacion.guiacastulo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import android.app.Activity;
import android.widget.Gallery;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;

import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.model.PuntoInteres;
import es.aplicacion.guiacastulo.db.schema.Database;

public class FichaPuntosInteres extends Activity {


    TextView seleccionado;
    Database database = new Database(this);
    Marcador marcador =new Marcador();
    PuntoInteres PoI =new PuntoInteres();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_puntos_interes);
        Bundle bundle = this.getIntent().getExtras();
        long id_marcador = bundle.getLong("ID_MARCADOR");
        database.open();
        marcador=database.getMarcador(id_marcador);
        //TODO sacar todos los puntos de interes y mostrar su informacion, no solo el primero
        PoI=database.getPuntoInteres(marcador.getId_puntos_interes()[0]);

        // Galeria de imagenes
        Gallery gallery = (Gallery)findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(this));
        TextView infoText =(TextView) findViewById(R.id.infotext);
        infoText.setText(PoI.toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ficha_puntos_interes, menu);
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
}
