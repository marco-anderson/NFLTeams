package com.bignerdranch.android.nflteams

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "TeamListVM"
class TeamListViewModel : ViewModel() {
    private val teamRepo: TeamRepo = TeamRepo.get()
    private val _teamsFlow: MutableStateFlow<List<NflTeam>> = MutableStateFlow(emptyList())
    val teamsFlow: StateFlow<List<NflTeam>>
        get() = _teamsFlow.asStateFlow()

    init {
        Log.d(TAG, "init started")
        viewModelScope.launch {
            teamRepo.fetchTeams().collect {teams ->
                _teamsFlow.value = teams
            }
        }
    }
}