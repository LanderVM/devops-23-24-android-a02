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
 * ViewModel for the About screen.
 *
 * @property restApiRepository An instance of ApiRepository used for making API calls.
 */
class AboutViewModel(
    private val restApiRepository: ApiRepository,
) : ViewModel() {

    // StateFlow for managing UI state in the About screen.
    private val _aboutUiState = MutableStateFlow(AboutUiState())
    val aboutUiState = _aboutUiState.asStateFlow()

    // Variable to track the current state of email posting API call.
    var postEmailApiState: PostEmailApiState by mutableStateOf(
        PostEmailApiState.Loading
    )
        private set

    /**
     * Function to post an email.
     * Uses the API repository to send an email and updates the postEmailApiState
     * based on the result of the operation.
     */
    fun postEmail() {
        viewModelScope.launch {
            try {
                Log.i("AboutViewModel postEmail", "Sending request to api..")
                restApiRepository.postEmail(ApiEmailPost(aboutUiState.value.emailAddress))
                postEmailApiState = PostEmailApiState.Success
            } catch (e: HttpException) {
                val errorMessage = e.message ?: "Post request failed"
                Log.e(
                    "RestApi sendQuotationRequest",
                    errorMessage
                )
                postEmailApiState = PostEmailApiState.Error(errorMessage)
            }
        }
    }

    /**
     * Updates the state of the first dialog.
     *
     * @param value Boolean indicating whether the dialog should be open or closed.
     */
    fun setOpenDialog1(value: Boolean) = _aboutUiState.update {
        it.copy(openDialog1 = value)
    }

    /**
     * Updates the state of the second dialog.
     *
     * @param value Boolean indicating whether the dialog should be open or closed.
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

    companion object {
        // Factory for creating instances of AboutViewModel.
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