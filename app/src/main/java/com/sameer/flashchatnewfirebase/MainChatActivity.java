package com.sameer.flashchatnewfirebase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sameer.flashchatnewfirebase.R;


public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private ChatListAdapter mAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // TODO: Set up the display name and get the Firebase reference
           setDisplayName();
           databaseReference= FirebaseDatabase.getInstance().getReference();

        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Send the message when the "enter" button is pressed
          mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
              @Override
              public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                 sendMessage();
                  return true;
              }
          });

        // TODO: Add an OnClickListener to the sendButton to send a message
            mSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                      sendMessage();
                }
            });
    }

    // TODO: Retrieve the display name from the Shared Preferences

    public void setDisplayName()
    {
        SharedPreferences pref=getSharedPreferences(RegisterActivity.CHAT_PREFS,MODE_PRIVATE);
        mDisplayName=pref.getString(RegisterActivity.DISPLAY_NAME_KEY,null);
    }


    private void sendMessage() {

        // TODO: Grab the text the user typed in and push the message to Firebase
        String message=mInputText.getText().toString();
        if(message!=null)
        {
        InstanceMessage input=new InstanceMessage(message,mDisplayName);
        databaseReference.child("messages").push().setValue(input);
            mInputText.setText("");
        }

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.
     @Override
     public void onStart()
     {
         super.onStart();
       //  Toast.makeText(this, "on start", Toast.LENGTH_SHORT).show();
         mAdapter=new ChatListAdapter(MainChatActivity.this,databaseReference,mDisplayName);
         mChatListView.setAdapter(mAdapter);
 
     }


    @Override
    public void onStop() {
        super.onStop();
        mAdapter.cleanUp();
        // TODO: Remove the Firebase event listener on the adapter.

    }

}
