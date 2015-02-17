package es.aplicacion.guiacastulo.Utilidades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import es.aplicacion.guiacastulo.FichaPuntosInteres;
import es.aplicacion.guiacastulo.R;
/**
public class ProximityAlert extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
      final  long id_marcador = bundle.getLong("ID_MARCADOR");
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.titulo_visitar));
        builder.setPositiveButton(getResources().getString(R.string.visitar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ProximityAlert.this, FichaPuntosInteres.class);
                Bundle b = new Bundle();
                //sacamos el valor, mapeado con este marcador de google, del id de la base de datos
                b.putLong("ID_MARCADOR", id_marcador);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.volver_mapa), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }**/


public class ProximityAlert extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        Toast.makeText(getBaseContext(),"Exiting the region"  , Toast.LENGTH_LONG).show();
        final long id_marcador = bundle.getLong("ID_MARCADOR");
       // onCreateDialog(id_marcador);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.titulo_visitar));
        builder.setPositiveButton(getResources().getString(R.string.visitar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ProximityAlert.this, FichaPuntosInteres.class);
                Bundle b = new Bundle();
                //sacamos el valor, mapeado con este marcador de google, del id de la base de datos
                b.putLong("ID_MARCADOR", id_marcador);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.volver_mapa), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
        finish();
    }

    public Dialog onCreateDialog(final long id) {


        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.titulo_visitar));
        builder.setPositiveButton(getResources().getString(R.string.visitar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ProximityAlert.this, FichaPuntosInteres.class);
                Bundle b = new Bundle();
                //sacamos el valor, mapeado con este marcador de google, del id de la base de datos
                b.putLong("ID_MARCADOR", id);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.volver_mapa), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        return builder.show();
    }
        /**
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(items[item]==getResources().getString(R.string.automatic_region)){
                            Intent intent = new Intent(getActivity(),
                                    CrearRegionAuto.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getActivity(),
                                    CrearRegionManual.class);
                            startActivity(intent);
                        }
                    }
                });


    }**/
}
