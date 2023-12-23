package com.example.templateapplication.ui.screens.aboutPage

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.templateapplication.api.RestApiApplication
import com.example.templateapplication.data.ApiRepository
import com.example.templateapplication.model.about.AboutUiState
import com.example.templateapplication.network.restApi.about.ApiEmailPost
import com.example.templateapplication.network.restApi.about.PostEmailApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
 * ViewModel for managing the UI state of the About screen.
 *
 * Responsible for handling the functionality related to the About screen, such as posting emails
 * and updating dialog states. This ViewModel uses [ApiRepository] to interact with API services.
 *
 * @property restApiRepository Instance of [ApiRepository] used for making API calls.
 */
class AboutViewModel(
    private val restApiRepository: ApiRepository,
) : ViewModel() {

    private val _aboutUiState = MutableStateFlow(AboutUiState())
    val aboutUiState = _aboutUiState.asStateFlow()

    /**
     * State variable to track the status of the email posting API call.
     */
    private var postEmailApiState: PostEmailApiState by mutableStateOf(
        PostEmailApiState.Loading
    )

    /**
     * Posts an email using the API repository and updates [postEmailApiState] based on the outcome.
     */
    fun postEmail() {
        viewModelScope.launch {
            postEmailApiState = try {
                Log.i("AboutViewModel postEmail", "Sending request to api..")
                restApiRepository.postEmail(ApiEmailPost(aboutUiState.value.emailAddress))
                PostEmailApiState.Success
            } catch (e: HttpException) {
                val errorMessage = e.message ?: "Post request failed"
                Log.e(
                    "RestApi sendQuotationRequest",
                    errorMessage
                )
                PostEmailApiState.Error(errorMessage)
            }
        }
    }

    /**
     * Updates the state of the first dialog.
     *
     * @param value Boolean indicating the desired open/closed state of the dialog.
     */
    fun setOpenDialog1(value: Boolean) = _aboutUiState.update {
        it.copy(openDialog1 = value)
    }

    /**
     * Updates the state of the second dialog.
     *
     * @param value Boolean indicating the desired open/closed state of the dialog.
     */
    fun setOpenDialog2(value: Boolean) = _aboutUiState.update {
        it.copy(openDialog2 = value)
    }

    /**
     * Sets the email address in the UI state.
     *
     * @param value String representing the email address to set.
     */
    fun setEmail(value: String) = _aboutUiState.update {
        it.copy(emailAddress = value)
    }

    /**
     * Factory for creating instances of [AboutViewModel].
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestApiApplication)
                val guidePriceEstimationRepository =
                    application.container.apiRepository
                AboutViewModel(
                    restApiRepository = guidePriceEstimationRepository,
                )
            }
        }
    }
}