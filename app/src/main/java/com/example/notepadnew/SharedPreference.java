package com.example.notepadnew;

import android.content.Context;
import android.content.SharedPreferences;

//import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;
public class SharedPreference {
    Context context;
    SharedPreference(Context context) {
        this.context = context;
    }
    public void saveLoginDetails(String uid,String email, String password)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Uid",uid);
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.commit();
    }
    public  void getUid(String uid){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Uid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Uid",uid);
        editor.commit();

    }
    public boolean isUserLoged() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
//        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
//        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        boolean key=false;
        if((sharedPreferences.getString("Email",""))!="")
        {
            key=true;
        }
        else
        {
            key=false;

        }
        return key;
    }
    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }
    public String getPassword() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Password", "");
    }
    //logout call
    public  void clearall( )  {
        SharedPreferences preferences =context.getSharedPreferences("LoginDetails", 0);
        preferences.edit().clear().commit();
    }
}
