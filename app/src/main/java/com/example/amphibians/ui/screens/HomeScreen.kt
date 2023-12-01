package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.model.AmphibianInfo

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    onRetryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (homeUiState) {
        is HomeUiState.Success ->
            SuccessScreen(
                amphibians = homeUiState.amphibiansCard,
                modifier = modifier
            )

        is HomeUiState.Loading ->
           LoadingScreen(modifier = modifier)

        is HomeUiState.Error ->
            ErrorScreen(onRetryAction = onRetryAction, modifier = modifier)
    }
}

@Composable
fun SuccessScreen(
    amphibians: List<AmphibianInfo>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(amphibians) { amphibian ->
            AmphibianCard(
                amphibian = amphibian
            )
        }
    }

}

@Composable
fun AmphibianCard(
    amphibian: AmphibianInfo,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
        ) {
            Text(
                text = "${amphibian.name} (${amphibian.type})",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .fillMaxWidth(),
            )
            AmphibiansImage(
                imgSrc = amphibian.imageSource,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            Text(
                text = amphibian.description,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            )
        }

    }
}

@Composable
fun AmphibiansImage(
    imgSrc: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(imgSrc)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
        error = painterResource(id = R.drawable.ic_broken_image),
        placeholder = painterResource(id = R.drawable.loading_img)
    )
}


@Composable
fun ErrorScreen(
    onRetryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = "Some troubles, please try again 😅",
            modifier = modifier.padding(bottom = 16.dp)
        )
        Button(onClick = onRetryAction) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Filled.Refresh, contentDescription = "Loading",
            modifier = modifier.size(100.dp))
    }
}

@Preview
@Composable
fun AmphibianCardPreview() {
    val amphibian = AmphibianInfo(
        name = "Great Basin Spadefoot",
        type = "Toad",
        description = "This toad is typically found in South America. Specifically on Mount Roraima at the boarders of Venezuala, Brazil, and Guyana, hence the name. The Roraiam Bush Toad is typically black with yellow spots or marbling along the throat and belly.",
        imageSource = "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/roraima-bush-toad.png",
    )
    AmphibianCard(amphibian = amphibian)
}