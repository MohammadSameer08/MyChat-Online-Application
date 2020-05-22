package com.sameer.flashchatnewfirebase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import static android.R.color.holo_green_dark;
import static android.R.color.holo_red_dark;
import static android.support.v7.widget.RecyclerView.*;

public class ChatListAdapter extends BaseAdapter {

    Activity mActivity;
    DatabaseReference mDatabaseReference;
    String mDisplayName;
    ArrayList<DataSnapshot> mSnapshotsList;
    public ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            mSnapshotsList.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ChatListAdapter(Activity activity, DatabaseReference ref, String name) {

        mActivity = activity;
        mDisplayName = name;
        mDatabaseReference = ref.child("messages");
        mDatabaseReference.addChildEventListener(mListener);
        mSnapshotsList = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return mSnapshotsList.size();
    }

    @Override
    public InstanceMessage getItem(int i) {
        DataSnapshot snapshot = mSnapshotsList.get(i);
        return snapshot.getValue(InstanceMessage.class);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        TextView authName;
        TextView body;
        LinearLayout.LayoutParams params;


    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.chat_msg_row, viewGroup, false);
            final ViewHolder holder = new ViewHolder();
            holder.authName = (TextView) view.findViewById(R.id.author);
            holder.body = (TextView) view.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.authName.getLayoutParams();
            view.setTag(holder);
        }

        InstanceMessage message = getItem(i);
        final ViewHolder holder = (ViewHolder) view.getTag();
        String author = message.getAuthor();
        holder.authName.setText(author);
        boolean itme = message.getAuthor().equals(mDisplayName);
        ChatRow(itme, holder);
        String msg = message.getMessage();
        holder.body.setText(msg);

        return view;


    }



    @SuppressLint("ResourceAsColor")
    public void ChatRow(boolean itsme, ViewHolder holder) {
        if (itsme) {
            holder.params.gravity = Gravity.END;
         //   holder.authName.setTextColor(holo_green_dark);
            holder.body.setBackgroundResource(R.drawable.bubble1);
        } else {
            holder.params.gravity = Gravity.START;
          // holder.authName.setTextColor(holo_red_dark);
            holder.body.setBackgroundResource(R.drawable.bubble2);
        }
      holder.authName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);
    }

    public void cleanUp() {
        mDatabaseReference.removeEventListener(mListener);

    }
}


