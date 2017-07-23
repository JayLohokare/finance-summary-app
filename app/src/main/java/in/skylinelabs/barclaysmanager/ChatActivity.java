package in.skylinelabs.barclaysmanager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

    private EditText messageET, edt;
    private ListView messagesContainer;
    private ImageView sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private DatabaseHandler db;
    protected static final int RESULT_SPEECH = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Personal Banker");

        Bundle p = getIntent().getExtras();
        String cardnum ="vkvk";

        initControls(cardnum);

        ImageView mic = (ImageView) findViewById(R.id.micButton);
        mic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    edt = (EditText) findViewById(R.id.messageEdit);
                    edt.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Ops! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    edt.setText(text.get(0));
                }
                break;
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initControls(final String cardnum) {

        db = new DatabaseHandler(this);
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (ImageView) findViewById(R.id.sendButton);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                //displayMessage(messageET.getText().toString());

                ChatMessage chatMessage = new ChatMessage();

                chatMessage.setmessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setTag(0);

                messageET.setText("");
                Log.d("Insert: ", "Inserting ..");
                // db.addtodatabase(chatMessage);
                displayMessage(chatMessage);

                String[] tokens = messageText.split(" ");
                String cmp = null;
                int flag = 0;
                String keys[] = {"transaction","Transaction","Transaction","Transaction.","Transaction.","Balance", "transactions", "balance",};
                for(int i = 0; i < tokens.length; i++) {
                    if(tokens[i].equals("for")) {
                        cmp = tokens[i + 1];
                        flag = 1;
                        System.out.println("entered");
                    }
                }
                if(flag == 1){
                    bank_vendor(cardnum, cmp);
                }
                else {
                    String main_key = "";
                    for (String token : tokens) {
                        for (String key : keys) {
                            if (token.matches(key)) {
                                main_key = key;
                                break;
                            }
                        }
                    }
                    if (main_key == "transaction" || main_key == "transactions") {

                        DisplayContent("Showing recent transactions");
                        transaction_history(cardnum);
                    }

                    if (main_key == "balance") {

                        DisplayContent("Showing last known balance");
                        balance(cardnum);
                    }

                }
            }
        });


        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);


        DisplayContent("Hi ! \nI am Kym\nYou can call me Kym\nI can help you with all your daily bank transactions!");
        DisplayContent("Here are a few things I can help you with. You can ask me following questions:\n1.Tell me my account balance\n2.Show me my last transactions\n3.");





    }

    void balance(final String cardnum){
        Log.d("Reading: ", "Reading all contacts..");
        List<ChatMessage> cm = db.getAllMessages(cardnum);

        for (ChatMessage cn : cm) {
            String log = "Id: " + cn.getId() + " ,Date: " + cn.getDate() + " ,Message: " + cn.getvendor() + " ,Tag: " + cn.getTag();
            Log.d("Row: ", log);
        }
            int i = cm.size();

        if(i!=0) {
            ChatMessage message = cm.get(i - 1);
            String Balance = message.getbalance();

            message.setmessage("Your last known balance is :" + Balance);

            displayMessage(message);
        }

    }
    void bank_vendor(final String cardnum, String cmp){
        List<ChatMessage> cm = db.getAllMessages(cardnum);
        int flag = 0;
        for(int i=0; i<cm.size(); i++) {
            ChatMessage message = cm.get(i);
            String Amount = message.getamount();
            String BankName = message.getbankname();
            String Vendor = message.getvendor();
            String Balance = message.getbalance();
            String Date = message.getDate();
            if(Vendor.equals(cmp) || BankName.equals(cmp)) {
                message.setmessage("Vendor :  " + Vendor + "\n" + "Bank :  "  + BankName +"\nAmount : " + Amount + "Rs\n" + "Balance: "+ Balance +"\n" + Date  );
                displayMessage(message);
                flag = 1;
            }



        }
        if(flag == 0){
            DisplayContent("No transactions!//");
        }
    }

    void transaction_history(final String cardnum){
        Log.d("Reading: ", "Reading all contacts..");
        List<ChatMessage> cm = db.getAllMessages(cardnum);

        for (ChatMessage cn : cm) {
            String log = "Id: " + cn.getId() + " ,Date: " + cn.getDate() + " ,Message: " + cn.getvendor() + " ,Tag: " + cn.getTag();
            Log.d("Row: ", log);
        }
        for(int i=0; i<cm.size(); i++) {
            ChatMessage message = cm.get(i);
            String Amount = message.getamount();
            String BankName = message.getbankname();
            String Vendor = message.getvendor();
            String Balance = message.getbalance();
            String Date = message.getDate();

            message.setmessage("Vendor :  " + Vendor + "\n" + "Bank :  "  + BankName +"\nAmount : " + Amount + "Rs\n" + "Balance: "+ Balance +"\n" + Date  );


            displayMessage(message);
        }

    }

    public void DisplayContent(String message1)
    {
        ChatMessage m = new ChatMessage();
        m.setmessage(message1);
        m.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        m.setTag(1);
        displayMessage(m);
    }
    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();

    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    /*private void loadDummyHistory(){

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        Log.d("Reading: ", "Reading all contacts..");
        List<ChatMessage> cm = db.getAllMessages();

        for (ChatMessage cn : cm) {
           // String log = "Id: " + cn.getId() + " ,Date: " + cn.getDate() + " ,Message: " + cn.getMessage() + " ,Tag: " + cn.getTag();
            //Log.d("Row: ", log);
        }
        for(int i=0; i<cm.size(); i++) {
                    ChatMessage message = cm.get(i);
                    displayMessage(message);
        }

    } */

}
