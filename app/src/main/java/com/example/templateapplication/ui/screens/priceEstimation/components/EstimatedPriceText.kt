package com.example.templateapplication.ui.screens.priceEstimation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R
import com.example.templateapplication.network.restApi.priceEstimation.PriceEstimationResultApiState


@Composable
fun EstimatedPriceText(
    calculatePriceApiState: PriceEstimationResultApiState
) {
    when (calculatePriceApiState) {
        is PriceEstimationResultApiState.Error -> {
            Text(
                text = calculatePriceApiState.errorMessage,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
            )
        }

        PriceEstimationResultApiState.Loading -> {
            Text(
                text = stringResource(id = R.string.guidePrice_calculating),
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
            )
        }

        is PriceEstimationResultApiState.Success -> {
            Text(
                text = stringResource(
                    id = R.string.guidePrice_estimatedPrice,
                    calculatePriceApiState.result
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
            )
        }
        PriceEstimationResultApiState.Idle -> {}
    }
}