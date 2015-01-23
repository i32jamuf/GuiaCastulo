package es.aplicacion.guiacastulo;

import android.app.ListActivity;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.aplicacion.guiacastulo.Utilidades.ItemRecorrido;
import es.aplicacion.guiacastulo.Utilidades.ItemRecorridoAdapter;


public class ListRecorridos extends Activity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recorridos);

// Se crea la lista de recorridos
        ListView lv = (ListView)findViewById(R.id.listrecorridos);
// Se cargan los items de los recorridos
        ArrayList<ItemRecorrido> itemsRecorrido = obtenerItems();

    // Se asocian los items al adaptador

        ItemRecorridoAdapter adapter = new ItemRecorridoAdapter(this, itemsRecorrido);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(mMessageClickedHandler);
    }


    OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position,
                                long id) {

            Intent intent = new Intent(getApplication(), DescripcionRecorridos.class);
            Bundle b = new Bundle();
            b.putLong("ID_LISTA",position);
            intent.putExtras(b);
            startActivity(intent);

        }
    };




    private ArrayList<ItemRecorrido> obtenerItems() {
        ArrayList<ItemRecorrido> items = new ArrayList<ItemRecorrido>();

        items.add(new ItemRecorrido (1, "Recorrido árabe", "3 horas y 20 minutos", "drawable/patatas"));
        items.add(new ItemRecorrido(2, "Recorrido musulmán", "4 horas", "drawable/naranjas"));
        items.add(new ItemRecorrido(3, "Recorrido romano", "2 horas y 30 minutos", "drawable/lechuga"));





        return items;
    }



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
