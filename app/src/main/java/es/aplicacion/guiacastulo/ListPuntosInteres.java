package es.aplicacion.guiacastulo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import es.aplicacion.guiacastulo.Utilidades.ItemRecorrido;
import es.aplicacion.guiacastulo.Utilidades.ItemRecorridoAdapter;


public class ListPuntosInteres extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_puntos_interes);


        ListView lv = (ListView)findViewById(R.id.listpuntosinteres);

        ArrayList<ItemRecorrido> itemsRecorrido = obtenerItems();

        ItemRecorridoAdapter adapter = new ItemRecorridoAdapter(this, itemsRecorrido);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(mMessageClickedHandler);

    }

    private ArrayList<ItemRecorrido> obtenerItems() {
        ArrayList<ItemRecorrido> items = new ArrayList<ItemRecorrido>();
// Cambiar por acceso a la base de datos
        items.add(new ItemRecorrido(1, "Terma árabe", "Localizacion Norte", "drawable/patatas"));
        items.add(new ItemRecorrido(2, "Mosaico", "Localizacion Sur", "drawable/naranjas"));
        items.add(new ItemRecorrido(3, "Cardo máximo","Localizacion Este", "drawable/lechuga"));

        return items;
    }

    AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position,
                                long id) {

            Intent intent = new Intent(getApplication(), FichaPuntosInteres.class);
            Bundle b = new Bundle();
            b.putLong("ID_FICHA",position);
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
