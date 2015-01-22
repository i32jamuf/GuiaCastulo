package es.aplicacion.guiacastulo;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class PantallaCarga extends ActionBarActivity {

    final int WELCOME		= 25;

    TextView linea_ayuda;
    ProgressBar mProgressBar;
    public int progreso=0;
    int paso = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_carga);

        mProgressBar=(ProgressBar) findViewById(R.id.progressbar);
        linea_ayuda = (TextView) findViewById(R.id.linea_ayuda);
    }

    @Override
    protected void onResume() {
        super.onResume();


        cuentaAtras(4000);


    }

    private void cuentaAtras(long milisegundos){
        CountDownTimer mCountDownTimer;

        mProgressBar.setMax((int)milisegundos);

        mProgressBar.setProgress(paso);

        mCountDownTimer=new CountDownTimer(milisegundos, paso) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress" + progreso + millisUntilFinished);
                progreso+=paso;
                mProgressBar.setProgress(progreso);

            }

            @Override
            public void onFinish() {

                Toast.makeText(getApplicationContext(), getString(R.string.db_ok), Toast.LENGTH_LONG).show();
                progreso+=	paso;
                mProgressBar.setProgress(progreso);
                mProgressBar.setVisibility(View.INVISIBLE);
                Intent i = new Intent ("es.aplicacion.guiacastulo.MainActivity");
                startActivityForResult(i, WELCOME);

            }
        };

        mCountDownTimer.start();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==WELCOME)
            // volvemos a la pantalla de bienvenida desde la pantalla principal,
            // la cerramos (no tiene sentido permanecer aquÃ­):
            finish();
        else
            super.onActivityResult(requestCode, resultCode, data);
    }









    @Override
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
