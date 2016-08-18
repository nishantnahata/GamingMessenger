package com.games.gamingmessenger;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatList extends AppCompatActivity {

    FirebaseDatabase fDb;
    ArrayList<String >chats;
    RecyclerView lv;
    Button btn;
    DatabaseReference ref;
    String username;
    ChatListViewAdapter adapter;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        Intent i=getIntent();

        username=i.getStringExtra(MainActivity.USER_NAME);

        lv= (RecyclerView) findViewById(R.id.lv);
        btn= (Button) findViewById(R.id.btn_send);
        chats=new ArrayList<>();
        adapter=new ChatListViewAdapter();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        lv.setLayoutManager(layoutManager);
        lv.setAdapter(adapter);
        et= (EditText) findViewById(R.id.msg);
        fDb=FirebaseDatabase.getInstance();
        ref=fDb.getReference("messages");
        adapter.notifyDataSetChanged();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String val=dataSnapshot.getValue().toString();
                chats.add(chats.size(),val);
                adapter.notifyDataSetChanged();
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
        });
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder{
        TextView sender,message;
        public ChatListViewHolder(View itemView) {
            super(itemView);
            sender=(TextView) itemView.findViewById(R.id.sender);
            message= (TextView) itemView.findViewById(R.id.message);
        }
    }

    public class ChatListViewAdapter extends RecyclerView.Adapter<ChatListViewHolder>{

        @Override
        public ChatListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li=getLayoutInflater();
            View itemView=li.inflate(R.layout.chat_layout,null,true);
            ChatListViewHolder vh=new ChatListViewHolder(itemView);
            return vh;
        }

        @Override
        public void onBindViewHolder(ChatListViewHolder holder, int position) {
            (holder.sender).setText(getSender(position));
            (holder.message).setText(getMessage(position));
        }

        @Override
        public int getItemCount() {
            return chats.size();
        }

        public String getSender(int position)
        {
            String s=null;
            String tmp=chats.get(position);
            int l=0;
            while (tmp.charAt(l)!=':')
            {
                l++;
            }
            s=tmp.substring(0,l-1);
            return s;
        }
        public String getMessage(int position)
        {
            String s=null;
            String tmp=chats.get(position);
            int l=0;
            while (tmp.charAt(l)!=':')
            {
                l++;
            }
            s=tmp.substring(l+1,tmp.length());
            return s;
        }

    }
    void sendMessage()
    {
        String s=et.getText().toString();
        et.setText("");
        Map<String, Object> map=new HashMap<>();
        map.put(String.valueOf(SystemClock.currentThreadTimeMillis()),username+" : "+s);
        ref.updateChildren(map);
    }
    @Override
    protected void onStop() {
        super.onStop();
        Intent i2=new Intent(this,MainActivity.class);
        i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i2.putExtra("EXIT", true);
        startActivity(i2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i2=new Intent(this,MainActivity.class);
        i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i2.putExtra("EXIT", true);
        startActivity(i2);
    }
}
