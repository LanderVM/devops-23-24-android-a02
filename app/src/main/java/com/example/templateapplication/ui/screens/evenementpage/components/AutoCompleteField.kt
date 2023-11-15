package com.example.templateapplication.ui.screens.evenementpage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.model.adres.GoogleMapsViewModel
import com.example.templateapplication.network.ApiResponse
import com.example.templateapplication.network.GoogleMapsPredictionsState
import com.example.templateapplication.network.GooglePrediction
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun AutoCompleteComponent(
    modifier: Modifier = Modifier,
    googleMapsViewModel: GoogleMapsViewModel = viewModel(factory = GoogleMapsViewModel.Factory),
) {
    val googleMapsPredictionState by googleMapsViewModel.uiStatePrediction.collectAsState()
    val googleMapsPredictionApiState = googleMapsViewModel.googleMapsPredictionApiState

    val googleMapsPlaceState by googleMapsViewModel.uiStatePlace.collectAsState()
    val googleMapsPlaceApiState = googleMapsViewModel.googleMapsPlaceApiState

    val googleMapsDistanceState by googleMapsViewModel.uiStateDistance.collectAsState()
    val googleMapsDistanceApiState = googleMapsViewModel.googleMapsDistanceApiState

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(googleMapsPlaceState.marker, 15f)
    }

    LaunchedEffect(key1 = googleMapsPredictionState.input) {
        // if (someInputText.isBlank()) return@LaunchedEffect
        withContext(Dispatchers.IO) {
            delay(1000)
            googleMapsViewModel.getPredictions()
            googleMapsViewModel.updateMarker()
            if (googleMapsPlaceState.placeResponse.candidates.isNotEmpty()) {
                googleMapsViewModel.updateDistance()
            }
        }

        withContext(Dispatchers.Main) {
            if (googleMapsPlaceState.placeResponse.candidates.isNotEmpty()) {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(googleMapsPlaceState.marker, 8f)
            }
        }
    }

    if (googleMapsDistanceState.distanceResponse.rows.isNotEmpty()) {
        Text(text = googleMapsDistanceState.distanceResponse.rows[0].elements[0].distance.value.toString())
    }

    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .height(300.dp)
            .width(300.dp)
            .padding(2.dp)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(position = googleMapsPlaceState.marker),
                title = "Blanche",
                snippet = "Onze opslagplaats"
            )
            if (googleMapsPlaceState.placeResponse.candidates.isNotEmpty()) {
                Marker(
                    state = MarkerState(position = LatLng(
                        googleMapsPlaceState.placeResponse.candidates[0].geometry.location.lat,
                        googleMapsPlaceState.placeResponse.candidates[0].geometry.location.lng
                    )),
                    title = "Event plaats",
                    snippet = "Uw gekozen plaats"
                )
                Polyline(
                    points = listOf(
                        googleMapsPlaceState.marker,
                        LatLng(
                            googleMapsPlaceState.placeResponse.candidates[0].geometry.location.lat,
                            googleMapsPlaceState.placeResponse.candidates[0].geometry.location.lng
                        )
                    )
                )
            }
            Circle(center = googleMapsPlaceState.marker, radius = 20000.0)
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
    
    OutlinedTextField(
        value = googleMapsPredictionState.input,
        onValueChange = {
            googleMapsViewModel.updateInput(it)
        },
        modifier = Modifier.width(300.dp)
    )

    when (googleMapsPredictionApiState) {
        is ApiResponse.Loading -> {}
        is ApiResponse.Error -> Text(text = "Error")
        is ApiResponse.Success -> {
            AutoCompleteListComponent(
                predictionsState = googleMapsPredictionState,
                onPredictionClick = { prediction -> googleMapsViewModel.updateInput(prediction.description) }
            )
        }
        else -> {}
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AutoCompleteListComponent(
    predictionsState: GoogleMapsPredictionsState,
    onPredictionClick: (GooglePrediction) -> Unit
) {
    predictionsState.predictionsResponse.predictions.forEach {
        AutocompleteCardItem(onPredictionClick, it)
    }
}

@Composable
fun AutocompleteCardItem(onPredictionClick: (GooglePrediction) -> Unit, prediction: GooglePrediction) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .width(300.dp)
            .background(Color.LightGray)
            .clickable { onPredictionClick(prediction) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(5.dp),
            text = prediction.description,
            color = Color.Black
        )
    }
}


@Preview
@Composable
fun PreviewCard() {
    Box(
        modifier = Modifier.height(50.dp)
    ) {
        AutocompleteCardItem(onPredictionClick = {}, prediction = GooglePrediction("Aalst, België", listOf()))
    }
}
