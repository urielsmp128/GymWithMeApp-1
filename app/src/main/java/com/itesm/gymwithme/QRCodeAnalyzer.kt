package com.itesm.gymwithme

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata

//
class QRCodeAnalyzer(
        private val onQrCodesDetected: (qrCodes: List<FirebaseVisionBarcode>) -> Unit
): ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy?, rotationDegrees: Int) {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
                .build()

        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)

        val mediaImage = image?.image
        val imageRotation = degreesToFirebaseRotation(rotationDegrees)
        if (mediaImage != null) {
            val firebaseVisionImage = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
            detector.detectInImage(firebaseVisionImage)
                    .addOnSuccessListener { barcodes ->
                        onQrCodesDetected(barcodes)
                    }
                    .addOnFailureListener {
                        Log.e("QRCodeActivity", "error", it)
                    }

        }
    }

    private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 90, 180, 270 or 0")
    }

}