package es.aplicacion.guiacastulo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.LinkedList;
import java.util.List;

import es.aplicacion.guiacastulo.Utilidades.Utils;
import es.aplicacion.guiacastulo.db.model.Informacion;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.model.PuntoInteres;
import es.aplicacion.guiacastulo.db.model.Recorrido;
import es.aplicacion.guiacastulo.db.schema.Database;


public class PantallaCarga extends Activity {

    final int WELCOME		= 25;
    TextView linea_ayuda;

    public int progreso=0;
    int paso = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_carga2);
        ImageView imgView = (ImageView) findViewById(R.id.animationImage);
        imgView.setVisibility(ImageView.VISIBLE);
        imgView.setBackgroundResource(R.drawable.frame_animation);

        AnimationDrawable frameAnimation =
                (AnimationDrawable) imgView.getBackground();


        frameAnimation.start();



        // Creamos por primera vez las carpetas donde importar y exportar
        Utils.crearDirSiNoExiste("/GuiaCastulo/Imagenes/");
        Utils.crearDirSiNoExiste("/GuiaCastulo/Videos/");
        Utils.crearDirSiNoExiste("/GuiaCastulo/Audios/");



    }


    protected void onResume() {
        super.onResume();
        cuentaAtras(4000);
    }

    private void cuentaAtras(long milisegundos) {

        CountDownTimer mCountDownTimer;


        mCountDownTimer=new CountDownTimer(milisegundos, paso) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress" + progreso + millisUntilFinished);
                progreso+=paso;

            }

            @Override
            public void onFinish() {

                progreso+=	paso;

                Intent intent= new Intent(PantallaCarga.this, MainActivity.class);
                startActivity(intent);
            }
        };
        mCountDownTimer.start();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_pantalla_carga, menu);
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
