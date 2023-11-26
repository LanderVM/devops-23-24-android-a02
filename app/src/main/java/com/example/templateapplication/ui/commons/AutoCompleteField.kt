package com.example.templateapplication.ui.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.model.adres.ApiResponse
import com.example.templateapplication.model.adres.EventAddressViewModel
import com.example.templateapplication.model.adres.GoogleMapsPredictionsState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteComponent(
    modifier: Modifier = Modifier,
    eventAddressViewModel: EventAddressViewModel = viewModel(factory = EventAddressViewModel.Factory),
    showMap: Boolean,
) {
    val googleMapsPredictionState by eventAddressViewModel.uiStatePrediction.collectAsState()
    val googleMapsPredictionApiState = eventAddressViewModel.googleMapsPredictionApiState

    val googleMapsPlaceState by eventAddressViewModel.uiStatePlace.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(googleMapsPlaceState.marker, 15f)
    }

    LaunchedEffect(key1 = googleMapsPredictionState.input) {
        withContext(Dispatchers.IO) {
            delay(1000)
            eventAddressViewModel.getPredictions()
        }

        withContext(Dispatchers.Main) {
            delay(1000)
            if (googleMapsPlaceState.placeResponse.candidates.isNotEmpty()) {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(
                        googleMapsPlaceState.placeResponse.candidates[0].geometry.location.lat,
                        googleMapsPlaceState.placeResponse.candidates[0].geometry.location.lng
                    ), 12f
                )
            } else {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    googleMapsPlaceState.marker, 10f
                )
            }
        }
    }

    if (showMap) {
        Text(text = eventAddressViewModel.getDistanceString())
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
                if (eventAddressViewModel.checkForPlace()) {
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                googleMapsPlaceState.placeResponse.candidates[0].geometry.location.lat,
                                googleMapsPlaceState.placeResponse.candidates[0].geometry.location.lng
                            )
                        ),
                        title = "Event Locatie",
                        snippet = "Uw gekozen locatie"
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
    }

    OutlinedTextField(
        label = { Text(text = "Adres", color = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter)))) },
        value = googleMapsPredictionState.input,
        onValueChange = {
            eventAddressViewModel.updateInput(it)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
            unfocusedBorderColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
        ),
        modifier = Modifier.width(300.dp),
    )

    when (googleMapsPredictionApiState) {
        is ApiResponse.Loading -> {}
        is ApiResponse.Error -> Text(text = "Error")
        is ApiResponse.Success -> {
            AutoCompleteListComponent(
                predictionsState = googleMapsPredictionState,
                onPredictionClick = { prediction ->
                    eventAddressViewModel.updateInput(prediction.description)
                    eventAddressViewModel.updateMarker()
                }
            )
        }
    }
}

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
fun AutocompleteCardItem(
    onPredictionClick: (GooglePrediction) -> Unit,
    prediction: GooglePrediction
) {
    Box(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxHeight()
            .width(300.dp)
            .background(color = Color(android.graphics.Color.parseColor("#C8A86E")))
            .clickable { onPredictionClick(prediction) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(5.dp),
            text = prediction.description,
            color = Color.White
        )
    }
}


@Preview
@Composable
fun PreviewCard() {
    Box(
        modifier = Modifier.height(50.dp)
    ) {
        AutocompleteCardItem(
            onPredictionClick = {},
            prediction = GooglePrediction("Aalst, België", listOf())
        )
    }
}
