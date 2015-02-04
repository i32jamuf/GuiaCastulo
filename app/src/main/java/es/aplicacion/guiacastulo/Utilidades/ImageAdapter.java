package es.aplicacion.guiacastulo.Utilidades;

/**
 * Created by PUESTO02_EJCI on 28/01/2015.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.GridView;
import android.content.res.TypedArray;
import android.widget.Gallery;

import java.util.LinkedList;
import java.util.List;

import es.aplicacion.guiacastulo.R;


public class ImageAdapter extends BaseAdapter {
    //TODO tamaños a modificar segun tamaños de pantalla
    private int thumbWidth=200;
    private int thumbHeight=200;
    int mGalleryItemBackground;
    private Context mContext;
    private LinkedList<String> mImageURIs=new LinkedList<String>();

    public ImageAdapter(Context c, LinkedList<String> URIs) {
        mContext = c;
        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = attr.getResourceId(
                R.styleable.HelloGallery_android_galleryItemBackground, 0);
        attr.recycle();
        mImageURIs=URIs;
    }

    public void setURIs(LinkedList<String> URIs){
        mImageURIs= URIs;
    }

    public List<String> getImgUris() {
        return mImageURIs;
    }
    public int getCount() {
        return mImageURIs.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);

        //imageView.setImageResource(mImageIds[position]);
        Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mImageURIs.get(position)), thumbWidth, thumbHeight);

        imageView.setImageBitmap(ThumbImage);
        //  imageView.setImageURI(mImageURIs.get(position));

        // imageView.setLayoutParams(new Gallery.LayoutParams(600,550));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setBackgroundResource(mGalleryItemBackground);

        return imageView;
    }
}
