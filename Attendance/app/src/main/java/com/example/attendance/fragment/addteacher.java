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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.attendance.R;
import com.example.attendance.setpassword;
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
import java.util.regex.Pattern;

public class addteacher extends Fragment {
    private ImageButton profilepic;
    private TextView faceidteacher;
    private EditText fullname,degree,birthofdate,number,email,address;
    private Spinner courece;
    private static final int CAMERA_PIC_REQUEST = 1;
    Uri imagefileteacher;
    private String faceidstring;
    private final FaceServiceClient faceServiceClient =
            new FaceServiceRestClient("https://suthar.cognitiveservices.azure.com/face/v1.0", "key");
    private ProgressDialog detectionProgressDialog;
    private Uri imagefile;
    DatePickerDialog picker;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.addteacher,container,false);
        profilepic=layout.findViewById(R.id.profile);
        getActivity().setTitle("Add Teacher");

        faceidteacher=layout.findViewById(R.id.faceidteacher);
        fullname=layout.findViewById(R.id.fullname);
        degree=layout.findViewById(R.id.degree);
        birthofdate=layout.findViewById(R.id.birtofdate);
        number=layout.findViewById(R.id.number);
        email=layout.findViewById(R.id.email);
        address=layout.findViewById(R.id.addresss);
        Button next = layout.findViewById(R.id.submit);
        courece = layout.findViewById(R.id.course);


        birthofdate.setInputType(InputType.TYPE_NULL);
        birthofdate.setOnClickListener(new View.OnClickListener() {
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
                                birthofdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        List<String> categories = new ArrayList<String>();
        categories.add("BSCIT");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        courece.setAdapter(dataAdapter);

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullnames = fullname.getText().toString();
                final String emai = email.getText().toString();
                final String degre = degree.getText().toString();
                final String birthofdates=birthofdate.getText().toString();
                final String numbers=number.getText().toString();
                final String addresss=address.getText().toString();
                final String coureses =courece.getSelectedItem().toString();
                if(emai.isEmpty()){
                    email.setError("Please enter email id");
                    email.requestFocus();
                } else if(fullnames.isEmpty()){
                    fullname.setError("Please enter FullName id");
                    fullname.requestFocus();
                } else if(degre.isEmpty()){
                    degree.setError("Please enter FullName id");
                    degree.requestFocus();
                } else if(birthofdates.isEmpty()){
                    birthofdate.setError("Please enter email id");
                    birthofdate.requestFocus();
                } else if(numbers.isEmpty()){
                    number.setError("Please enter email id");
                    number.requestFocus();
                } else if(addresss.isEmpty()){
                    address.setError("Please enter email id");
                    address.requestFocus();
                } else if(emai.length()<10){
                    email.setError("Please enter proper email id");
                    email.requestFocus();
                } else if(fullnames.length()<9){
                    fullname.setError("Please enter Full Name");
                    fullname.requestFocus();
                } else if(numbers.length()<10){
                    number.setError("number lenght will be 10");
                    number.requestFocus();
                } else if(addresss.length()<7){
                    address.setError("Please enter proper address");
                    address.requestFocus();
                } else if(faceidstring==null){
                    faceidteacher.setError("PLease enter face image");
                    faceidteacher.requestFocus();
                    Toast.makeText(getContext(),"PLease enter face",Toast.LENGTH_LONG).show();
                } else if(imagefileteacher==null){
                    faceidteacher.setError("PLease enter face image");
                    faceidteacher.requestFocus();
                    Toast.makeText(getContext(),"First enter face",Toast.LENGTH_LONG).show();
                } else if(!isValid(emai)){
                    email.setError("Email not valid");
                    email.requestFocus();
                    Toast.makeText(getContext(),"Email are not valid",Toast.LENGTH_LONG).show();
                } else  if(!(emai.isEmpty() && fullnames.isEmpty()&&birthofdates.isEmpty()&&numbers.isEmpty()&&addresss.isEmpty())){
                    Intent intent=new Intent(getContext(), setpassword.class);
                    intent.putExtra("fullname",fullnames);
                    intent.putExtra("email",emai);
                    intent.putExtra("degree",degre);
                    intent.putExtra("birthofdate",birthofdates);
                    intent.putExtra("number",numbers);
                    intent.putExtra("address",addresss);
                    intent.putExtra("cource",coureses);
                    intent.putExtra("uri",imagefileteacher.toString());
                    startActivity(intent);
                    fullname.setText("");
                    degree.setText("");
                    birthofdate.setText("");
                    number.setText("");
                    email.setText("");
                    address.setText("");
                } else {
                    Toast.makeText(getContext(),"Error Occurred!",Toast.LENGTH_SHORT).show();
                }
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
            imagefileteacher=savedImageURI;
            profilepic.setImageBitmap(image);
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
                            faceidteacher.setText("Please enter valid face");
                        }else if(result.length>1){
                            faceidteacher.setText("please enter one face");
                        }else {
                            faceidstring=result[0].faceId.toString();
                            faceidteacher.setText(faceidstring);
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
                        profilepic.setImageBitmap(drawFaceRectanglesOnBitmap(imageBitmap, result));
                        imageBitmap.recycle();
                    }
                };
        detectTask.execute(inputStream);
    }
    private void showError(String message) {
        new AlertDialog.Builder(getContext()).setTitle("Error").setMessage(message)
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
        paint.setStrokeWidth(3);
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
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
