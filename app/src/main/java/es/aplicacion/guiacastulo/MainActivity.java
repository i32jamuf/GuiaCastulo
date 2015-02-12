package es.aplicacion.guiacastulo;

import android.app.Activity;
import android.content.Intent;


import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;

import java.util.Locale;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void brecorridoClick(View view){
    Intent intent= new Intent(MainActivity.this, ListRecorridos.class);
    startActivity(intent);
}

    public void bptointeresClick(View view){
        Intent intent= new Intent(MainActivity.this, ListPuntosInteres.class);
        startActivity(intent);
    }

    public void bajustesClick(View view){
        Intent intent= new Intent(MainActivity.this, PreferenceActivity.class);
        startActivity(intent);

    }
}
