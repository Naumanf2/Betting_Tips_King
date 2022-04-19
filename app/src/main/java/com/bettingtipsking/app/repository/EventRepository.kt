package com.bettingtipsking.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bettingtipsking.app.activity.fixtures.model.EventsModelold
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.events.EventsModel

class EventRepository(private val fixturesService: FixturesService) {

    val mutableEventData = MutableLiveData<EventsModel>()
    val progressMutableEventData = MutableLiveData<Int>()

    val eventsLiveData: LiveData<EventsModel>
        get() = mutableEventData

    suspend fun getEventsByFixture(fixture: Int) {
        try {
            progressMutableEventData.postValue(0)
            val result = fixturesService.getEventsByFixture(fixture);
            if (result?.body() != null){
                progressMutableEventData.postValue(1)
                mutableEventData.postValue(result.body())
            }
        }catch (exception:Exception){
            progressMutableEventData.postValue(1)
            //todo handal expection
        }
    }

    suspend fun getEventsByTeam(fixture: Int, id: Int) {
        try {
            progressMutableEventData.postValue(0)
            val result = fixturesService.getEventsByTeam(fixture, id);
            if (result?.body() != null){
                progressMutableEventData.postValue(1)
                mutableEventData.postValue(result.body())
            }
        }catch (exception:Exception){
            progressMutableEventData.postValue(1)
        }

    }


}