package com.example.notepadnew;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

public class Note_add extends AppCompatActivity {
    public static Object settext;
    private FloatingActionButton upload;
    private Snackbar snr;
    public static EditText heading;
    public static EditText textdata;
    String data_text,data_head;
    DatabaseReference mydb;
    FirebaseDatabase myfb;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        upload=(FloatingActionButton)findViewById(R.id.upload);
        heading=(EditText)findViewById(R.id.headingedit);
        textdata=(EditText)findViewById(R.id.textedit);
        progressDialog=new ProgressDialog(Note_add.this);
        myfb = FirebaseDatabase.getInstance();
        mydb = myfb.getReference("Notes");
            String key=getIntent().getStringExtra("key");


        //Toast.makeText(getApplicationContext(),key+" klflkjflkjlkf",Toast.LENGTH_LONG).show();
        //obtain  Intent Object send  from SenderActivity
        Intent intent = this.getIntent();
        //Toast.makeText(getApplicationContext(),key+" if block",Toast.LENGTH_LONG).show();
        /* Obtain String from Intent  */
        if(intent !=null) {
            String strdata = intent.getExtras().getString("key");
            Toast.makeText(getApplicationContext(),strdata+"",Toast.LENGTH_LONG).show();
            if(strdata.equals("true"))
            {
                String date_s=getIntent().getStringExtra("date");
                String heading_s=getIntent().getStringExtra("heading");
                String content_s=getIntent().getStringExtra("content");
                heading.setText(heading_s);
                textdata.setText(content_s);
                //Toast.makeText(getApplicationContext(),key+" if block",Toast.LENGTH_LONG).show();

            }
            if(key.equals("false"))
            {
                heading.setText("");
                textdata.setText("");
            }
        }
        else
        {
            heading.setText("");
            textdata.setText("");
        }
//        String date_s=getIntent().getStringExtra("date");
//        String heading_s=getIntent().getStringExtra("heading");
//        String content_s=getIntent().getStringExtra("content");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    if(heading.getText().toString().isEmpty())
                    {
                        snack_barcall("heading can't be emphty","yellow",800,v);
                        heading.setError("add heading please");
                        heading.requestFocus();

                    }
                    else if(textdata.getText().toString().isEmpty())
                    {
                        snack_barcall("content can't be emphty","yellow",800,v);
                        textdata.setError("add content please");
                        textdata.requestFocus();

                    }
                    else
                    {
                        data_text=textdata.getText().toString().trim();
                        data_head=heading.getText().toString().trim();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                        String currentDateandTime = sdf.format(new Date());
                        Notes note=new Notes(data_head,data_text,currentDateandTime);

                        progressDialog.setMessage("Please wait ...");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        //getting uid from shared preferences
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                        //SharedPreferences.Editor editor = sp.edit();
                        String Uid = sp.getString("Uid", "");

                        //adding valuee to this
                        final DatabaseReference userId = mydb.child(Uid).push();
                        userId.child("tittle").setValue(data_head);
                        userId.child("date").setValue(currentDateandTime);
                        userId.child("content").setValue(data_text)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        //lnr.setVisibility(View.GONE);
                                        progressDialog.dismiss();
                                        snack_barcall("note added successfully...","green",800,v);
                                        textdata.setText("");
                                        heading.setText("");

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        snack_barcall("please try agin....","orenge",800,v);
                                        upload.requestFocus();

                                    }
                                });


                    }
                }
                else {
                    // Not Available...
                    snack_barcall("check network connection","red",800,v);

                }


            }
        });

    }
    public static EditText settexthead()
    {
        return heading;
    }
    public static EditText settextcontent()
    {
        return textdata;
    }

    public  void toast(String msg)
    {
        Toast.makeText(getApplicationContext(),msg+"",Toast.LENGTH_SHORT).show();
    }
    public void snack_barcall(String msg,String color,int time,View vi)
    {
        int col;
        switch (color)
        {
            case "red":
                col=R.color.snackbarred;
                break;
            case "yellow":
                col=R.color.snackbaryellow;
                break;
            default:
                col=R.color.snackbargreen;
        }
        snr=Snackbar.make(vi,msg,Snackbar.LENGTH_INDEFINITE);
        snr.setDuration(time);
        snr.setAction("ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snr.dismiss();
            }
        });
        View vn=snr.getView();
        vn.setBackgroundColor(getResources().getColor(col));
        snr.show();

    }
}
