package com.bignerdranch.android.nflteams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamDetailViewModel(teamId: String) : ViewModel() {
    // Get singleton instance of "database"
    private val teamRepo = TeamRepo.get()

    private val _teamFlow: MutableStateFlow<NflTeam?> = MutableStateFlow(null)
    val teamFlow: StateFlow<NflTeam?> = _teamFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _teamFlow.value = teamRepo.fetchById(teamId)
        }
    }
}

class TeamDetailViewModelFactory(private val teamId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TeamDetailViewModel(teamId) as T
    }
}