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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity implements View.OnClickListener
{
           EditText email,password;
           Button login,register;
           DatabaseReference mydb;
           FirebaseDatabase myfb;
           private FirebaseAuth mAuth;
           private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        if((new SharedPreference(this).isUserLoged()==false))
        {
            SharedPreferences sharedPreferences = LoginPage.this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            String isEmailEmpty = sharedPreferences.getString("Email", "");
            Toast.makeText(getApplicationContext()," "+isEmailEmpty,Toast.LENGTH_SHORT).show();
            email=(EditText)findViewById(R.id.emaillog);
            password=(EditText)findViewById(R.id.passlog);
            login=(Button)findViewById(R.id.loginlog);
            register=(Button)findViewById(R.id.registerlog);
            login.setOnClickListener(this);
            register.setOnClickListener(this);
            progressDialog=new ProgressDialog(LoginPage.this);

        }
        else
        {
            Toast.makeText(getApplicationContext()," "+new SharedPreference(this).isUserLoged(),Toast.LENGTH_SHORT).show();
            Intent in=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(in);
        }
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.loginlog:
                         login();
                break;
            case R.id.registerlog:
                Intent  in=new Intent(getApplicationContext(),RegisterPage.class);
                startActivity(in);
                break;
                default:
                    return;

        }
    }
   public  void login()
   {
       progressDialog.setMessage("Please wait ...");
       progressDialog.show();
       progressDialog.setCancelable(false);
       final String str_email=email.getText().toString()+"@gmail.com";
       final String str_password=password.getText().toString();
           mAuth.signInWithEmailAndPassword(str_email,str_password)
                   .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>()
                   {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful())
                           {
                               myfb = FirebaseDatabase.getInstance();
                               mydb = myfb.getReference("users");

                               FirebaseUser currentUser = mAuth.getCurrentUser();

                               final DatabaseReference userId=mydb.child(currentUser.getUid());
                               final String uid=userId.getKey();
                               mydb.child(uid).addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                       User value= dataSnapshot.getValue(User.class);

                                       saveLoginDetails(uid,str_email, str_password);
                                   }

                                   @Override
                                   public void onCancelled(DatabaseError databaseError) {

                                   }
                               });
                               progressDialog.dismiss();
                               Intent i=new Intent(getApplicationContext(),MainActivity.class);
                               startActivity(i);
                               Toast.makeText(getApplicationContext(),"logeed in ",Toast.LENGTH_SHORT).show();
                           }
                           else
                           {
                               progressDialog.dismiss();
                               Toast.makeText(getApplicationContext(),"incorrect credentials",Toast.LENGTH_SHORT).show();
                           }

                       }
                   });


   }
    private void saveLoginDetails(String uid,String email, String pass)
    {
        new SharedPreference(this).saveLoginDetails(uid,email, pass);
    }

}
