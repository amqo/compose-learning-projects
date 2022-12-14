package com.amqo.workmanagercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.work.*
import coil.compose.rememberImagePainter
import com.amqo.workmanagercompose.ui.theme.WorkManagerComposeTheme
import com.amqo.workmanagercompose.worker.ColorFilterWorker
import com.amqo.workmanagercompose.worker.DownloadWorker
import com.amqo.workmanagercompose.worker.WorkerKeys

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(
                        NetworkType.CONNECTED
                    ).build()
            ).build()
        val colorFilterRequest = OneTimeWorkRequestBuilder<ColorFilterWorker>().build()
        val workManager = WorkManager.getInstance(applicationContext)

        setContent {
            WorkManagerComposeTheme {
                val workInfos = workManager
                    .getWorkInfosForUniqueWorkLiveData("download")
                    .observeAsState()
                    .value
                val downloadInfo = remember(key1 = workInfos) {
                    workInfos?.find { it.id == downloadRequest.id }
                }
                val filterInfo = remember(key1 = workInfos) {
                    workInfos?.find { it.id == colorFilterRequest.id }
                }
                val imageUri by derivedStateOf {
                    val downloadUri = downloadInfo?.outputData?.getString(WorkerKeys.IMAGE_URI)?.toUri()
                    val filterUri = filterInfo?.outputData?.getString(WorkerKeys.FILTER_URI)?.toUri()
                    filterUri ?: downloadUri
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    imageUri?.let {  uri ->
                        Image(
                            painter = rememberImagePainter(data = uri),
                            contentDescription = "Downloaded Image",
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    if (downloadInfo?.state != WorkInfo.State.RUNNING &&
                        filterInfo?.state != WorkInfo.State.RUNNING) {
                        Button(onClick = {
                            workManager
                                .beginUniqueWork(
                                    "download",
                                    ExistingWorkPolicy.KEEP,
                                    downloadRequest
                                )
                                .then(colorFilterRequest)
                                .enqueue()
                        }) {
                            Text(text = if (filterInfo?.state == WorkInfo.State.SUCCEEDED) "Repeat download" else "Start download")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    when(downloadInfo?.state) {
                        WorkInfo.State.RUNNING -> Text("Downloading...")
                        WorkInfo.State.SUCCEEDED -> Text("Download succeeded")
                        WorkInfo.State.CANCELLED -> Text("Download cancelled")
                        WorkInfo.State.ENQUEUED -> Text("Download enqueued")
                        WorkInfo.State.BLOCKED -> Text("Download blocked")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    when(filterInfo?.state) {
                        WorkInfo.State.RUNNING -> Text("Applying filter...")
                        WorkInfo.State.SUCCEEDED -> Text("Filter succeeded")
                        WorkInfo.State.CANCELLED -> Text("Filter cancelled")
                        WorkInfo.State.ENQUEUED -> Text("Filter enqueued")
                        WorkInfo.State.BLOCKED -> Text("Filter pending")
                    }
                }
            }
        }
    }
}