package es.aplicacion.guiacastulo;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import es.aplicacion.guiacastulo.Utilidades.ImageAdapter;
import es.aplicacion.guiacastulo.Utilidades.ImageDialog;
import es.aplicacion.guiacastulo.Utilidades.Utils;
import es.aplicacion.guiacastulo.Utilidades.Video;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.model.Recorrido;
import es.aplicacion.guiacastulo.db.schema.Database;


public class FichaMarcador  extends Activity implements
        MediaController.MediaPlayerControl,
        MediaPlayer.OnBufferingUpdateListener{

    TextView seleccionado;
    Database database= new Database (this);
    long id_marcador=0;
    Marcador marcador=new Marcador();
    MediaController mController;
    MediaPlayer mPlayer;
    int bufferPercent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        boolean proximity_entering = bundle.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING,true);
        if(!proximity_entering) {
          finish();
            Toast.makeText(this,"ESTA SALIENDO",Toast.LENGTH_LONG).show();
        }
        id_marcador = bundle.getLong("ID_MARCADOR");
        setContentView(R.layout.activity_ficha_marcador);

        Toast.makeText(this,"ESTA ENTRANDO",Toast.LENGTH_LONG).show();
        Log.d("PendigIntent_VistaMapa", " "+id_marcador);
        database.open();
        marcador=database.getMarcador(id_marcador);
        cargarGallery(marcador);
        cargarDescripcion(marcador);

        mController = new MediaController(this);
        mController.setAnchorView(findViewById(R.id.root));
    }
    @Override
    public void onResume() {
        super.onResume();
        mPlayer = new MediaPlayer();
    }


    //Llamada a la clase mapa al presionar el boton de Mapa
    public void bMasInfoClick(View view) {
        mController.hide();
        Intent intent = new Intent(FichaMarcador.this, FichaPuntosInteres.class);
        Bundle b = new Bundle();
        b.putLong("ID_MARCADOR", id_marcador);
        intent.putExtras(b);
        startActivity(intent);

    }

    /**
     * Carga una lista de imágenes en la galeria
     * @param marcador
     */
    private void cargarGallery(Marcador marcador){

        Gallery gallery = (Gallery)findViewById(R.id.gallery);
        final LinkedList<String> uris= new LinkedList<String>();
        for(int i=0;i<marcador.getUriImagen().length;i++){
            uris.add(marcador.getUriImagen()[i]);
        }

        final ImageAdapter imgAdapter = new ImageAdapter(this,uris);
        gallery.setAdapter(imgAdapter);

//al clickar en una imagen
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //se inicia la activity que muestra la imagen a pantalla completa
                mController.hide();
                Intent intent= new Intent(FichaMarcador.this, ImageDialog.class);
                Bundle b = new Bundle();
                b.putString("ID_IMG",imgAdapter.getImgUris().get(i).toString());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    public void onButtonVideoClicked(View view){
        mController.hide();
        cargarVideo();

    }
    public void onButtonAudioClicked(View view) {
        cargarAudio();
    }
    /**
     * Carga la descripción del marcador en el TextView
     * @param marcador
     */
    private void cargarDescripcion(Marcador marcador){
        TextView infoText =(TextView) findViewById(R.id.infotext);
        infoText.setText(marcador.toString());
    }


    private void cargarAudio() {
        //si no se esta reproduciendo el audio, carga uno nuevo
        if(!mPlayer.isPlaying()){
//Set the audio data source
            try {
                mPlayer.setDataSource(this,
                        Uri.parse(Environment.getExternalStorageDirectory().toString() + "/GuiaCastulo/Audios/audio_01.mp3"));
                mPlayer.prepareAsync() ;
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){

                    @Override
                    public void onPrepared(MediaPlayer mp){
                        mp.start();
                    } });
                //  mPlayer.start();

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
        Intent  tostart = new Intent(FichaMarcador.this, Video.class);
        startActivity(tostart);
    }

    /**
     @Override
     public void onPause() {
     super.onPause();
     mPlayer.release();
     // mPlayer = null;
     }
     **/
    @Override
    public void onStop() {
        super.onStop();
        mController.hide();
        mPlayer.release();
        // mPlayer = null;
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