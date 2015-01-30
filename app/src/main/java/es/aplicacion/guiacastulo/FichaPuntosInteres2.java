package es.aplicacion.guiacastulo;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;



/**
 * Created by PUESTO02_EJCI on 29/01/2015.
 */
public class FichaPuntosInteres2 extends ActionBarActivity {

    private Integer[] imagenes = {
            R.drawable.image_0,
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4,
            R.drawable.image_5,
            R.drawable.image_6,
            R.drawable.image_7
    };

    ManejadoraGaleria manejadorGaleria;
    ViewPager mViewPager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_puntos_interes);
        Bundle bundle = this.getIntent().getExtras();
        long id_marcador = bundle.getLong("ID_MARCADOR");


            //galeria de imagenes

            manejadorGaleria = new ManejadoraGaleria(getSupportFragmentManager());

         //   mViewPager = (ViewPager) findViewById(R.id.pager);
            manejadorGaleria.agregarFragmentos(FragmentosImagenes.newInstance(imagenes[0]));
            manejadorGaleria.agregarFragmentos(FragmentosImagenes.newInstance(imagenes[1]));
            manejadorGaleria.agregarFragmentos(FragmentosImagenes.newInstance(imagenes[2]));
            manejadorGaleria.agregarFragmentos(FragmentosImagenes.newInstance(imagenes[3]));
            manejadorGaleria.agregarFragmentos(FragmentosImagenes.newInstance(imagenes[4]));
            manejadorGaleria.agregarFragmentos(FragmentosImagenes.newInstance(imagenes[5]));
            mViewPager.setAdapter(manejadorGaleria);

        }



        public class ManejadoraGaleria extends FragmentPagerAdapter {

            List<Fragment> fragmentos;
            public ManejadoraGaleria(FragmentManager fm) {
                super(fm);
                fragmentos = new ArrayList();
            }

            public void agregarFragmentos(Fragment xfragmento){
                fragmentos.add(xfragmento);
            }


            @Override
            public Fragment getItem(int position) {
                return fragmentos.get(position);
            }

            @Override
            public int getCount() {
                return fragmentos.size();
            }
        }


        public static class FragmentosImagenes extends Fragment {

            private static final String ARG_IMAGE = "imagen";
            private int imagen;

            public static FragmentosImagenes newInstance(int imagen) {
                FragmentosImagenes fragment = new FragmentosImagenes();
                Bundle args = new Bundle();
                args.putInt(ARG_IMAGE, imagen);
                fragment.setArguments(args);
                fragment.setRetainInstance(true);
                return fragment;
            }

            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                if(getArguments() != null) {
                    imagen = getArguments().getInt(ARG_IMAGE);
                }
            }

            public FragmentosImagenes() {
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment, container, false);

                ImageView imagenView = (ImageView) rootView.findViewById(R.id.imageView1);
                imagenView.setImageResource(imagen);
                return rootView;
            }
        }

    }


