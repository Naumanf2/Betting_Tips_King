package com.bettingtipsking.app.model.fixture_by_fixture_id

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class FixtureByFixtureId(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
){
    companion object{
        @BindingAdapter("android:fixtureTeamLogo")
        @JvmStatic
        public fun loadCoachImage(imageView: ImageView, url:String?){
            Glide.with(imageView)
                .load(url)
                .into(imageView)
        }
    }
}


