package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.activity.fixtures.model.EventsModelold
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.events.EventsModel

class EventRepository(private val fixturesService: FixturesService) {

    private val mutableEventData = MutableLiveData<EventsModel>()

    val eventsLiveData: LiveData<EventsModel>
        get() = mutableEventData

    suspend fun getEventsByFixture(fixture: Int) {
        val result = fixturesService.getEventsByFixture(fixture);
        if (result?.body() != null)
            mutableEventData.postValue(result.body())
        else
            showException(result.errorBody().toString())
    }

    suspend fun getEventsByTeam(fixture: Int, id: Int) {
        val result = fixturesService.getEventsByTeam(fixture, id);
        if (result?.body() != null)
            mutableEventData.postValue(result.body())
        else
            showException(result.errorBody().toString())
    }

    private fun showException(exception: String) {

    }

}