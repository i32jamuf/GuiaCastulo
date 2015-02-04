package es.aplicacion.guiacastulo.Utilidades;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.aplicacion.guiacastulo.R;

/**
 * Created by PUESTO02_EJCI on 22/01/2015.
 */
public class ItemRecorridoAdapter extends BaseAdapter {



    //TODO tamaños a modificar segun tamaños de pantalla
    private int thumbWidth=200;
    private int thumbHeight=200;

    protected Activity activity;
    protected ArrayList<ItemRecorrido> items;

    public ItemRecorridoAdapter(Activity activity, ArrayList<ItemRecorrido> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.listaelementos, null);
        }

        ItemRecorrido item = items.get(position);

        ImageView image = (ImageView) vi.findViewById(R.id.imagen);
        Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(item.getRutaImagen()), thumbWidth, thumbHeight);
        // image.setImageURI(Uri.parse(item.getRutaImagen()));
        // int imageResource = activity.getResources().getIdentifier(item.getRutaImagen(), null, activity.getPackageName());
        //image.setImageDrawable(activity.getResources().getDrawable(imageResource));
        image.setImageBitmap(ThumbImage);

        TextView titulo = (TextView) vi.findViewById(R.id.titulo);
        titulo.setText(item.getTitulo());

        TextView subtitulo = (TextView) vi.findViewById(R.id.subtitulo);
        subtitulo.setText(item.getSubtitulo());

        return vi;
    }
}
