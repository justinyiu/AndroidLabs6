package com.cst2335.yiu00005;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    MyListAdapter myAdapter;
    ListView listView;
    Button sendButton;
    Button receiveButton;
    EditText editText;
    ArrayList<Message> messages = new ArrayList<>();

    public class Message{
        public Boolean isSent;
        public String message;

        public Message(Boolean isSent, String message) {
            this.isSent = isSent;
            this.message = message;
        }
        public Boolean getIsSent() {

            return isSent;
        }
        public String getMessage() {

            return message;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        editText = findViewById(R.id.editTextMessage);
        listView = findViewById(R.id.list_view);
        myAdapter = new MyListAdapter();
        listView.setAdapter(myAdapter);

        sendButton = findViewById(R.id.sendButton);
        receiveButton = findViewById(R.id.receiveButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myMessage = editText.getText().toString();
                messages.add(new Message(true,myMessage));
                editText.setText("");//clear the text
                myAdapter.notifyDataSetChanged();
            }
        });

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myMessage = editText.getText().toString();
                messages.add(new Message(false,myMessage));
                editText.setText("");//clear the text
                myAdapter.notifyDataSetChanged();
            }
        });


        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialogBuilder.setTitle("Do you want to delete this?")
                        .setMessage("The selected row is:" + position)
                        .setMessage("The database id is:" + position)
                        .setPositiveButton("Yes", (click, arg) -> {
                            messages.remove(position);
                            myAdapter.notifyDataSetChanged();
                        })

                        .setNegativeButton("No", (click, arg) -> { })
                        .setView(getLayoutInflater().inflate(R.layout.activity_chat_left, null) )
                        .create().show();
                return true;
            }
        });
    }


    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return messages.size();}

        public Object getItem(int position) { return messages.get(position); }

        public long getItemId(int position) { return position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View newView;
            if (messages.get(position).isSent) {
                newView = inflater.inflate(R.layout.activity_chat_left, parent, false);
                EditText editText  = newView.findViewById(R.id.editTextSend);
                editText.setText(messages.get(position).getMessage());
            }
            else {
                newView = inflater.inflate(R.layout.activity_chat_right, parent, false);
                EditText editText = newView.findViewById(R.id.editTextReceive);
                editText.setText(messages.get(position).getMessage());
            }
            return newView;
        }
    }



}