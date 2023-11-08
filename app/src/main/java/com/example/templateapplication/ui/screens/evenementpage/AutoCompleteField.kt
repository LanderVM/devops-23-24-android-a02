package com.example.templateapplication.ui.screens.evenementpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.templateapplication.R
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

/*
@Composable
fun MyComposable(navController: NavController) {
    // Initialize the AutocompleteSupportFragment
    val autocompleteFragment = viewModel<AutocompleteSupportFragmentViewModel>().autocompleteFragment
    val containerId = R.layout.autocomplete_google_maps // Replace with the ID of the container in your XML layout

    FragmentContainerView() {
        // Define a container for the AutocompleteSupportFragment
        id = containerId
    }

    // Embed the AutocompleteSupportFragment in a Composable using AndroidView
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val fragment = autocompleteFragment
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment
        }
    ) { autocompleteSupportFragment ->
        // Callbacks or additional setup can be done here
    }
    ComposeUIFragment()
    // Handle navigation or other Composable content as needed
}

class ComposeUIFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.autocomplete_google_maps, container, false).apply {
            findViewById<ComposeView>(R.id.autocomplete_fragment).setContent {
                Text(text = "Hello world.")
            }
        }
    }
}

class AutocompleteSupportFragmentViewModel : ViewModel() {
    lateinit var autocompleteFragment: AutocompleteSupportFragment

    fun initializeAutocompleteFragment() {
        autocompleteFragment = AutocompleteSupportFragment.newInstance()

        // Set Autocomplete configuration here
        val token = AutocompleteSessionToken.newInstance()
        autocompleteFragment.setSessionToken(token)
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set an activity result listener to receive the selected place
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Handle the selected place
            }

            override fun onError(status: Status) {
                // Handle errors
            }
        })
    }
}*/
