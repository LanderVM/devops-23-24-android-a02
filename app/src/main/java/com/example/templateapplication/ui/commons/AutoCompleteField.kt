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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R
import com.example.templateapplication.model.ApiResponse
import com.example.templateapplication.model.UiText
import com.example.templateapplication.model.common.googleMaps.GoogleMapsPlaceCandidates
import com.example.templateapplication.model.common.googleMaps.GoogleMapsResponse
import com.example.templateapplication.network.googleMapsApi.GooglePrediction
import com.example.templateapplication.ui.utils.ReplyNavigationType
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

/**
 * Composable function that displays an address text field with Google Maps integration.
 *
 * This function shows a map view and an address text field. It provides autocomplete suggestions
 * for addresses and displays markers on the map based on the selected address.
 *
 * @param navigationType The type of navigation being used in the UI.
 * @param getPredictionsFunction Function to retrieve address predictions for autocomplete.
 * @param hasFoundPlace Function that checks if a place has been found.
 * @param onValueChange Callback for when the text field value changes.
 * @param placeResponse Response object containing place candidates.
 * @param showMap Boolean indicating whether the map should be shown.
 * @param updateMarkerFunction Function to update the marker on the map.
 * @param apiStatus The status of the API response (Loading, Error, Success).
 * @param googleMaps Google Maps response data.
 * @param errorMessage Optional error message for display.
 * @param isError Boolean indicating if there is an error.
 */
@Composable
fun AddressTextField(
    modifier: Modifier = Modifier,
    navigationType: ReplyNavigationType,
    getPredictionsFunction: () -> Unit,
    hasFoundPlace: () -> Boolean,
    onValueChange: (String) -> Unit,
    placeResponse: GoogleMapsPlaceCandidates,
    showMap: Boolean,
    updateMarkerFunction: () -> Unit = {},
    apiStatus: ApiResponse<GoogleMapsResponse>,
    googleMaps: GoogleMapsResponse,
    errorMessage: UiText? = null,
    isError: Boolean = false
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(googleMaps.marker, 15f)
    }

    LaunchedEffect(key1 = googleMaps.eventAddress) {
        withContext(Dispatchers.IO) {
            delay(1000)
            getPredictionsFunction()
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

    val mapWidth: Dp
    val mapHeight: Dp
    when (navigationType) {
        ReplyNavigationType.NAVIGATION_RAIL -> {
            mapHeight = 400.dp
            mapWidth = 500.dp
        }

        ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            mapHeight = 500.dp
            mapWidth = 700.dp
        }

        else -> {
            mapHeight = 250.dp
            mapWidth = 300.dp
        }
    }

    if (showMap) {
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .height(mapHeight)
                .width(mapWidth)
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

    ValidationTextFieldApp(
        placeholder = stringResource(id = R.string.address_placeholder),
        text = googleMaps.eventAddress,
        onValueChange = {
            onValueChange(it)
        },
        errorMessage = errorMessage,
        isError = isError,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    )

    when (apiStatus) {
        is ApiResponse.Loading -> {}
        is ApiResponse.Error -> Text(text = stringResource(id = R.string.error))
        is ApiResponse.Success -> {
            AutoCompleteListComponent(
                mapWidth = mapWidth,
                predictionsState = apiStatus.data.predictionsResponse.predictions
            ) { prediction ->
                onValueChange(prediction.description)
                updateMarkerFunction()
            }
        }
    }
}

/**
 * Composable function for displaying a list of autocomplete suggestions.
 *
 * Renders a list of predictions provided by the Google Maps API, allowing the user
 * to select one. Each item is rendered using the AutocompleteCardItem Composable.
 *
 * @param mapWidth The width of the map display area.
 * @param predictionsState List of GooglePrediction objects for rendering the autocomplete items.
 * @param onPredictionClick Callback for when a prediction is clicked.
 */
@Composable
fun AutoCompleteListComponent(
    mapWidth: Dp,
    predictionsState: List<GooglePrediction>,
    onPredictionClick: (GooglePrediction) -> Unit
) {
    predictionsState.forEach {
        AutocompleteCardItem(mapWidth = mapWidth, onPredictionClick, it)
    }
}

/**
 * Composable function for displaying an individual autocomplete item.
 *
 * Represents a single prediction in the autocomplete list. When clicked,
 * it triggers the provided callback with the selected prediction.
 *
 * @param mapWidth The width of the map display area.
 * @param onPredictionClick Callback for when this item is clicked.
 * @param prediction The GooglePrediction object to display.
 */
@Composable
fun AutocompleteCardItem(
    mapWidth: Dp,
    onPredictionClick: (GooglePrediction) -> Unit,
    prediction: GooglePrediction
) {
    Box(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxHeight()
            .width(mapWidth)
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
            mapWidth = 300.dp,
            onPredictionClick = {},
            prediction = GooglePrediction("Aalst, België", listOf())
        )
    }
}
