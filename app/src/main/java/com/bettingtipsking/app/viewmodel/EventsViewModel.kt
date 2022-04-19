package com.bettingtipsking.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingtipsking.app.activity.fixtures.model.EventsModelold
import com.bettingtipsking.app.api.FixturesService
import com.bettingtipsking.app.model.events.EventsModel
import com.bettingtipsking.app.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsViewModel(private val fixturesService: FixturesService) : ViewModel() {

    private val eventsRepository = EventRepository(fixturesService);

    val eventsLiveData: MutableLiveData<EventsModel>
        get() = eventsRepository.mutableEventData
    val progressMutableEventData: LiveData<Int>
        get() = eventsRepository.progressMutableEventData


    fun getEventsByFixture(fixture: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            eventsRepository.getEventsByFixture(fixture)
        }
    }

    fun getEventsByTeam(fixture: Int, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            eventsRepository.getEventsByTeam(fixture, id)
        }
    }
}