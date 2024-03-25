package com.bignerdranch.android.nflteams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.nflteams.databinding.FragmentTeamDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import kotlinx.coroutines.launch
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private const val TAG = "NFLTeamDetailFragment"
class NFLTeamDetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentTeamDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var map: GoogleMap
    private lateinit var currentTeam: NflTeam

    private val args: NFLTeamDetailFragmentArgs by navArgs()

    private val teamDetailViewModel: TeamDetailViewModel by viewModels() {
        TeamDetailViewModelFactory(args.teamId)
    }

    // Create, configure fragment's view; inflate and bind layout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamDetailBinding.inflate(layoutInflater, container, false)
        binding.mapView.onCreate(savedInstanceState)
        return binding.root
    }

    // Wire up view
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.getMapAsync(this)

        // Listeners
        binding.apply {

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                teamDetailViewModel.teamFlow.collect {team ->
                    team?.let { updateUi(it) }
                }
            }
        }
    }

    // Free up memory & improve performance by clearing reference to fragment view
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(team: NflTeam) {
        // Store the team to use in MapView
        currentTeam = team
        binding.apply {
            val resourceName = team.logoFile.substring(0, team.logoFile.lastIndexOf('.'))
            val resourceId = resources.getIdentifier(resourceName, "drawable", requireContext().packageName)
            teamLogo.setImageResource(resourceId)

            teamName.text = team.teamName
            teamDivision.text = team.division
            teamStadium.text = team.stadium
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false

        val teamLocation: LatLng

        currentTeam.let {team ->
            teamLocation = LatLng(team.latitude, team.longitude)
        }

        map.addMarker(
            MarkerOptions()
                .position(teamLocation)
                .visible(true)
        )

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(teamLocation, 15f))
        binding.mapView.onResume()
    }
}