package com.example.templateapplication.unit

import com.example.templateapplication.data.ApiRepository
import com.example.templateapplication.data.GoogleMapsRepository
import com.example.templateapplication.model.common.quotation.Formula
import com.example.templateapplication.ui.screens.quotationRequest.QuotationRequestViewModel
import com.example.templateapplication.utility.GoogleMapsApiMock
import com.example.templateapplication.utility.RestApiMock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class QuotationRequestViewModelTests {

    val restApi: ApiRepository = RestApiMock()

    val mapsApi: GoogleMapsRepository = GoogleMapsApiMock()

    private var viewModel: QuotationRequestViewModel = QuotationRequestViewModel(restApi, mapsApi)

    @Test
    fun selectFormula_selectsFormula() {
        var currentState = viewModel.quotationRequestState.value
        val oldFormula = currentState.formula
        val newFormula = Formula(
            99,
            "New",
            listOf(),
            20.0,
            listOf(10.0, 20.0, 30.0),
            true,
            ""
        )

        viewModel.selectFormula(newFormula)
        currentState = viewModel.quotationRequestState.value
        val currentFormula = currentState.formula

        assertEquals(currentFormula, newFormula)
        assertNotEquals(currentFormula, oldFormula)
    }

    @Test
    fun calculatePriceBeer_normalBeer() {
        val formula = Formula(99, "New", listOf(), 20.0, listOf(10.0, 20.0, 30.0), true, "")
        val amountOfPeople = 10
        viewModel.selectFormula(formula)
        viewModel.setAmountOfPeople(amountOfPeople.toString())
        viewModel.selectBeer(0)

        val priceBeer: Double = 1.5 * amountOfPeople

        assertEquals(priceBeer, viewModel.calculatePriceBeer(), 1.0)
    }

    @Test
    fun calculatePriceBeer_tripelBeer() {
        val formula = Formula(99, "New", listOf(), 20.0, listOf(10.0, 20.0, 30.0), true, "")
        val amountOfPeople = 10
        viewModel.selectFormula(formula)
        viewModel.setAmountOfPeople(amountOfPeople.toString())
        viewModel.selectBeer(1)

        val priceBeer: Double = 3.0 * amountOfPeople

        assertEquals(priceBeer, viewModel.calculatePriceBeer(), 1.0)
    }
}