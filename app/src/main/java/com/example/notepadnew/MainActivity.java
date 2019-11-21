package com.example.notepadnew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toast backToast;
    private static long back_pressed;
    DatabaseReference mydbN,myref;
    FirebaseDatabase myfb;
    private RecyclerView recyclerView1;
    private NoteAdapter adapter;
    private List<Noteget> notifications;
    TextView tittle,date,content;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);

        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        recyclerView1 = (RecyclerView)findViewById(R.id.recycler);
        notifications = new ArrayList<>();
        adapter = new NoteAdapter(this, notifications);
        progressDialog=new ProgressDialog(MainActivity.this);

        RecyclerView.LayoutManager mLayouManager = new LinearLayoutManager(getApplicationContext());
        recyclerView1.setLayoutManager(mLayouManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(adapter);

        tittle=(TextView)findViewById(R.id.headingcard);
        date=(TextView)findViewById(R.id.datecard);
        content=(TextView)findViewById(R.id.content_card);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent in = new Intent(MainActivity.this, Note_add.class);
                in.putExtra("key","false");
                startActivity(in);
            }
        });

        myfb = FirebaseDatabase.getInstance();
        SharedPreferences sp=getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        final String uid=sp.getString("Uid","");
        Toast.makeText(getApplicationContext(),"uid:"+uid,Toast.LENGTH_SHORT).show();
        mydbN = myfb.getReference("Notes").child(uid);
          progressDialog.setMessage("Please wait getting your data ...");
          progressDialog.show();
          progressDialog.setCancelable(false);
        mydbN.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot booking: dataSnapshot.getChildren())
                {
//                    Toast.makeText(view.getContext(),booking.child("date")+" ",Toast.LENGTH_SHORT).show();
                    Noteget noti1 = booking.getValue(Noteget.class);
                    Log.i("Tag", noti1.getDate() + " " +noti1.getTitle());
                    notifications.add(noti1);

                }
                progressDialog.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            new SharedPreference(this).clearall();
            Intent in=new Intent(getApplicationContext(),LoginPage.class);

            startActivity(in);
            Toast.makeText(getApplicationContext(),"logout successfully",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }



}
