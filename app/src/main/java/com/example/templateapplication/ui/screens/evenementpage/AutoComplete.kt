
import android.app.Activity
import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import io.reactivex.annotations.NonNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Arrays
import java.util.Objects

@Composable
fun MyComponent() {
    MyComponentC()
    // AutocompleteComposable()
}

@Composable
fun MyComponentA() {
    var selectedPlace by remember { mutableStateOf<Place?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    val placesClient = remember { Places.createClient(context) }

    var autocompleteFragment: AutocompleteSupportFragment? by remember { mutableStateOf(null) }

    DisposableEffect(Unit) {
        autocompleteFragment = AutocompleteSupportFragment()
            .apply {
                setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS))
                setOnPlaceSelectedListener(object : PlaceSelectionListener {
                    override fun onPlaceSelected(place: Place) {
                        selectedPlace = place

                        val photoRequest = FetchPhotoRequest.builder(Objects.requireNonNull(place.photoMetadatas)[0])
                            .build()

                        placesClient.fetchPhoto(photoRequest)
                            .addOnSuccessListener { response ->
                                bitmap = response.bitmap
                            }
                            .addOnFailureListener { exception ->
                                exception.printStackTrace()
                            }
                    }

                    override fun onError(status: Status) {
                        // Handle the error.
                    }
                })
            }

        onDispose {
            // Clean up resources if needed
            autocompleteFragment = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AndroidView(
            factory = { autocompleteFragment!!.requireView() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {}

        selectedPlace?.let { place ->
            Text(
                text = "Selected Place: ${place.name}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        bitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Place Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}


@Composable
fun MyComponentB() {
    val context = LocalContext.current

    // Create a container for the AutocompleteSupportFragment
    val autocompleteContainer = rememberSaveable { FrameLayout(context) }

    val autocompleteFragment = rememberSaveable {
        AutocompleteSupportFragment().apply {
            setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
            setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    // TODO: Get info about the selected place.
                    Log.i(ContentValues.TAG, "Place: ${place.name}, ${place.id}")
                }

                override fun onError(status: Status) {
                    // TODO: Handle the error.
                    Log.i(ContentValues.TAG, "An error occurred: $status")
                }
            })
        }
    }
    autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS));

    autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
        override fun onPlaceSelected(@NonNull place: Place) {
            // TODO: Get info about the selected place.
            Toast.makeText(context, place.name, Toast.LENGTH_SHORT).show()
        }

        override fun onError(@NonNull status: Status) {
            // TODO: Handle the error.
            Toast.makeText(context, status.toString(), Toast.LENGTH_SHORT).show()
        }
    })
    // autocompleteFragment.setOnPlaceSelectedListener()
    AndroidView(
        factory = { context ->
            // Check if the AutocompleteSupportFragment's view is not null before adding it
            autocompleteFragment.view?.let { fragmentView ->
                autocompleteContainer.removeAllViews()
                autocompleteContainer.addView(fragmentView)
            }

            autocompleteContainer
        },
        update = { view ->
            // No-op for the container
        },
        modifier = Modifier.fillMaxSize() // Adjust as needed
    )
}

@Composable
fun MyComponentC() {
    val context = LocalContext.current

    val intentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                it.data?.let {
                    val place = Autocomplete.getPlaceFromIntent(it)
                    Log.i("MAP_ACTIVITY", "Place: ${place.name}, ${place.id}")
                }
            }
            Activity.RESULT_CANCELED -> {
                Log.i("test", "ERROR")
                // The user canceled the operation.
            }
            else -> {
                Log.i("test", "ERROR")
            }
        }
    }

    var landen = listOf("BE")
    // Places.initialize(context, R.string.map_key.toString())

    // Create a new PlacesClient instance
    val placesClient = Places.createClient(context)
    val launchMapInputOverlay = {
        Log.i("test", Places.isInitialized().toString())
        val fields = listOf(Place.Field.ID, Place.Field.NAME)
        val intent = Autocomplete
            .IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .setCountries(landen)
            .build(context)
        intentLauncher.launch(intent)
    }

    var test = true
    var results = listOf<AutocompletePrediction>()

    LaunchedEffect(test) {
        if (getAutocompletePredictionsAsync(placesClient, "Opwijk").size > 1)
            Log.i("test", getAutocompletePredictionsAsync(placesClient, "Opwijk").get(0).toString())
    }

    Column {
        Button(onClick = launchMapInputOverlay) {
            Text("Select Location")
        }
        // Text(text = getAutocompletePredictionsAsync(placesClient, "Opwijk"))
    }

}

suspend fun getAutocompletePredictionsAsync(
    placesClient: PlacesClient,
    query: String
): List<AutocompletePrediction> = withContext(Dispatchers.IO) {
    try {
        val request = FindAutocompletePredictionsRequest.newInstance(query)
        val response = placesClient.findAutocompletePredictions(request)

        if (response.isSuccessful) {
            response.result.autocompletePredictions
        } else {
            emptyList()
        }
    } catch (e: Exception) {
        // Handle exceptions as needed
        emptyList()
    }
}


@Composable
fun AutocompleteComposable() {
    var selectedPlace by remember { mutableStateOf("") }

    // Autocomplete fragment initialization
    val autocompleteFragment = remember { AutocompleteSupportFragment() }

    // Set up the PlaceSelectionListener to listen for place selection events
    autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
        override fun onPlaceSelected(place: com.google.android.libraries.places.api.model.Place) {
            // Handle the selected place
            selectedPlace = place.name ?: ""
        }

        override fun onError(p0: Status) {
            // Handle the error
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display the selected place
        Text(text = "Selected Place: $selectedPlace", style = MaterialTheme.typography.titleMedium)

        // Embed the AutocompleteSupportFragment using AndroidView
        AndroidView(factory = { context ->
            autocompleteFragment.requireView().apply {
                // Make sure the fragment's view is attached to the Compose view tree
                if (parent == null) {
                    val layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setLayoutParams(layoutParams)
                }
            }
        }, update = { view ->
            // Update the view if needed
        })
    }
}