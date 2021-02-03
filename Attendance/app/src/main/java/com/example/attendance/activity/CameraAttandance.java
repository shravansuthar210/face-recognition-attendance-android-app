package com.example.attendance.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.beans.Attandance;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;
import com.microsoft.projectoxford.face.contract.VerifyResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CameraAttandance extends AppCompatActivity {
    private static final int CAMERA_PIC_REQUEST = 1;
    private EditText rollnumberview;
    private ImageButton classpic;
    private final FaceServiceClient faceServiceClient = new FaceServiceRestClient(
            "https://suthar.cognitiveservices.azure.com/face/v1.0", "key");
    ProgressDialog detectionProgressDialog;
    private List faceidcamera=new ArrayList();
    final List<Integer> rollNoList = new ArrayList<>();
    private UUID id1;
    private StorageReference storageRef;
    private DatabaseReference notice;
    String course,year,division,subject,starttime,endtime;
    private DatabaseReference notice2;
    private DatabaseReference notice3;
    private int w=1;
    private int localfacec=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_attandance);
        rollnumberview=findViewById(R.id.RollNumberAttand);
        classpic=findViewById(R.id.detectfaceview);
        Button attandancebutton = findViewById(R.id.cameraAttandanceSubmit);
        storageRef = FirebaseStorage.getInstance().getReference();
        detectionProgressDialog = new ProgressDialog(this);
        setTitle("Camera Attandance");
        ActionBar actionBar= getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0052c5"));
        actionBar.setBackgroundDrawable(colorDrawable);

        final Intent intent=getIntent();
        course =intent.getStringExtra("course");
        year=intent.getStringExtra("year");
        division=intent.getStringExtra("division");
        subject=intent.getStringExtra("subject");
        starttime=intent.getStringExtra("starttime");
        endtime=intent.getStringExtra("endtime");
        Log.d("camera","value"+course+year+division+subject+starttime+endtime);

        classpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("Camera","Error---"+e);
                }

            }
        });
        Date dNow = new Date( );
        @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddHH:mm");
        final String date=ft.format(dNow);

        notice2=FirebaseDatabase.getInstance().getReference("Attandance");
        attandancebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int roll=0;roll<rollNoList.size();roll++){
                    Attandance attandance=new Attandance(starttime,endtime);
                    String pathroll=Integer.toString(rollNoList.get(roll));
                    notice2.child(course+"/"+year+"/"+division+"/"+pathroll+"/"+subject+"/"+date).setValue(attandance);
                    Log.d("tag","submit");
                }
                Date AdNow = new Date( );
                @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddHH:mm");
                final String Adate=ft.format(AdNow);

                notice3 = FirebaseDatabase.getInstance().getReference("Attandancedetail");
                notice3.child(course+"/"+year+"/"+division+"/"+subject+"/"+Adate).setValue("date");
                rollnumberview.setText("");
                Toast.makeText(CameraAttandance.this,"Attandance successful",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && data != null) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            classpic.setImageBitmap(image);
            detectAndFrame(image);
        }else {
            Toast.makeText(CameraAttandance.this,"Enter a Image",Toast.LENGTH_LONG).show();
        }
    }
    private void detectAndFrame(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());
        @SuppressLint("StaticFieldLeak") AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";

                    @SuppressLint("DefaultLocale")
                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            Log.d("ss","Detection start"+params[0].available());
                            publishProgress("Detecting...");
                            Face[] result = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    true,        // returnFaceLandmarks
                                    null
                            );
                            if (result == null){
                                publishProgress("Detection Finished. Nothing detected");
                                Log.d("ss","Detection Finished. Nothing detected");
                                return null;
                            }
                            publishProgress(String.format("Detection Finished. %d face(s) detected", result.length));
                            Log.d("ss","Detection Finished"+result.length);

                            return result;

                        } catch (Exception e) {
                            exceptionMessage = String.format("Detection failed: %s", e.getMessage());
                            Log.d("ss","Detection failed"+e+"messge"+e.getMessage());
                            return null;
                        }
                    }
                    @Override
                    protected void onPreExecute() {
                        //TODO: show progress dialog
                        detectionProgressDialog.show();
                    }
                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //TODO: update progress
                        detectionProgressDialog.setMessage(progress[0]);
                    }
                    @Override
                    protected void onPostExecute(Face[] result) {
                        if(result!=null){
                            for (int i=0;i<result.length;i++){
                                faceidcamera.add(result[i].faceId);
                                Log.d("camera","face local"+result[i].faceId);
                            }
                            nextpic(w);
                        }
                        //TODO: update face frames
                        detectionProgressDialog.dismiss();
                        if(!exceptionMessage.equals("")){
                            Log.d("ss","errrrrr"+exceptionMessage);
                            showError(exceptionMessage);
                        }
                        if (result == null) {
                            Log.d("ss","Null");
                            return;
                        }
                        classpic.setImageBitmap(drawFaceRectanglesOnBitmap(imageBitmap, result));
                        imageBitmap.recycle();
                    }
                };
        detectTask.execute(inputStream);
    }
    private void detectAndFrame2(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        @SuppressLint("StaticFieldLeak") AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";

                    @SuppressLint("DefaultLocale")
                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            Log.d("ss","Detection start"+params[0].available());
                            publishProgress("Detecting...");
                            Face[] result = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    true,        // returnFaceLandmarks
                                    null

                            );
                            if (result == null){
                                publishProgress("Detection Finished. Nothing detected");
                                Log.d("ss","Detection Finished. Nothing detected");
                                return null;
                            }
                            publishProgress(String.format("Detection Finished. %d face(s) detected", result.length));
                            Log.d("ss","Detection Finished"+result.length);
                            return result;
                        } catch (Exception e) {
                            exceptionMessage = String.format("Detection failed: %s", e.getMessage());
                            Log.d("ss","Detection failed"+e+"messge"+e.getMessage());
                            return null;
                        }
                    }
                    @Override
                    protected void onPreExecute() {
                        //TODO: show progress dialog
                        detectionProgressDialog.show();
                    }
                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //TODO: update progress
                        detectionProgressDialog.setMessage(progress[0]);
                    }
                    @Override
                    protected void onPostExecute(Face[] result) {
                        if(result!=null){
                            Log.d("camera","face 2_id"+result[0].faceId.toString());
                            id1=result[0].faceId;
                            Log.d("camera","faceidcamerlenght"+faceidcamera.size());
                            localfacec=0;
                            if(localfacec<faceidcamera.size()){
                                Log.d("camera","faceid came r"+faceidcamera.get(localfacec));
                                if(id1!=null){
                                    Log.d("camera","face id11__"+id1.toString());

                                    Log.d("hy","faceid------"+faceidcamera.get(localfacec)+"\t"+id1);
                                    newlocal(localfacec);
                                }
                            }
                        }
                        //TODO: update face frames
                        detectionProgressDialog.dismiss();

                        if(!exceptionMessage.equals("")){
                            Log.d("ss","errrrrr"+exceptionMessage);
                            showError(exceptionMessage);
                        }
                        if (result == null) {
                            Log.d("ss","Null");
                            return;
                        }
                        //classpic.setImageBitmap(drawFaceRectanglesOnBitmap(imageBitmap, result));
                        imageBitmap.recycle();
                    }
                };
        detectTask.execute(inputStream);
    }

    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create().show();
    }

    private static Bitmap drawFaceRectanglesOnBitmap(
            Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        if (faces != null) {
            for (Face face : faces) {
                FaceRectangle faceRectangle = face.faceRectangle;
                canvas.drawRect(
                        faceRectangle.left,
                        faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height,
                        paint);
            }
        }
        return bitmap;
    }
    @SuppressLint("StaticFieldLeak")
    private class VerificationTask extends AsyncTask<Void, String, VerifyResult> {
        // The IDs of two face to verify.
        private UUID mFaceId0;
        private UUID mFaceId1;

        VerificationTask (UUID faceId0, UUID faceId1) {
            mFaceId0 = faceId0;
            mFaceId1 = faceId1;
            Log.d("camera","fac"+mFaceId0+"\t"+mFaceId1);
        }

        @Override
        protected VerifyResult doInBackground(Void... params) {
            // Get an instance of face service client to detect faces in image.
            //FaceServiceClient faceServiceClient = SampleApp.getFaceServiceClient();
            try{
                publishProgress("Verifying...");
                return faceServiceClient.verify(
                        mFaceId0,      /* The first face ID to verify */
                        mFaceId1);     /* The second face ID to verify */
            }  catch (Exception e) {
                publishProgress(e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(CameraAttandance.this, "VERIFYING PLEASE WAIT A BIT MORE", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            Toast.makeText(CameraAttandance.this, "VERIFYING PLEASE WAIT A BIT MORE", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(VerifyResult result) {
            if (result != null) {
                if(result.isIdentical){
                    Toast.makeText(CameraAttandance.this, "Same"+result.confidence, Toast.LENGTH_SHORT).show();
                    Log.d("hy","Same"+result.confidence);
                    rollNoList.add(w);
                    faceidcamera.remove(faceidcamera.get(localfacec));
                    String ot=Integer.toString(w);
                    rollnumberview.append(ot+",");
                    Log.d("camera","Rollnumber"+ot);
                    Log.d("camera","size"+faceidcamera.size());
                    w=w+1;
                    nextpic(w);
                }
                else{
                    Toast.makeText(CameraAttandance.this, "Not Same", Toast.LENGTH_SHORT).show();
                    Log.d("camer","hy"+faceidcamera.size()+"\t"+localfacec);
                    if(faceidcamera.size()>localfacec+1){
                        localfacec++;
                        newlocal(localfacec);
                    }else {
                        Toast.makeText(CameraAttandance.this, "local finish", Toast.LENGTH_SHORT).show();
                        w=w+1;
                        nextpic(w);
                    }
                }
            }
        }
    }
    public void nextpic(final int roll){
        final String yearsubstring=year.substring(0,2);
        notice = FirebaseDatabase.getInstance().getReference("Student/"+course+"/"+yearsubstring+"/"+division);
        notice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    Long k=dataSnapshot.getChildrenCount();
                    if(roll<=k){
                        Log.d("camera","Student/"+course+"/"+yearsubstring+"/"+division+"/"+roll+"/"+roll+".jpg");
                        StorageReference riversRef = storageRef.child("Student/"+course+"/"+yearsubstring+"/"+division+"/"+roll+"/"+roll+".jpg");
                        try {
                            final File localFile = File.createTempFile("imagesattandance", "jpg");
                            riversRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmapdatabase = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    detectAndFrame2(bitmapdatabase);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Log.d("camera","Error---"+exception);
                                    Toast.makeText(CameraAttandance.this,"Error"+exception,Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            Log.d("camera","Error---"+e);
                            Toast.makeText(CameraAttandance.this,"Error"+e,Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(CameraAttandance.this,"finish",Toast.LENGTH_SHORT).show();
                        Log.d("camera","finish");
                    }
                }else {
                    Toast.makeText(CameraAttandance.this,"not child",Toast.LENGTH_SHORT).show();
                }
            }@Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CameraAttandance.this,"Error"+error,Toast.LENGTH_SHORT).show();
                Log.d("camera","Error--"+error);
            }
        });
    }
    public void newlocal(int nex){
        new VerificationTask((UUID) faceidcamera.get(nex),id1).execute();
    }
}
