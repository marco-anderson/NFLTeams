package com.bignerdranch.android.nflteams

import android.app.Application

class NflTeamsApp: Application() {
    override fun onCreate() {
        super.onCreate()
        TeamRepo.initialize()
    }
}