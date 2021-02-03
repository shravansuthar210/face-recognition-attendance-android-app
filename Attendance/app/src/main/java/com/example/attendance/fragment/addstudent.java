package com.example.attendance.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.attendance.R;
import com.example.attendance.activity.login;
import com.example.attendance.beans.addstudentdatabase;
import com.example.attendance.beans.profiledatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class addstudent extends Fragment {
    private EditText studentfullname, studentrollnumber,studentnumber,studentemail,studentdivision,studentbirtofdate,studentaddresss;
    private TextView faceidstudent;
    private ImageButton studentimage;
    private Spinner studentcourse,studentyear;
    private DatabaseReference notice;
    private StorageReference mStorageRef;
    private StorageReference mStorageRef1;
    private ProgressDialog detectionProgressDialog;
    private final FaceServiceClient faceServiceClient = new FaceServiceRestClient(
            "https://suthar.cognitiveservices.azure.com/face/v1.0", "key");
    private String faceidstring=null;
    private Uri imagefile;
    private static final int CAMERA_PIC_REQUEST = 1;
    private FirebaseAuth mAuth1;
    private DatabaseReference notice1;
    private ProgressBar pbadd;
    private Button studentsubmit;
    DatePickerDialog picker;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout= inflater.inflate(R.layout.addstudent,container,false);

        studentfullname=layout.findViewById(R.id.studentfullname);
        studentrollnumber=layout.findViewById(R.id.studentrollnumber);
        studentnumber=layout.findViewById(R.id.studentnumber);
        studentemail=layout.findViewById(R.id.studentemail);
        studentdivision=layout.findViewById(R.id.studentdivision);;
        studentbirtofdate=layout.findViewById(R.id.studentbirtofdate);
        studentaddresss=layout.findViewById(R.id.studentaddresss);
        faceidstudent=layout.findViewById(R.id.faceidstudent);

        studentsubmit = layout.findViewById(R.id.studentsubmit);
        studentimage = layout.findViewById(R.id.studentimage);
        pbadd=layout.findViewById(R.id.progressaddstudent);
        pbadd.setVisibility(View.GONE);

        studentcourse=layout.findViewById(R.id.studentcourse);
        studentyear=layout.findViewById(R.id.studentyear);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mStorageRef1= FirebaseStorage.getInstance().getReference();
        notice = FirebaseDatabase.getInstance().getReference("Student");
        notice1 = FirebaseDatabase.getInstance().getReference("Profile");

        Objects.requireNonNull(getActivity()).setTitle("Add Student");

        studentbirtofdate.setInputType(InputType.TYPE_NULL);
        studentbirtofdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                studentbirtofdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        List<String> categories = new ArrayList<String>();
        categories.add("BSCIT");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        studentcourse.setAdapter(dataAdapter);

        List<String> categories1 = new ArrayList<String>();
        categories1.add("FY");
        categories1.add("SY");
        categories1.add("TY");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(R.layout.spinner_drop_item);
        studentyear.setAdapter(dataAdapter1);

        studentsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbadd.setVisibility(View.VISIBLE);
                setfasle();
                final String emai = studentemail.getText().toString().trim();
                final String fullnames = studentfullname.getText().toString().trim();
                final String rollnumbers = studentrollnumber.getText().toString().trim();
                final String birthofdates=studentbirtofdate.getText().toString().trim();
                final String numbers=studentnumber.getText().toString().trim();
                final String divisions=studentdivision.getText().toString().trim();
                final String addresss=studentaddresss.getText().toString().trim();
                final String coureses =studentcourse.getSelectedItem().toString();
                final String years = studentyear.getSelectedItem().toString();
                if(faceidstring==null){
                    Log.d("student","faceid string null");
                    Toast.makeText(getContext(),"face id empty",Toast.LENGTH_LONG).show();
                    faceidstudent.setError("PLease enter face image");
                    faceidstudent.requestFocus();
                    settrue();
                    pbadd.setVisibility(View.GONE);
                } else if(imagefile==null){
                    Log.d("student","file null");
                    Toast.makeText(getContext(),"image not found",Toast.LENGTH_LONG).show();
                    faceidstudent.setError("PLease enter face image");
                    faceidstudent.requestFocus();
                    settrue();
                    pbadd.setVisibility(View.GONE);
                } else if(emai.isEmpty()){
                    studentemail.setError("Please enter email id");
                    studentemail.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                } else if(fullnames.isEmpty()){
                    studentfullname.setError("Please enter FullName id");
                    studentfullname.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                } else if(rollnumbers.isEmpty()){
                    studentrollnumber.setError("Please enter roll number");
                    studentrollnumber.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                } else if(birthofdates.isEmpty()){
                    studentbirtofdate.setError("Please enter birth date");
                    studentbirtofdate.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                } else if(numbers.isEmpty()){
                    studentnumber.setError("Please enter number");
                    studentnumber.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                } else if(divisions.isEmpty()){
                    studentdivision.setError("Please enter division");
                    studentdivision.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                } else if(addresss.isEmpty()){
                    studentaddresss.setError("Please enter address");
                    studentaddresss.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                } else if(emai.length()<10){
                    studentemail.setError("Please enter proper email id");
                    studentemail.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                } else if(fullnames.length()<9){
                    studentfullname.setError("Please enter FullName");
                    studentfullname.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                } else if(numbers.length()<10){
                    studentnumber.setError("Please enter valid number");
                    studentnumber.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                }else if(addresss.length()<7){
                    studentaddresss.setError("Please enter proper addrresss");
                    studentaddresss.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                }else if(!isValid(emai)){
                    studentemail.setError("Email not valid");
                    studentemail.requestFocus();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                } else  if(emai.isEmpty() && fullnames.isEmpty()&&rollnumbers.isEmpty()&&birthofdates.isEmpty()&&numbers.isEmpty()&&divisions.isEmpty()&&addresss.isEmpty()){
                    Toast.makeText(getContext(),"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                    pbadd.setVisibility(View.GONE);
                    settrue();
                }else {
                    Log.d("file","file datat uri"+imagefile);

                    mAuth1 = FirebaseAuth.getInstance();
                    mAuth1.createUserWithEmailAndPassword(emai, numbers).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    String uid = user.getUid();
                                    StorageReference riversRef = mStorageRef.child("Student/"+coureses+"/"+years+"/"+divisions+"/"+rollnumbers+"/"+rollnumbers+".jpg");
                                    riversRef.putFile(imagefile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(getContext(),"Face added",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Log.d("image", "Error--" +exception);
                                            Toast.makeText(getContext(),"Error--"+exception,Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    StorageReference riversRef1 = mStorageRef1.child("Profile/"+uid+".jpg");
                                    riversRef1.putFile(imagefile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Log.d("setpassword","Face Added");
                                            Toast.makeText(getContext(),"Face Added",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Log.d("addteacher","Error--"+exception);
                                            Toast.makeText(getContext(),"Error--"+exception,Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    profiledatabase profiledatabase=new profiledatabase(fullnames, emai,birthofdates, numbers, uid, addresss, numbers,"Student",rollnumbers,coureses,years,null,null,divisions);
                                    notice1.child(uid).setValue(profiledatabase);
                                    addstudentdatabase addstudentdatabase=new addstudentdatabase(fullnames,rollnumbers,addresss,emai,birthofdates,divisions,coureses+years,numbers,uid);
                                    notice.child(coureses+"/"+years+"/"+divisions+"/"+rollnumbers).setValue(addstudentdatabase);
                                    studentfullname.setText("");
                                    studentaddresss.setText("");
                                    studentbirtofdate.setText("");
                                    studentemail.setText("");
                                    studentrollnumber.setText("");
                                    studentnumber.setText("");
                                    studentdivision.setText("");
                                    Toast.makeText(getContext(),"Successfull add",Toast.LENGTH_LONG).show();
                                    pbadd.setVisibility(View.GONE);
                                    settrue();
                                    mAuth1.signOut();
                                    Intent intent=new Intent(getContext(), login.class);
                                    intent.putExtra("action","autologin");
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                                    pbadd.setVisibility(View.GONE);
                                    settrue();
                                }
                            } else {
                                Toast.makeText(getContext(), "Unsuccessfull", Toast.LENGTH_LONG).show();
                                pbadd.setVisibility(View.GONE);
                                settrue();
                            }
                        }
                    });
                }
            }
        });
        studentimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });
        detectionProgressDialog = new ProgressDialog(getContext());
        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && data != null) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            String savedImageURL = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                    image, "Bird", "Image of bird"
            );
            Uri savedImageURI = Uri.parse(savedImageURL);
            Log.d("addstudent","uri"+savedImageURI);
            imagefile=savedImageURI;
            studentimage.setImageBitmap(image);
            detectAndFrame(image);
        }
    }
    private void detectAndFrame(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        @SuppressLint("StaticFieldLeak") AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";
                    @SuppressLint("DefaultLocale")
                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            Log.d("ss","Detection start"+params[0].available());
                            publishProgress("Detecting...");
                            Face[] result = faceServiceClient.detect(params[0],
                                    true,         // returnFaceId
                                    false,        // returnFaceLandmarks
                                    null
                            );
                            Log.d("ss","Detection f-I- "+result[0].faceId.toString());
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
                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onPostExecute(Face[] result) {
                        if(result==null){
                            faceidstudent.setText("Please enter valid face");
                        }else if(result.length>1){
                            faceidstudent.setText("please enter one face");
                        }else {
                            faceidstring=result[0].faceId.toString();
                            faceidstudent.setText(faceidstring);
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
                        studentimage.setImageBitmap(drawFaceRectanglesOnBitmap(imageBitmap, result));
                        imageBitmap.recycle();
                    }
                };
        detectTask.execute(inputStream);
    }
    private void showError(String message) {
        new AlertDialog.Builder(getContext())
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }}).create().show();
    }
    private static Bitmap drawFaceRectanglesOnBitmap(
            Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        if (faces != null) {
            for (Face face : faces) {
                FaceRectangle faceRectangle = face.faceRectangle;
                canvas.drawRect(faceRectangle.left, faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height, paint);
            }
        }
        return bitmap;
    }
    private void setfasle(){
        studentemail.setEnabled(false);
        studentfullname.setEnabled(false);
        studentrollnumber.setEnabled(false);
        studentbirtofdate.setEnabled(false);
        studentnumber.setEnabled(false);
        studentdivision.setEnabled(false);
        studentaddresss.setEnabled(false);
        studentcourse.setEnabled(false);
        studentyear.setEnabled(false);
        studentsubmit.setEnabled(false);
    }
    private void settrue(){
        studentemail.setEnabled(true);
        studentfullname.setEnabled(true);
        studentrollnumber.setEnabled(true);
        studentbirtofdate.setEnabled(true);
        studentnumber.setEnabled(true);
        studentdivision.setEnabled(true);
        studentaddresss.setEnabled(true);
        studentcourse.setEnabled(true);
        studentyear.setEnabled(true);
        studentsubmit.setEnabled(true);
    }
    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
