package es.aplicacion.guiacastulo;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import es.aplicacion.guiacastulo.db.model.Informacion;
import es.aplicacion.guiacastulo.db.schema.Database;


public class Info extends Activity {
Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        database=new Database(getApplicationContext());
        database.open();
        Informacion info=database.getInformacion();
        TextView textViewHorario =(TextView)findViewById(R.id.horario);
        textViewHorario.setText(info.getHorario());
        TextView textViewTelefono =(TextView)findViewById(R.id.telefono);
        textViewTelefono.setText(info.getTelefono());
        TextView textViewDireccion =(TextView)findViewById(R.id.direccion);
        textViewDireccion.setText(info.getDireccion());
        TextView textViewWeb =(TextView)findViewById(R.id.web);
        textViewWeb.setText(info.getWeb());
        TextView textViewMas_info =(TextView)findViewById(R.id.mas_info);
        textViewMas_info.setText(info.getMasInfo());
    }

}
