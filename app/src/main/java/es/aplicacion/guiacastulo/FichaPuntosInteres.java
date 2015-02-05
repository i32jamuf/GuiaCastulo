
package es.aplicacion.guiacastulo;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;

import android.app.Activity;
import android.widget.Gallery;
import android.widget.AdapterView;
import android.view.View;

import java.util.LinkedList;

import es.aplicacion.guiacastulo.Utilidades.ImageAdapter;
import es.aplicacion.guiacastulo.Utilidades.ImageDialog;
import es.aplicacion.guiacastulo.Utilidades.Video;
import es.aplicacion.guiacastulo.db.model.Marcador;
import es.aplicacion.guiacastulo.db.model.PuntoInteres;
import es.aplicacion.guiacastulo.db.schema.Database;

public class FichaPuntosInteres extends Activity implements
        MediaController.MediaPlayerControl,
        MediaPlayer.OnBufferingUpdateListener{


    TextView seleccionado;
    Database database = new Database(this);
    Marcador marcador =new Marcador();
    PuntoInteres PoI =new PuntoInteres();
    LinkedList<PuntoInteres> PoIsMarker =new LinkedList<PuntoInteres>();
    Button b_anterior;
    Button b_siguiente;
    int n_PoI=0;
    MediaController mController;
    MediaPlayer mPlayer;
    int bufferPercent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_puntos_interes);
        Bundle bundle = this.getIntent().getExtras();
        long id_marcador = bundle.getLong("ID_MARCADOR");
        database.open();
        marcador=database.getMarcador(id_marcador);
        long [] ids_PoIs=marcador.getId_puntos_interes();

        //llenamos una lista de PoIs con todos los que tiene este marcador
        for(int i=0;i<ids_PoIs.length;i++ ){

            // PuntoInteres poi=database.getPuntoInteres(ids_PoIs[i]);
            //  Log.d("Ficha_PoI",poi.toString());
            PoIsMarker.add(database.getPuntoInteres(ids_PoIs[i]));

        }
        b_anterior= (Button)findViewById(R.id.b_anterior);
        b_siguiente= (Button)findViewById(R.id.b_siguiente);
        //en el primer PoI ocultamos el boton anterior
        b_anterior.setVisibility(View.INVISIBLE);
        //si es el unico PoI ocultamos el boton siguiente
        if(PoIsMarker.size()==1)
            b_siguiente.setVisibility(View.INVISIBLE);

        cargarGallery(PoIsMarker.get(n_PoI));
        cargarDescripcion(PoIsMarker.get(n_PoI));
        mController = new MediaController(this);
        mController.setAnchorView(findViewById(R.id.root));

    }
    @Override
    public void onResume() {
        super.onResume();
        mPlayer = new MediaPlayer();
    }
    /**
    @Override
    public void onPause() {
        super.onPause();
        mPlayer.release();
        mPlayer = null;
    }**/

    @Override
    public void onStop() {
        super.onStop();
        mController.hide();
        mPlayer.release();
      //  mPlayer = null;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ficha_puntos_interes, menu);
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

    public void onButtonSiguienteClicked(View view){
        n_PoI++;
        mController.hide();
        mPlayer.stop();
        mPlayer.release();
        mPlayer = new MediaPlayer();
        cargarGallery(PoIsMarker.get(n_PoI));
        cargarDescripcion(PoIsMarker.get(n_PoI));
        b_anterior.setVisibility(View.VISIBLE);
        //en el ultimo PoI no mostramos siguiente
        if(n_PoI==PoIsMarker.size()-1)
            b_siguiente.setVisibility(View.GONE);
    }

    public void onButtonAnteriorClicked(View view){
        n_PoI--;
        mController.hide();
        mPlayer.stop();
        mPlayer.release();
        mPlayer = new MediaPlayer();
        cargarGallery(PoIsMarker.get(n_PoI));
        cargarDescripcion(PoIsMarker.get(n_PoI));
        b_siguiente.setVisibility(View.VISIBLE);
        //en el primer PoI no mostramos anterior
        if(n_PoI==0)
            b_anterior.setVisibility(View.GONE);
    }

    public void bvideoClick(View view){
        mController.hide();
        cargarVideo();
    }

    public void baudioClick(View view){
        cargarAudio(PoIsMarker.get(n_PoI));
    }

    //TODO rehacer y comentar codigo
    private void cargarGallery(PuntoInteres PoI){

        Gallery gallery = (Gallery)findViewById(R.id.gallery);
        // ImageAdapter imgAdapter = new ImageAdapter(this);
        final LinkedList<String> uris= new LinkedList<String>();
        // String [] stringUris =new String[PoIsMarker.get(n_PoI).getUriImagen().length];
        // System.arraycopy( PoIsMarker.get(n_PoI).getUriImagen(), 0,stringUris, 0, PoIsMarker.get(n_PoI).getUriImagen().length );
        for(int i=0;i<PoI.getUriImagen().length;i++){
            // Uri uri = Uri.parse(PoI.getUriImagen()[i]);
            uris.add(PoI.getUriImagen()[i]);
        }

        final ImageAdapter imgAdapter = new ImageAdapter(this,uris);
        gallery.setAdapter(imgAdapter);
        //  final Integer[] imagesID = imgAdapter.getImgIds();

        //  final List<Uri> uris_img= imgAdapter.getImgIds();
        //  for(int k=0;k<imagesID.length;k++){
        //     Log.d("Ficha PoI galley","imagesID " + imagesID[k]);
        // }
//cargar imagen
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Ficha PoI","Foto " + (i+1));
                //  for(int j=0;j<uris_img.size();j++){
                //     Log.d("Ficha PoI onclick","imagesID " + uris_img.get(j));
                // }
                mController.hide();
                Intent intent= new Intent(FichaPuntosInteres.this, ImageDialog.class);
                Bundle b = new Bundle();

                b.putString("ID_IMG",imgAdapter.getImgUris().get(i).toString());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void cargarDescripcion(PuntoInteres PoI){
        TextView infoText =(TextView) findViewById(R.id.infotext);
        infoText.setText(PoI.toString());
    }
    private void cargarAudio(PuntoInteres PoI){

        //si no se esta reproduciendo el audio, carga uno nuevo
        if(!mPlayer.isPlaying()){
//Set the audio data source
            try {
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.setDataSource(this,
                Uri.parse(Environment.getExternalStorageDirectory().toString()+"/GuiaCastulo/Audios/audio_01.mp3"));
                // Uri.parse(PoI.getUriAudio()));
                //crea un nuevo hilo para cargar el audio
                mPlayer.prepareAsync() ;
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                    @Override
                    public void onPrepared(MediaPlayer mp){
                        mp.start();
                    } });
            } catch (Exception e) {
                e.printStackTrace();
            }

            mController.setMediaPlayer(this);
            mController.setEnabled(true);
        }
        mController.show();
    }
    private void cargarVideo(){
            Intent tostart = new Intent(FichaPuntosInteres.this, Video.class);
            startActivity(tostart);
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
