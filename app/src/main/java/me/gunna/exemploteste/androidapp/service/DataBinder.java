package me.gunna.exemploteste.androidapp.service;


import android.databinding.BindingAdapter;
import android.view.View;

import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Daniel on 18/07/17.
 */

public class DataBinder  {


    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, boolean visible){
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("app:imageUrl")
    public static void setImage(CircularImageView view, String imgUrl){
        Picasso.with(view.getContext())
                .load(imgUrl)
                .centerCrop()
                .resize(150,150)
                .noFade()
                .into(view);
    }

}
