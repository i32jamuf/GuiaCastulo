package es.aplicacion.guiacastulo.Utilidades;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import es.aplicacion.guiacastulo.R;

public class Video extends Activity {

    VideoView videoView;
    MediaController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = new VideoView(this);
        // VideoView videoView = (VideoView)findViewById(R.id.video);
        videoView.setVideoURI( Uri.parse(Environment.getExternalStorageDirectory().toString()+"/GuiaCastulo/Videos/vid_01.mp4") );
        controller = new MediaController(this);
        videoView.setMediaController(controller);
        videoView.start();
        setContentView(videoView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video, menu);
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
