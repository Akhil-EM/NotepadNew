package com.example.notepadnew;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> implements View.OnClickListener
    { private Context mContext;
        private List<Noteget> notifications;
        public NoteAdapter(Context mContext, List<Noteget> notifications) {
            this.mContext = mContext;
            this.notifications = notifications;


        }

        public NoteAdapter() {

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_note_card, parent, false);

            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Noteget notificationlist = notifications.get(position);


            holder.tittle.setText(notificationlist.getTitle());
            holder.date.setText(notificationlist.getDate());
            Toast.makeText(mContext,"delet"+notificationlist.getDate(),Toast.LENGTH_SHORT).show();
            holder.content.setText(notificationlist.getContents());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(mContext,Note_add.class);
                    in.putExtra("key","true");
                    in.putExtra("date",notificationlist.getDate());
                    in.putExtra("heading",notificationlist.getTitle());
                    in.putExtra("content",notificationlist.getContents());
                    mContext.startActivity(in);

                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    final AlertDialog.Builder bul= new AlertDialog.Builder(mContext);
                    bul.setMessage("Do you want to delete this note");
                    bul.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sp=mContext.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sp.edit();
                            final String uid=sp.getString("Uid","");
                            DatabaseReference mydbN;
                            FirebaseDatabase myfb;
                            myfb = FirebaseDatabase.getInstance();
                            mydbN = myfb.getReference("Notes").child(uid);

                            Toast.makeText(mContext,"uid:"+mydbN,Toast.LENGTH_SHORT).show();



                        }
                    });
                    bul.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog=bul.create();
                    dialog.setCancelable(true);
                    dialog.show();
                }
            });


        }

        @Override
        public int getItemCount() {
            return notifications.size();
        }

        @Override
        public void onClick(View v) {

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tittle,date,content;
            public Button delete;



            public MyViewHolder(View view) {
                super(view);
                tittle = view.findViewById(R.id.headingcard);
                date = view.findViewById(R.id.datecard);
                content = view.findViewById(R.id.content_card);
                delete=view.findViewById(R.id.card_delete_note);

                view.setOnClickListener(NoteAdapter.this);

            }
        }


    }
