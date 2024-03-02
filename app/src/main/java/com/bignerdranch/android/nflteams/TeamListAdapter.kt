package com.bignerdranch.android.nflteams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.nflteams.databinding.ListItemTeamBinding

class TeamHolder(private val binding: ListItemTeamBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(team: NflTeam, onTeamClicked: (teamId: String) -> Unit) {
        binding.teamName.text = team.teamName
        binding.teamStadium.text = team.stadium

        binding.root.setOnClickListener {
            onTeamClicked(team.teamID)
        }
    }
}

class TeamListAdapter(private val teams: List<NflTeam>, private val onTeamClicked: (teamId: String) -> Unit) : RecyclerView.Adapter<TeamHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTeamBinding.inflate(inflater, parent, false)
        return TeamHolder(binding)
    }

    override fun getItemCount() = teams.size

    override fun onBindViewHolder(holder: TeamHolder, position: Int) {
        val team = teams[position]
        holder.bind(team, onTeamClicked)
    }
}