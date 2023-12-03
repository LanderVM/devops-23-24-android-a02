package com.example.templateapplication.ui.commons

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R
import com.example.templateapplication.model.adres.ApiResponse
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlaceCandidates
import com.example.templateapplication.model.common.googleMaps.GoogleMapsResponse
import com.example.templateapplication.network.googleMapsApi.GooglePrediction
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
fun AddressTextField(
    modifier: Modifier = Modifier,
    getPredictionsFunction: () -> Unit,
    hasFoundPlace: () -> Boolean,
    updateInputFunction: (String) -> Unit,
    placeResponse: GoogleMapsPlaceCandidates,
    showMap: Boolean, // TODO separate map from textfield composable
    updateMarkerFunction: () -> Unit = {},
    apiStatus: ApiResponse<GoogleMapsResponse>,
    googleMaps: GoogleMapsResponse,
) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(googleMaps.marker, 15f)
    }

    LaunchedEffect(key1 = googleMaps.eventAddress) {
        withContext(Dispatchers.IO) {
            delay(1000)
            getPredictionsFunction()
            Log.i("test", "piung")
        }

        withContext(Dispatchers.Main) {
            delay(1000)
            if (placeResponse.candidates.isNotEmpty()) {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(
                        placeResponse.candidates[0].geometry.location.lat,
                        placeResponse.candidates[0].geometry.location.lng
                    ), 12f
                )
            } else {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    googleMaps.marker, 10f
                )
            }
        }
    }

    if (showMap) {
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
                    state = MarkerState(position = googleMaps.marker),
                    title = stringResource(id = R.string.autoComplete_storageLocation_title),
                    snippet = stringResource(id = R.string.autoComplete_storageLocation_snippet)
                )
                if (hasFoundPlace()) {
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                placeResponse.candidates[0].geometry.location.lat,
                                placeResponse.candidates[0].geometry.location.lng
                            )
                        ),
                        title = stringResource(id = R.string.autoComplete_eventLocation_title),
                        snippet = stringResource(id = R.string.autoComplete_eventLocation_snippet)
                    )
                    Polyline(
                        points = listOf(
                            googleMaps.marker,
                            LatLng(
                                placeResponse.candidates[0].geometry.location.lat,
                                placeResponse.candidates[0].geometry.location.lng
                            )
                        )
                    )
                }
                Circle(center = googleMaps.marker, radius = 20000.0)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }

    OutlinedTextField(
        label = {
            Text(
                text = "Adres",
                color = Color(0xFFe9dcc5)
            )
        },
        value = googleMaps.eventAddress,
        onValueChange = {
            updateInputFunction(it)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFe9dcc5),
            unfocusedBorderColor = Color(0xFFe9dcc5),
        ),
        modifier = Modifier.width(300.dp),
    )

    when (apiStatus) {
        is ApiResponse.Loading -> {}
        is ApiResponse.Error -> Text(text = "Error") // TODO proper error && as supporting in textfield, not as Text()
        is ApiResponse.Success -> {
            AutoCompleteListComponent(
                predictionsState = apiStatus.data.predictionsResponse.predictions
            ) { prediction ->
                updateInputFunction(prediction.description)
                updateMarkerFunction()
            }
        }
    }
}

@Composable
fun AutoCompleteListComponent(
    predictionsState: List<GooglePrediction>,
    onPredictionClick: (GooglePrediction) -> Unit
) {
    predictionsState.forEach {
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
            .background(color = Color(0XFFC8A86E))
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
