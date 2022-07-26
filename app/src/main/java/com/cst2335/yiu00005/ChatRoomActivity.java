package com.cst2335.yiu00005;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {
    private Button send;
    private Button receive;
    private EditText chatText;
    private ListView myList;
    private ArrayList<Message> messageList = new ArrayList<>();
    private MyListAdapter myAdapter;

    private MyOpenHelper myOpener;
    private SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        myOpener = new MyOpenHelper(this);
        myDatabase = myOpener.getWritableDatabase();
        Cursor history = myDatabase.rawQuery("Select * from " + MyOpenHelper.TABLE_NAME + ";", null);

        int idIndex = history.getColumnIndex((MyOpenHelper.COL_ID));
        int messageIndex = history.getColumnIndex(MyOpenHelper.COL_MESSAGE);
        int sOrRIndex = history.getColumnIndex((MyOpenHelper.COL_SEND_RECEIVE));

        while(history.moveToNext()) {
            long id = history.getInt(idIndex);
            String message = history.getString(messageIndex);
            boolean sOrR = (history.getInt(sOrRIndex) != 0);
            messageList.add(new Message(message, sOrR, id));
        }
        printCursor(history, 1);
        history.close();

        send = findViewById(R.id.btnSend);
        receive = findViewById(R.id.btnReceive);
        chatText = findViewById(R.id.chatText);
        myList = findViewById(R.id.chatList);
        myList.setAdapter(myAdapter = new MyListAdapter());

//*************************************************************************************************
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = chatText.getText().toString();
                if (!input.isEmpty()) {
                    ContentValues newRow = new ContentValues();
                    newRow.put(MyOpenHelper.COL_MESSAGE, input);
                    newRow.put(MyOpenHelper.COL_SEND_RECEIVE, 1);
                    long id = myDatabase.insert(MyOpenHelper.TABLE_NAME, null, newRow);

                    messageList.add(new Message(input,true, id));
                    chatText.setText("");
                    myAdapter.notifyDataSetChanged();

                }
            }
        });

        receive.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = chatText.getText().toString();
                if(!input.isEmpty()) {
                    ContentValues newRow = new ContentValues();
                    newRow.put(MyOpenHelper.COL_MESSAGE, input);
                    newRow.put(MyOpenHelper.COL_SEND_RECEIVE, 0);
                    long id = myDatabase.insert(MyOpenHelper.TABLE_NAME, null, newRow);

                    messageList.add(new Message(input,false, id));
                    chatText.setText("");
                    myAdapter.notifyDataSetChanged();

                }
            }
        }));
//*************************************************************************************************
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChatRoomActivity.this);
                //Message whatWasClicked = messageList.get(position);
                //whatWasClicked.getId()
                alertDialogBuilder.setTitle("Do you want to delete this ？")
                        .setMessage("The selected row is:" + position + " " + "The database id is:" + id)
                        .setNegativeButton("Negative", (dialog, click1)->{})
                        .setPositiveButton("Positive", (dialog, click2)->{
                            messageList.remove(position);
                            myAdapter.notifyDataSetChanged();

                            myDatabase.delete(MyOpenHelper.TABLE_NAME,MyOpenHelper.COL_ID +" = ?", new String[]{Long.toString(id)});

                        })
                        .create().show();
                return true;
            }
        });

    }
    public void printCursor(Cursor c, int version) {
        ArrayList<Message> rowValue = new ArrayList<>();

        int idIndex = c.getColumnIndex(MyOpenHelper.COL_ID);
        int msgIndex = c.getColumnIndex(MyOpenHelper.COL_MESSAGE);
        int sOrRIndex = c.getColumnIndex(MyOpenHelper.COL_SEND_RECEIVE);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            long id = c.getInt(idIndex);
            String msg = c.getString(msgIndex);
            int sOrR = c.getInt(sOrRIndex);
            boolean b = (sOrR != 0);
            rowValue.add(new Message(msg, b, id));
            c.moveToNext();
        }

        Log.e("version", myDatabase.getVersion() + "");
        Log.e("number of columns", c.getColumnCount() + "");
        Log.e("name of the columns", Arrays.toString(c.getColumnNames()));
        Log.e("number of rows", c.getCount() + "");
        Log.e("each row of results", rowValue.toString());

        c.close();
    }
    //*************************************************************************************************
    public class MyListAdapter extends BaseAdapter {
        public int getCount() { return messageList.size();}
        public Message getItem(int position) { return messageList.get(position);}
        public long getItemId(int position) { return getItem(position).getId();}
        public View getView(int position, View convertView, ViewGroup parent) {
            Message msg = getItem(position);
            View rowView;
            TextView textView;
            LayoutInflater inflater = getLayoutInflater();

            //if (messageList.get(position).getIsSent()) {
            if (msg.getIsSent()) {
                rowView = inflater.inflate(R.layout.send_layout, parent, false);
                textView = rowView.findViewById(R.id.textSend);
            } else{
                rowView = inflater.inflate(R.layout.receive_layout, parent, false);
                textView = rowView.findViewById(R.id.textReceive);
            }

            textView.setText(msg.getMessage());
            return rowView;
        }
    }
    //*************************************************************************************************
    public class Message {
        String message;
        boolean isSent;
        long id;

        public Message(String message, boolean isSent, long id) {
            super();
            this.message = message;
            this.isSent = isSent;
            this.id = id;
        }

        public String getMessage() {
            return this.message;
        }

        public boolean getIsSent() {
            return this.isSent;
        }

        public long getId() { return this.id;}

        public String toString(){
            return("message:" + this.message + " isSent:" + this.isSent + " id:" + this.id);
        }
    }

}