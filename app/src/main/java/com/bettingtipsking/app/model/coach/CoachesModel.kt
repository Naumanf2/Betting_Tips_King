package com.bettingtipsking.app.model.coach

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class CoachesModel(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int)
{
    companion object{
        @BindingAdapter("android:coachImage")
        @JvmStatic
        public fun loadCoachImage(imageView: ImageView, url:String?){
            Glide.with(imageView)
                .load(url)
                .into(imageView)
        }
    }




}