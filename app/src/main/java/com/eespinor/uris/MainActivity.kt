package com.eespinor.uris

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eespinor.uris.ui.theme.UrisTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = Uri.parse("android.resource://$packageName/drawable/quanta")
        val quantaBytes = contentResolver.openInputStream(uri)?.use{
            it.readBytes()
        }
        println("Quanta size: ${quantaBytes?.size}")

        val file = File(filesDir, "Quanta.jpg" )
        FileOutputStream(file).use {
            it.write(quantaBytes)
        }

        println("Quanta ${file.toURI()}")

        val dataUri = Uri.parse("data:text/plain;charset=8,Hello%20%World ")

        setContent {
            UrisTheme {
                val pickImage = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(), 
                    onResult = {contentUri -> 
                        println(contentUri)
                    })
                Button(onClick = { pickImage.launch("image/*") }) {
                    Text(text = "Pick image")
                }
            }
        }
    }
}
