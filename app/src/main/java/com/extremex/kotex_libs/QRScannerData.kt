package com.extremex.kotex_libs

import android.content.Context
import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import java.lang.Exception
import java.nio.ByteBuffer

// unpacking and processing the QRCode Image to get the desired stored valve in string format
class QRScannerData(private val onQrCodeScanned: (String) -> Unit): ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        if (image.format == ImageFormat.RGB_565) {
            val bytes = image.planes.first().buffer.toByteArrey()
            val source = PlanarYUVLuminanceSource(bytes, image.width, image.height, 0, 0, image.width, image.height, false)
            val  binBmp = BinaryBitmap(HybridBinarizer(source))
            try {
                val result = MultiFormatReader().apply { setHints(mapOf(DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE))) }.decode(binBmp)
                onQrCodeScanned(result.text)
            } catch (e: Exception){ e.printStackTrace() } finally { image.close() }
        }
    }
    // custom buffer To get an arrey list instead of getting single buffer
    private fun ByteBuffer.toByteArrey(): ByteArray {
        rewind()
        return ByteArray(remaining()).also { get(it) }
    }
}