package com.example.pruebatecnicatest.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun PdfViewerWithZoom(assetName: String, modifier: Modifier) {
    val context = LocalContext.current
    val pdfBitmaps = remember { mutableStateListOf<Bitmap>() }
    var scaleFactor by remember { mutableFloatStateOf(1f) }

    // Load PDF into bitmaps
    LaunchedEffect(assetName) {
        try {
            val file = copyPdfFromAssets(context, assetName)
            loadPdfFromFile(file, pdfBitmaps)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Zoom Controls
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            IconButton(onClick = { scaleFactor *= 1.1f }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Zoom In")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Zoom: ${(scaleFactor * 100).toInt()}%", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = { scaleFactor = (scaleFactor / 1.1f).coerceAtLeast(1f) }) {
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Zoom Out")
            }
        }

        // Display PDF Pages
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(pdfBitmaps.size) { index ->
                PdfPageView(
                    bitmap = pdfBitmaps[index],
                    scaleFactor = scaleFactor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}
@Composable
fun PdfPageView(bitmap: Bitmap, scaleFactor: Float, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val canvasWidth = bitmap.width * scaleFactor
        val canvasHeight = bitmap.height * scaleFactor

        // Scale and draw the image
        scale(scaleFactor, scaleFactor, pivot = Offset.Zero) {
            drawImage(
                image = bitmap.asImageBitmap(),
                topLeft = Offset.Zero
            )
        }
    }
}

@Throws(IOException::class)
fun copyPdfFromAssets(context: Context, assetName: String): File {
    val file = File(context.filesDir, assetName)
    if (!file.exists()) {
        context.assets.open(assetName).use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
    return file
}

fun loadPdfFromFile(file: File, bitmaps: MutableList<Bitmap>) {
    bitmaps.clear()
    val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    PdfRenderer(parcelFileDescriptor).use { renderer ->
        for (pageIndex in 0 until renderer.pageCount) {
            val page = renderer.openPage(pageIndex)
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            bitmaps.add(bitmap)
            page.close()
        }
    }
}
