package com.example.fl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonFlash;
    static Camera camera = null;
    Camera.Parameters parameters;
    boolean isflash = false;
    boolean isOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonFlash = (Button)findViewById(R.id.buttonFlash);

        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))

        {
            camera = Camera.open();
                parameters = camera.getParameters();
                isflash = true;

        }

        buttonFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isflash)
                {
                    if(!isOn)
                    {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        isOn = true;

                    }

                    else
                    {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                        isOn = false;

                    }

                }

                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error...");
                    builder.setMessage(("Flash Lights is not available on your device"));
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }


            }

        });

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        if (isflash == true)
        {
            if (camera != null) {
                camera.release();
                camera = null;
            }
            if (camera == null) {
                camera = camera.open();

                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
                isOn = true;
            }
        }
    }

    @Override
    public void onBackPressed()

    {
        // TODO Auto-generated method stub
        super.onBackPressed();

        parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
        isOn = false;

        if (camera != null)
        {
            camera.release();
            camera = null;
        }
    }





}