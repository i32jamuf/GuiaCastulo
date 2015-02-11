package es.aplicacion.guiacastulo.Utilidades;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        final VideoView videoView = (VideoView)findViewById(R.id.video);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        // Configure the video view and assign a source video.
        videoView.setKeepScreenOn(true);
        videoView.setVideoPath(Environment.getExternalStorageDirectory().toString()+"/GuiaCastulo/Videos/vid_01.mp4");
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
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
