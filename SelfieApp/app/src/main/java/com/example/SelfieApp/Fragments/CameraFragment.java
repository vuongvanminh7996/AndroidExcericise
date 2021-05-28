package com.example.SelfieApp.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.SelfieApp.MainActivity;
import com.example.SelfieApp.R;
import com.example.SelfieApp.utils.DataBaseHandler;
import java.io.ByteArrayOutputStream;

public class CameraFragment extends Fragment {
    private final static String DEBUG_TAG = "MakePhotoActivity";
    private Camera camera;
    private int cameraId = 0;

    private boolean safeToTakePicture = false;

    private static final int CAMERA_REQUEST = 1888;
    TextView text,text1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    //Bitmap photo;
    String photo;
    DataBaseHandler databaseHandler;
    private SQLiteDatabase db;
    Bitmap theImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.camera_fragment,container,false);


       // imageView =view. findViewById(R.id.imageView1);
        text = view.findViewById(R.id.text);
        text1 = view.findViewById(R.id.viewImage);
        databaseHandler = new DataBaseHandler(getContext());

//        cameraId = findFrontFacingCamera();
//        if (cameraId < 0) {
////                        Toast.makeText(this, "No front facing camera found.",
////                                Toast.LENGTH_LONG).show();
//        }
//        else {
//            try{
//            releaseCameraAndPreview();
//            camera = Camera.open(cameraId);
////                SurfaceTexture st = new SurfaceTexture(10);
////                camera.setPreviewTexture(st);
//            camera.startPreview();
//            safeToTakePicture = true;
//        } catch (Exception e) {
//            Log.e(getString(R.string.app_name), e.toString());
//            e.printStackTrace();
//            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
//        }
//        }

        text.setOnClickListener(
                new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    try {
                        cameraId = findFrontFacingCamera();
                        if (cameraId < 0) {
//                        Toast.makeText(this, "No front facing camera found.",
//                                Toast.LENGTH_LONG).show();
                        }
                        releaseCameraAndPreview();
                        camera = Camera.open(cameraId);
                        SurfaceTexture st = new SurfaceTexture(10);
                        camera.setPreviewTexture(st);
                        camera.startPreview();
                        safeToTakePicture = true;
                        if(safeToTakePicture)
                        {
                            camera.takePicture(null, null, mPictureCallback);
                            safeToTakePicture = false;
                        }
                    } catch (Exception e) {
                        Log.e(getString(R.string.app_name), e.toString());
                        e.printStackTrace();
                        Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }


//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    cameraIntent.putExtra("android.intent.extra.quickCapture",true);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

                }
            }
        });

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(new LocalFragment(), true);
            }
        });
       return view;
    }

    private void releaseCameraAndPreview() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    private void setDataToDataBase() {
        db = databaseHandler.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(databaseHandler.KEY_IMG_URL,getEncodedString(theImage));

        long id = db.insert(databaseHandler.TABLE_NAME, null, cv);
        if (id < 0) {
            Toast.makeText(getContext(), "Something went wrong. Please try again later...", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Add successful", Toast.LENGTH_LONG).show();
            safeToTakePicture = true;
        }
    }

    /**
     * Reuqesting for premissons
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                // do we have a camera?
                try {
                    cameraId = findFrontFacingCamera();
                    if (cameraId < 0) {
//                        Toast.makeText(this, "No front facing camera found.",
//                                Toast.LENGTH_LONG).show();
                    }
                    releaseCameraAndPreview();
                    camera = Camera.open(cameraId);
                SurfaceTexture st = new SurfaceTexture(10);
                camera.setPreviewTexture(st);
                    camera.startPreview();
                    safeToTakePicture = true;
                    if(safeToTakePicture)
                    {
                        camera.takePicture(null, null, mPictureCallback);
                        safeToTakePicture = false;
                    }
                } catch (Exception e) {
                    Log.e(getString(R.string.app_name), e.toString());
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Start an activity for result
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            theImage = (Bitmap) data.getExtras().get("data");
          photo=getEncodedString(theImage);
                setDataToDataBase();
        }
    }



    private String getEncodedString(Bitmap bitmap){

        ByteArrayOutputStream os = new ByteArrayOutputStream();

               bitmap.compress(Bitmap.CompressFormat.JPEG,100, os);

       /* or use below if you want 32 bit images

        bitmap.compress(Bitmap.CompressFormat.PNG, (0–100 compression), os);*/
        byte[] imageArr = os.toByteArray();

        return Base64.encodeToString(imageArr, Base64.URL_SAFE);

    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.e(DEBUG_TAG, "Camera found " + i);
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    public void onPause() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onPause();
    }

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if(data !=null)
            {
//                camera.stopPreview();

                theImage =BitmapFactory.decodeByteArray(data, 0, data.length);

                photo=getEncodedString(theImage);
                setDataToDataBase();
            }

        }
    };
}

//class MakePhotoActivity extends Activity {
//    private final static String DEBUG_TAG = "MakePhotoActivity";
//    private Camera camera;
//    private int cameraId = 0;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // do we have a camera?
//        if (!getPackageManager()
//                .hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
//            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
//                    .show();
//        } else {
//            cameraId = findFrontFacingCamera();
//            if (cameraId < 0) {
//                Toast.makeText(this, "No front facing camera found.",
//                        Toast.LENGTH_LONG).show();
//            } else {
//                camera = Camera.open(cameraId);
//            }
//        }
//    }
//
//    public void onClick(View view) {
//        camera.startPreview();
//        camera.takePicture(null, null, null);
//    }
//
//    private int findFrontFacingCamera() {
//        int cameraId = -1;
//        // Search for the front facing camera
//        int numberOfCameras = Camera.getNumberOfCameras();
//        for (int i = 0; i < numberOfCameras; i++) {
//            Camera.CameraInfo info = new Camera.CameraInfo();
//            Camera.getCameraInfo(i, info);
//            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                Log.d(DEBUG_TAG, "Camera found");
//                cameraId = i;
//                break;
//            }
//        }
//        return cameraId;
//    }
//
//    @Override
//    protected void onPause() {
//        if (camera != null) {
//            camera.release();
//            camera = null;
//        }
//        super.onPause();
//    }
//
//}


