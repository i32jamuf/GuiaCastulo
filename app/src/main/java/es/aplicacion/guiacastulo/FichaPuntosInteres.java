package es.aplicacion.guiacastulo;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.Activity;
import android.widget.Gallery;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;

import java.util.LinkedList;

import es.aplicacion.guiacastulo.Utilidades.ImageDialog;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.model.PuntoInteres;
import es.aplicacion.guiacastulo.db.schema.Database;

public class FichaPuntosInteres extends Activity {


    TextView seleccionado;
    Database database = new Database(this);
    Marcador marcador =new Marcador();
    PuntoInteres PoI =new PuntoInteres();
    LinkedList<PuntoInteres> PoIsMarker =new LinkedList<PuntoInteres>();
    Button b_anterior;
    Button b_siguiente;
    int n_PoI=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_puntos_interes);
        Bundle bundle = this.getIntent().getExtras();
        long id_marcador = bundle.getLong("ID_MARCADOR");
        database.open();
        marcador=database.getMarcador(id_marcador);
        long [] ids_PoIs=marcador.getId_puntos_interes();

        //llenamos una lista de PoIs con todos los que tiene este marcador
        for(int i=0;i<ids_PoIs.length;i++ ){

           // PuntoInteres poi=database.getPuntoInteres(ids_PoIs[i]);
          //  Log.d("Ficha_PoI",poi.toString());
            PoIsMarker.add(database.getPuntoInteres(ids_PoIs[i]));

        }
        b_anterior= (Button)findViewById(R.id.b_anterior);
        b_siguiente= (Button)findViewById(R.id.b_siguiente);
        //en el primer PoI ocultamos el boton anterior
        b_anterior.setVisibility(View.INVISIBLE);
        //si es el unico PoI ocultamos el boton siguiente
        if(PoIsMarker.size()==1)
        b_siguiente.setVisibility(View.INVISIBLE);

        cargarGallery(PoIsMarker.get(n_PoI));
        cargarDescripcion(PoIsMarker.get(n_PoI));
        cargarAudio(PoIsMarker.get(n_PoI));
        cargarVideo(PoIsMarker.get(n_PoI));
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

    public void onButtonSiguienteClicked(View view){
        n_PoI++;
        cargarGallery(PoIsMarker.get(n_PoI));
        cargarDescripcion(PoIsMarker.get(n_PoI));
        cargarAudio(PoIsMarker.get(n_PoI));
        cargarVideo(PoIsMarker.get(n_PoI));
        b_anterior.setVisibility(View.VISIBLE);
        //en el ultimo PoI no mostramos siguiente
        if(n_PoI==PoIsMarker.size()-1)
        b_siguiente.setVisibility(View.GONE);

    }
    public void onButtonAnteriorClicked(View view){
        n_PoI--;
        cargarGallery(PoIsMarker.get(n_PoI));
        cargarDescripcion(PoIsMarker.get(n_PoI));
        cargarAudio(PoIsMarker.get(n_PoI));
        cargarVideo(PoIsMarker.get(n_PoI));
        b_siguiente.setVisibility(View.VISIBLE);
        //en el primer PoI no mostramos anterior
        if(n_PoI==0)
        b_anterior.setVisibility(View.GONE);
    }

    private void cargarGallery(PuntoInteres PoI){

        Gallery gallery = (Gallery)findViewById(R.id.gallery);
        ImageAdapter imgAdapter = new ImageAdapter(this);
        gallery.setAdapter(imgAdapter);
        final Integer[] imagesID = imgAdapter.getImgIds();
        for(int k=0;k<imagesID.length;k++){
            Log.d("Ficha PoI galley","imagesID " + imagesID[k]);
        }
//cargar imagen
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Ficha PoI","Foto " + (i+1));
                for(int j=0;j<imagesID.length;j++){
                    Log.d("Ficha PoI onclick","imagesID " + imagesID[j]);
                }
                Intent intent= new Intent(FichaPuntosInteres.this, ImageDialog.class);
                Bundle b = new Bundle();
                b.putInt("ID_IMG",imagesID[i]);
                intent.putExtras(b);
                startActivity(intent);

        }
        });


    }

    private void cargarDescripcion(PuntoInteres PoI){
        TextView infoText =(TextView) findViewById(R.id.infotext);
        infoText.setText(PoI.toString());
    }
    private void cargarAudio(PuntoInteres PoI){

    }
    private void cargarVideo(PuntoInteres PoI){

    }

}
