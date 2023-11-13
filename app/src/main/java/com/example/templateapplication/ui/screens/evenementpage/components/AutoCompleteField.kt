package com.example.templateapplication.ui.screens.evenementpage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay

@Composable
fun AutoCompleteComponent(
    modifier: Modifier = Modifier,
    googleMapsViewModel: GoogleMapsViewModel = viewModel(factory = GoogleMapsViewModel.Factory),
) {
    val googleMapsPredictionState by googleMapsViewModel.uiStatePrediction.collectAsState()
    val googleMapsApiState = googleMapsViewModel.googleMapsPredictionApiState

    var someInputText by rememberSaveable { mutableStateOf(googleMapsPredictionState.input) }

    val startPlaats = LatLng(50.93735122680664, 4.03336238861084)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPlaats, 15f)
    }

    LaunchedEffect(key1 = someInputText) {
        // if (someInputText.isBlank()) return@LaunchedEffect

        delay(1000)
        googleMapsViewModel.getPredictions()
    }

    TextField(
        value = googleMapsPredictionState.input,
        onValueChange = {
            someInputText = it
            googleMapsViewModel.updateInput(it)
        },
    )
    OutlinedTextField(
        value = googleMapsPredictionState.input,
        onValueChange = {
            someInputText = it
            googleMapsViewModel.updateInput(it)
        },
    )

    Box(modifier = modifier.height(400.dp)) {
        when (googleMapsApiState) {
            is ApiResponse.Loading -> Text("")
            is ApiResponse.Error -> Text("Error")
            is ApiResponse.Success -> {
                AutoCompleteListComponent(
                    predictionsState = googleMapsPredictionState,
                    onPredictionClick = { prediction -> googleMapsViewModel.updateInput(prediction.description) }
                )
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .padding(2.dp)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(position = googleMapsPredictionState.marker),
                title = "Blanche",
                snippet = "Onze opslagplaats"
            )
            Circle(center = startPlaats, radius = 20000.0)
        }
    }
}

@Composable
fun AutoCompleteListComponent(
    predictionsState: GoogleMapsPredictionsState,
    onPredictionClick: (GooglePrediction) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(5.dp),
        state = lazyListState
    ) {
        items(items = predictionsState.predictionsResponse.predictions) { prediction ->
            AutocompleteCardItem(onPredictionClick, prediction)
        }
        item { Spacer(modifier = Modifier.height(400.dp)) }
    }
}

@Composable
fun AutocompleteCardItem(onPredictionClick: (GooglePrediction) -> Unit, prediction: GooglePrediction) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .clickable { onPredictionClick(prediction) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
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
