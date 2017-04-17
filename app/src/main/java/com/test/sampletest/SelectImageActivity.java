package com.test.sampletest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SelectImageActivity extends AppCompatActivity implements View.OnClickListener
{

    ImageView mImageView;
    final static int SELECT_FILE = 100;
    final static int REQUEST_CAMERA = 200;
    String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        mImageView = (ImageView)findViewById(R.id.imgSelected);
    }

    @Override
    public void onClick(View v)
    {
        selectImage();
    }

    private void selectImage()
    {
        final CharSequence[] items =
                { "Take Photo", "Choose from Library", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(SelectImageActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                boolean result=Utility.checkPermission(SelectImageActivity.this);

                if (items[item].equals("Take Photo"))
                {
                    userChoosenTask="Take Photo";
                    if(result)
                    {
                        cameraIntent();
                    }
                }
                else if (items[item].equals("Choose from Library"))
                {
                    userChoosenTask="Choose from Library";
                    if(result)
                    {
                        galleryIntent();
                    }
                }
                else if (items[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(userChoosenTask.equals("Take Photo"))
                    {
                        cameraIntent();
                    }
                    else if(userChoosenTask.equals("Choose from Library"))
                    {
                        galleryIntent();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You deny the permission",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == SELECT_FILE)
            {
                onSelectFromGalleryResult(data);
            }
            else if (requestCode == REQUEST_CAMERA)
            {
                onCaptureImageResult(data);
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null)
        {
            try
            {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        mImageView.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data)
    {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg"); //.jpg or .png

        FileOutputStream fo;
        try
        {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        mImageView.setImageBitmap(thumbnail);
    }


}
