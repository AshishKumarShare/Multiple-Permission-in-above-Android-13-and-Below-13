package com.example.dialogboxandroid;

import static android.Manifest.permission_group.CAMERA;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import javax.security.auth.callback.PasswordCallback;

public class MainActivity extends AppCompatActivity {

    Button btnclick;
    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    private static final String LOCATION_PERMISSION =  Manifest.permission.ACCESS_FINE_LOCATION;
    private static  String READ_STORAGE_PERMISSION;

    private int REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnclick = findViewById(R.id.btnclick);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            READ_STORAGE_PERMISSION = Manifest.permission.READ_MEDIA_IMAGES;
        }else {
            READ_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;

        }

        btnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showCustomDialogBox();
                showPermissionDialog();

            }
        });

    }

    private void showPermissionDialog() {

        if (ContextCompat.checkSelfPermission(this,CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this,READ_STORAGE_PERMISSION)  == PackageManager.PERMISSION_GRANTED
        ){
            Toast.makeText(this, "Permission accepted", Toast.LENGTH_SHORT).show();
            showCustomDialogBox();

        }else {
            ActivityCompat.requestPermissions(this, new String[]{ CAMERA_PERMISSION , LOCATION_PERMISSION , READ_STORAGE_PERMISSION },REQUEST_CODE);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                ){
                    showCustomDialogBox();
                }
                else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }

        }else {
            showPermissionDialog();
        }

    }

    private void showCustomDialogBox() {

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog_box_layout);
        ImageView closeIcon = dialog.findViewById(R.id.closeIcon);

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();

    }
}