package es.aplicacion.guiacastulo;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaActionSound;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.aplicacion.guiacastulo.Utilidades.ItemRecorrido;
import es.aplicacion.guiacastulo.Utilidades.ItemRecorridoAdapter;
import es.aplicacion.guiacastulo.Utilidades.Utils;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.model.PuntoInteres;
import es.aplicacion.guiacastulo.db.model.Recorrido;
import es.aplicacion.guiacastulo.db.schema.Database;


public class ListPuntosInteres extends Activity {

    Database database = new Database(this);
    List<Marcador> listamarcadores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_puntos_interes);
        database.open();
        listamarcadores = database.getAllMarcadores();

        ListView lv = (ListView)findViewById(R.id.listpuntosinteres);

        ArrayList<ItemRecorrido> itemsRecorrido = obtenerItems();

        ItemRecorridoAdapter adapter = new ItemRecorridoAdapter(this, itemsRecorrido);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(mMessageClickedHandler);

    }

    private ArrayList<ItemRecorrido> obtenerItems() {
        ArrayList<ItemRecorrido> items = new ArrayList<ItemRecorrido>();
// Cambiar por acceso a la base de datos de android
       // items.add(new ItemRecorrido(1, "Terma árabe", "Localizacion Norte", "drawable/patatas"));
        //items.add(new ItemRecorrido(2, "Mosaico", "Localizacion Sur", "drawable/naranjas"));
        //items.add(new ItemRecorrido(3, "Cardo máximo","Localizacion Este", "drawable/lechuga"));
        for(Marcador marcadores : listamarcadores){
            items.add(new ItemRecorrido(marcadores.getId(),marcadores.getNombre(),"", Utils.crearStringComas(marcadores.getUriImagen())));
        }

        return items;
    }

    AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position,
                                long id) {

            Intent intent = new Intent(ListPuntosInteres.this, FichaPuntosInteres.class);
            Bundle b = new Bundle();
            b.putLong("ID_MARCADOR",obtenerItems().get(position).getId());
            intent.putExtras(b);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_recorridos, menu);
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
