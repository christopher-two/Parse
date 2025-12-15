package org.christophertwo.parse.feature.home.presentation.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.christophertwo.parse.feature.home.presentation.HomeAction
import org.christophertwo.parse.feature.home.presentation.HomeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookSheet(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (state.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onAction(HomeAction.DismissBottomSheet) },
            sheetState = sheetState,
        ) {
            Crossfade(
                targetState = state.bookDownloaded,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                animationSpec = tween(500),
                label = "",
                content = { book ->
                    if (book) {
                        AddBookLoadedState(state, onAction)
                    } else {
                        AddBookInitialState(state, onAction)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun AddBookInitialState(state: HomeState, onAction: (HomeAction) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        uri?.let { onAction(HomeAction.BookSelected(it)) }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            CustomTextField(
                value = state.bookUri,
                onValueChange = { bookUri -> onAction(HomeAction.BookUri(bookUri)) },
                label = "Book URL",
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = MaterialTheme.shapes.extraExtraLarge,
                color = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onAction(HomeAction.DownloadBook) }) {
                        Icon(
                            imageVector = Icons.Filled.Download,
                            contentDescription = "Add book",
                        )
                    }
                    IconButton(onClick = { launcher.launch("application/pdf") }) {
                        Icon(
                            imageVector = Icons.Filled.UploadFile,
                            contentDescription = "Upload from device"
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun AddBookLoadedState(state: HomeState, onAction: (HomeAction) -> Unit) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                onAction(HomeAction.UpdateBookImage(uri))
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Box(
                modifier = Modifier.clickable {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            ) {
                CoilAsyncImage(
                    imageUrl = state.imageBook,
                    contentDescription = "Book cover",
                    contentScale = ContentScale.Crop
                )
            }

            CustomTextField(
                value = state.titleBook,
                onValueChange = { onAction(HomeAction.UpdateBookTitle(it)) },
                label = "Title"
            )

            Button(
                onClick = { onAction(HomeAction.SaveBook) },
                enabled = !state.isSaving,
                modifier = Modifier.size(48.dp),
                shape = MaterialShapes.Circle.toShape(),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                if (state.isSaving) {
                    CircularWavyProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = "Save book",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    )
}
