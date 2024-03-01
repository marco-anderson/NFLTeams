package com.bignerdranch.android.nflteams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.nflteams.databinding.FragmentTeamDetailBinding

class NFLTeamDetailFragment : Fragment() {

    private var _binding: FragmentTeamDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var team : NflTeam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        team = NflTeam(
            teamID = "01",
            teamName = "Indianapolis Colts",
            logoFile = "colts.png",
            conference = "American Football Conference",
            division = "South",
            stadium = "Lucas Oil Stadium",
            latitude = 39.759991,
            longitude = -86.163712
        )
    }

    // Create, configure fragment's view; inflate and bind layout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    // Wire up view
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Listeners
        binding.apply {
            teamName.text = team.teamName

            teamConference.text = team.conference

            teamDivision.text = team.division

            teamStadium.text = team.stadium

        }
    }

    // Free up memory & improve performance by clearing reference to fragment view
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}