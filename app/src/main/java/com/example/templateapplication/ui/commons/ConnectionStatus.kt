package com.example.templateapplication.ui.commons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R

@Composable
fun ConnectionStatus(
    isOnline: Boolean,
    modifier: Modifier = Modifier,
) {
    if(!isOnline){
        Column(modifier = Modifier.fillMaxWidth()){
            Text(
                stringResource(id = R.string.internet_offline),
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
            )
        }
    }
}