package com.bettingtipsking.app.model

import android.os.Parcel
import android.os.Parcelable
import com.bettingtipsking.app.model.fixtures.Fixture
import com.bettingtipsking.app.model.fixtures.League

data class FinalFixturesModel(val league: League, val matches: List<FinalMatchesModel>) : Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("league"),
        TODO("matches")) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<FinalFixturesModel> {
        override fun createFromParcel(parcel: Parcel): FinalFixturesModel {
            return FinalFixturesModel(parcel)
        }

        override fun newArray(size: Int): Array<FinalFixturesModel?> {
            return arrayOfNulls(size)
        }
    }
}