package com.itesm.gymwithme

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import java.util.concurrent.Executors

class CameraXActivity : AppCompatActivity(), LifecycleOwner {

    private val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_x)

        viewFinder = findViewById(R.id.view_finder)

        // Request camera permissions
        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Every time the provided texture view changes, recompute layout
        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }

    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder: TextureView

    private fun startCamera() {

        // Camera Preview
        // Create configuration object for the viewfinder use case
        val previewConfig = PreviewConfig.Builder()
                .setLensFacing(CameraX.LensFacing.BACK)
                .build()

        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)
            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        // Image analysis
        val imageAnalysisConfig = ImageAnalysisConfig.Builder()
                .build()

        val imageAnalysis = ImageAnalysis(imageAnalysisConfig)

        val qrCodeAnalyzer = QRCodeAnalyzer { qrCodes ->
            if (qrCodes.isNotEmpty()) {
                CameraX.unbind(imageAnalysis)
                val title = qrCodes[0].url!!.title
                val url = qrCodes[0].url!!.url

                AlertDialog.Builder(this)
                        .setTitle(title)
                        .setMessage("$url")
                        .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener{ dialog, id ->
                            finish()
                        })
                        .create()
                        .show()
            }
        }

        imageAnalysis.setAnalyzer(executor, qrCodeAnalyzer)

        CameraX.bindToLifecycle(this as LifecycleOwner, preview, imageAnalysis)
    }

    private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 90, 180, 270 or 0")
    }

    private fun scanBarcodes(image: FirebaseVisionImage) {

        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                        FirebaseVisionBarcode.FORMAT_QR_CODE)
                .build()

        val detector = FirebaseVision.getInstance()
                .getVisionBarcodeDetector(options)

        val result = detector.detectInImage(image)
                .addOnSuccessListener { barcodes ->

                    for (barcode in barcodes) {
                        val bounds = barcode.boundingBox
                        val corners = barcode.cornerPoints

                        val rawValue = barcode.rawValue

                        val valueType = barcode.valueType
                        // See API reference for complete list of supported types
                        when (valueType) {

                            FirebaseVisionBarcode.TYPE_URL -> {
                                val title = barcode.url!!.title
                                val url = barcode.url!!.url

                                AlertDialog.Builder(this)
                                        .setTitle(title)
                                        .setMessage("$url")
                                        .setPositiveButton(android.R.string.ok, null)
                                        .create()
                                        .show()

                            }
                        }
                    }

                }

    }

    private fun updateTransform() {
        val matrix = Matrix()
        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f
        // Correct preview output to account for display rotation
        val rotationDegrees = when(viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}