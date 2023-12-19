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

class AboutViewModel(
    private val restApiRepository: ApiRepository,
) :
    ViewModel() {

    private val _aboutUiState = MutableStateFlow(AboutUiState())
    val aboutUiState = _aboutUiState.asStateFlow()

    var postEmailApiState: PostEmailApiState by mutableStateOf(
        PostEmailApiState.Loading
    )
        private set

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

    fun setOpenDialog1(value: Boolean) = _aboutUiState.update {
        it.copy(openDialog1 = value)
    }

    fun setOpenDialog2(value: Boolean) = _aboutUiState.update {
        it.copy(openDialog2 = value)
    }

    fun setEmail(value: String) = _aboutUiState.update {
        it.copy(emailAddress = value)
    }

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