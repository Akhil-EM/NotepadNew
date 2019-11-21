package com.example.notepadnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterPage extends AppCompatActivity {
         private EditText email,password,cpassword;
         Button register;
         String str_email,str_password;
    private FirebaseAuth mAuth;
    DatabaseReference mydb;
    FirebaseDatabase myfb;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        email=(EditText)findViewById(R.id.emailReg);
        password=(EditText)findViewById(R.id.passreg);
        cpassword=(EditText)findViewById(R.id.confpassreg);
        register=(Button)findViewById(R.id.register);
       progressDialog=new ProgressDialog(RegisterPage.this);


        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Please wait ...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                str_email=(email.getText().toString().trim())+"@gmail.com";
                str_password=password.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(str_email,str_password)
                        .addOnCompleteListener(RegisterPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // TODO: Figure out how to give back certain messages
                                // If the task fails
                                if (task.isSuccessful()) {
                                    myfb = FirebaseDatabase.getInstance();
                                    mydb = myfb.getReference("users");
                                    //user1 = new user();
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                                    //SharedPreferences.Editor editor = sp.edit();
                                    String Uid = sp.getString("Uid", "");
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    String currentDateandTime = sdf.format(new Date());
                                    //adding valuee to this
                                    final DatabaseReference userId = mydb.child(Uid).push();
                                    userId.child("tittle").setValue("your note shoes here");
                                    userId.child("date").setValue(currentDateandTime);
                                    userId.child("content").setValue("click here to delete notes");


                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "created user success fully", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                                    startActivity(intent);

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "email already exists", Toast.LENGTH_SHORT).show();

                                }



                               }
                           });



            }
        });

    }
}
