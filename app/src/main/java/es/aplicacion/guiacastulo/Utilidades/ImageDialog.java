package es.aplicacion.guiacastulo.Utilidades;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import es.aplicacion.guiacastulo.R;

    public class ImageDialog extends Activity {

        private ImageView mDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_image_dialog);
            Bundle bundle = this.getIntent().getExtras();
            int id_img = bundle.getInt("ID_IMG");
            mDialog = (ImageView)findViewById(R.id.your_image);

            mDialog.setImageResource(id_img);
            mDialog.setClickable(true);
            //finish the activity (dismiss the image dialog) if the user clicks
            //anywhere on the image
            mDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }