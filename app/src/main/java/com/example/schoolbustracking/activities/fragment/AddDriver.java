package com.example.schoolbustracking.activities.fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolbustracking.R;
import com.example.schoolbustracking.activities.Model.DriverModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDriver extends Fragment {

    //Start Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
//End Firebase

    //Widget
    EditText edt_busno, edt_drivername, edt_driveCnt;
    Button btnSubmit;
    Context context;
    ProgressDialog progressDialog;
    //String
    String sbusno, sdrivername, sdriverCnt, spassword;
    int n;

    private final static int SEND_SMS_PERMISSION_REQUEST_CODE = 111;


    public AddDriver() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_driver, container, false);
        context = view.getContext();

        //init widget
        edt_busno = view.findViewById(R.id.busno);
        edt_drivername = view.findViewById(R.id.drivername);
        edt_driveCnt = view.findViewById(R.id.phnno);
        btnSubmit = view.findViewById(R.id.submit_btn);
        progressDialog = new ProgressDialog(context);

        //For sms
        if (checkPermission(Manifest.permission.SEND_SMS)) {

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }


        Random random = new Random();
        n = random.nextInt(999999);
        spassword = String.valueOf(n);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Driver");


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sbusno = edt_busno.getText().toString().trim();
                sdrivername = edt_drivername.getText().toString().trim();
                sdriverCnt = edt_driveCnt.getText().toString().trim();

                if (TextUtils.isEmpty(sbusno)) {
                    Toast.makeText(context, "Enter bus no", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(sdrivername)) {
                    Toast.makeText(context, "Enter driver name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(sdriverCnt)) {
                    Toast.makeText(context, "Enter driver Contact no", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child(sbusno).exists()) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Bus No already register", Toast.LENGTH_SHORT).show();

                            } else {
                                progressDialog.dismiss();
                                DriverModel driverModel = new DriverModel(sdrivername, spassword, sbusno, sdriverCnt);
                                myRef.child(sbusno).setValue(driverModel);
                                Toast.makeText(context, "Register successfully", Toast.LENGTH_SHORT).show();
                                sendSmsToDriver();
                                //Empty edit text
                                edt_busno.setText("");
                                edt_drivername.setText("");
                                edt_driveCnt.setText("");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        return view;
    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(context, permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void sendSmsToDriver() {

        if (checkPermission(Manifest.permission.SEND_SMS)) {
            String message = "This message is from School Bus Tracking. Your Bus no is " + sbusno + " and password is " + spassword;
            String numbers = sdriverCnt;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numbers, null, message, null, null);

        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
        }


//        try {
//            // Construct data
//            String apiKey = "apikey=" + "tvKMKzwIkXg-97fTiH5LL65hQJgaflvmWojsJ7B88R";
//            String message = "&message=" + "Your Bus no is" + sbusno + "and password is" + spassword;
//            String sender = "&sender=" + "TXTLCL";
//            String numbers = "&numbers=" + sdriverCnt;
//
//            // Send data
//            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
//            String data = apiKey + numbers + message + sender;
//            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
//            conn.getOutputStream().write(data.getBytes("UTF-8"));
//            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            final StringBuffer stringBuffer = new StringBuffer();
//            String line;
//            while ((line = rd.readLine()) != null) {
//                Toast.makeText(context, line.toString(), Toast.LENGTH_SHORT).show();
//            }
//            rd.close();
//        } catch (Exception e) {
//
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){

                }
                break;
        }
    }
}
