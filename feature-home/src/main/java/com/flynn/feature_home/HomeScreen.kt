package com.flynn.feature_home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flynn.feature_home.components.shimmerEffect

@Composable
fun HomeScreen() {

    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect {
            when (it) {
                is HomeSideEffect.ShowToast -> {
                    Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.8f)
                ) {
                    ContentSection(uiState)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f)
                ) {
                    ActionButtons(viewModel)
                }

            }
        }
    }
}

@Composable
fun ContentSection(uiState: HomeState) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TopTextSection(
                title = "Text every 10th chars:",
                loading = uiState.loadingTopBox,
                content = uiState.convertedContent,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            WordCountSection(
                title = "Count unique words:",
                loading = uiState.loadingBottomBox,
                wordsCounted = uiState.contentWordsCounted,
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Composable
fun TopTextSection(
    title: String,
    loading: Boolean,
    content: String,
    contentColor: Color,
    backgroundColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title)
        if (loading) {
            LoadingBox(backgroundColor = backgroundColor)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(color = backgroundColor)
            ) {
                item {
                    Text(
                        text = content,
                        modifier = Modifier.padding(8.dp),
                        color = contentColor
                    )
                }
            }
        }
    }

}

@Composable
fun WordCountSection(
    title: String,
    loading: Boolean,
    wordsCounted: Map<String, Int>,
    backgroundColor: Color,
    contentColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title)
        if (loading) {
            LoadingBox(backgroundColor = backgroundColor)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(color = backgroundColor)
            ) {
                val totalWords = wordsCounted.size
                item {
                    Text(
                        text = "Total words: $totalWords \n",
                        modifier = Modifier.padding(8.dp),
                        color = contentColor
                    )
                }
                wordsCounted.forEach { (word, count) ->
                    item {
                        Text(
                            text = "$word: $count",
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = contentColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingBox(backgroundColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .shimmerEffect(shape = RoundedCornerShape(0.dp))
    )
}

@Composable
fun ActionButtons(viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                viewModel.onIntent(HomeIntent.ProcessContent)
                viewModel.onIntent(HomeIntent.CountWords)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Process Info",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Button(
            onClick = { viewModel.onIntent(HomeIntent.ClearDb) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Clear Data",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

