package com.example.templateapplication.unit

import com.example.templateapplication.data.ApiRepository
import com.example.templateapplication.data.GoogleMapsRepository
import com.example.templateapplication.ui.screens.priceEstimation.PriceEstimationViewModel
import org.junit.Test

class PriceEstimationTest {


    val restApi: ApiRepository = RestApiMock()

    val mapsApi: GoogleMapsRepository = GoogleMapsApiMock()

    private var viewModel: PriceEstimationViewModel = PriceEstimationViewModel(restApi, mapsApi)

    @Test
    fun testYourFunction() {
        println(viewModel.estimationDetailsState.value.dbData.toString())
        println(viewModel.estimationDetailsState.value.dbData.toString())
    }


}