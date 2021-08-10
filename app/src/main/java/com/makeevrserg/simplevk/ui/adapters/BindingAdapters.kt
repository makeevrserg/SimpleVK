package com.makeevrserg.simplevk.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.makeevrserg.simplevk.R

//Загрузка изображений в ImageView
@BindingAdapter("imageUrl")
fun setImage(image: ImageView, url: String?) {

    if (!url.isNullOrEmpty()) {
        Glide.with(image.context)
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(image)
    }


}