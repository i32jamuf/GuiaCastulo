package es.aplicacion.guiacastulo;

import android.app.Activity;
import android.content.Intent;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.util.LinkedList;

import es.aplicacion.guiacastulo.Utilidades.ImageAdapter;
import es.aplicacion.guiacastulo.Utilidades.ImageDialog;
import es.aplicacion.guiacastulo.Utilidades.Utils;
import es.aplicacion.guiacastulo.Utilidades.Video;
import es.aplicacion.guiacastulo.db.model.Recorrido;
import es.aplicacion.guiacastulo.db.schema.Database;


public class DescripcionRecorridos extends Activity implements
        MediaController.MediaPlayerControl,
        MediaPlayer.OnBufferingUpdateListener{


    TextView seleccionado;
    Database database= new Database (this);
    long id_recorrido=0;
    Recorrido recorrido=new Recorrido();
    MediaController mController;
    MediaPlayer mPlayer;
    int bufferPercent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_recorridos);

        Bundle bundle = this.getIntent().getExtras();
        id_recorrido = bundle.getLong("ID_RECORRIDO");
        database.open();
        recorrido=database.getRecorrido(id_recorrido);
        cargarGallery(recorrido);
        cargarDescripcion(recorrido);

        mController = new MediaController(this);
        mController.setAnchorView(findViewById(R.id.root));


    }
    @Override
    public void onResume() {
        super.onResume();
        mPlayer = new MediaPlayer();
    }


    //Llamada a la clase mapa al presionar el boton de Mapa
    public void bmapaClick(View view) {
        Intent intent = new Intent(DescripcionRecorridos.this, VistaMapa.class);
        Bundle b = new Bundle();
        b.putString("IDS_MARCADORES", Utils.crearStringComas(recorrido.getId_marcadores()));
        b.putLong("ID_RECORRIDO",recorrido.getId());
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

    /**
     * Carga una lista de imágenes en la galeria
     * @param recorrido
     */
    private void cargarGallery(Recorrido recorrido){

        Gallery gallery = (Gallery)findViewById(R.id.gallery);
        final LinkedList<String> uris= new LinkedList<String>();
        for(int i=0;i<recorrido.getUriImagen().length;i++){
            uris.add(recorrido.getUriImagen()[i]);
        }

        final ImageAdapter imgAdapter = new ImageAdapter(this,uris);
        gallery.setAdapter(imgAdapter);

//al clickar en una imagen
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //se inicia la activity que muestra la imagen a pantalla completa
                Intent intent= new Intent(DescripcionRecorridos.this, ImageDialog.class);
                Bundle b = new Bundle();
                b.putString("ID_IMG",imgAdapter.getImgUris().get(i).toString());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    /**
     * Carga la descripción del recorrido en el TextView
     * @param recorrido
     */
    private void cargarDescripcion(Recorrido recorrido){
        TextView infoText =(TextView) findViewById(R.id.infotext);
        infoText.setText(recorrido.toString());
    }

    private void cargarAudio() {
        //si no se esta reproduciendo el audio, carga uno nuevo
        if(!mPlayer.isPlaying()){
//Set the audio data source
            try {
                mPlayer.setDataSource(this,
                        Uri.parse(Environment.getExternalStorageDirectory().toString()+"/GuiaCastulo/Audios/audio_01.mp3"));
                mPlayer.prepare();
                mPlayer.start();

            } catch (Exception e) {
                e.printStackTrace();
            }

            mController.setMediaPlayer(this);
            mController.setEnabled(true);


        }
        mController.show();
    }

    private void cargarVideo(){
        //  videoPlayer(Environment.getExternalStorageDirectory().toString()+"/GuiaCastulo/Videos/", "vid_01.mp4", true);
/**
 Intent  tostart = new Intent(Intent.ACTION_VIEW,Uri.parse("file:/"+Environment.getExternalStorageDirectory().toString()+"/vid_01.mp4"));
 Log.d("DescrpRecorridos", "Video URI: file:/"+Environment.getExternalStorageDirectory().toString()+"/vid_1.mp4");
 tostart.setDataAndType(Uri.parse("file:/"+Environment.getExternalStorageDirectory().toString()+"/vid_01.mp4"),"video/*");
 **/
        Intent  tostart = new Intent(DescripcionRecorridos.this, Video.class);
        startActivity(tostart);
    }

    public void onButtonVideoClicked(View view){
        cargarVideo();

    }
    public void onButtonAudioClicked(View view){
        cargarAudio();

    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayer.release();
        // mPlayer = null;
    }

    @Override
    public void onStop() {
        super.onPause();
        mPlayer.release();
        // mPlayer = null;
    }

    //si se esta reproduciendo audio al pulsar la pantalla muestra los controles
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mPlayer.isPlaying())
            mController.show();
        return super.onTouchEvent(event);
    }
    //MediaPlayerControl Methods

    @Override
    public int getBufferPercentage() {
        return bufferPercent;
    }
    @Override
    public int getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }
    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }
    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }
    @Override
    public int getAudioSessionId (){
        return 0;
    }
    @Override
    public void pause() {
        mPlayer.pause();
    }
    @Override
    public void seekTo(int pos) {
        mPlayer.seekTo(pos);
    }
    @Override
    public void start() {
        mPlayer.start();
    }
    //BufferUpdateListener Methods
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        bufferPercent = percent;
    }
    //Android 2.0+ Target Callbacks
    public boolean canPause() {
        return true;
    }
    public boolean canSeekBackward() {
        return true;
    }
    public boolean canSeekForward() {
        return true;
    }

}
