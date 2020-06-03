package com.itesm.gymwithme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class CameraXActivity extends AppCompatActivity  {

    private ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    private PreviewView previewView;
    private Camera camera;

    private static final int REQUEST_CODE_PERMISSIONS = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x);

        // Init preview
        previewView = findViewById(R.id.preview_view);

        // Request Camera Permissions
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSIONS);
        }


    }

    private void startCamera() {
        cameraProviderListenableFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderListenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    // Get camera provider to bind the lifecycle of cameras to the lifecycle owner
                    ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                    // Go to bind preview function
                    bindPreview(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this future
                    // This should never be reached
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        // Preview
        Preview preview = new Preview.Builder().build();
        // Select back camera
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        try {
            // Unbind use cases before rebinding
            cameraProvider.unbindAll();
            // Bind use cases to camera
            camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview);
            preview.setSurfaceProvider(previewView.createSurfaceProvider());

        } catch (Exception e) {
            Log.e("use case binding failed", e.getMessage());
        }


    }

}
