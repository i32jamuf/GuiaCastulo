package es.aplicacion.guiacastulo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import es.aplicacion.guiacastulo.Utilidades.Utils;
import es.aplicacion.guiacastulo.db.model.Recorrido;
import es.aplicacion.guiacastulo.db.schema.Database;


public class DescripcionRecorridos extends ActionBarActivity {


    TextView seleccionado;
    Database database= new Database (this);
    long id_recorrido=0;
    Recorrido recorrido=new Recorrido();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_recorridos);

        Bundle bundle = this.getIntent().getExtras();
        id_recorrido = bundle.getLong("ID_RECORRIDO");
        database.open();
        recorrido=database.getRecorrido(id_recorrido);
        seleccionado= (TextView)findViewById(R.id.seleccionado);
        seleccionado.setText(recorrido.toString());

        // Para probar que funciona la posicion
       //seleccionado= (TextView)findViewById(R.id.seleccionado);

       //seleccionado.setText("Has seleccionado: " + position + " Nombre seleccionado: "+ nombre);

       // Intent intent= new Intent(DescripcionRecorridos.this, VistaMapa.class);
       // startActivity(intent);


    }



    //Llamada a la clase mapa al presionar el boton de Mapa
    public void bmapaClick(View view) {
        Intent intent = new Intent(DescripcionRecorridos.this, VistaMapa.class);
        Bundle b = new Bundle();
        b.putString("IDS_MARCADORES", Utils.crearStringComas(recorrido.getId_marcadores()));
        intent.putExtras(b);
        startActivity(intent);

    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_descripcion_recorridos, menu);
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
