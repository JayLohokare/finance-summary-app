package in.skylinelabs.barclaysmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class IncomingSMS extends BroadcastReceiver {
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        try {

            if (bundle != null) {
                String message = null;
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int j = 0; j < pdusObj.length; j++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[j]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();
                    String msg = "Thank you for using your SBI card 7798720001 for a transaction of Rs 99 at flipkart account balance: 2000";
                    String[] tokens = message.split(" ");
                    for (String token : tokens) {
                        Log.d("token", token);
                    }
                    Date d = new Date( );
                    SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd");

                    System.out.println("Current Date: " + ft.format(d));
                    String date = ft.format(d);
                    String bankname = null, card = null, vendor = null, amount= null, balance = null;
                    String keys[] = { "Rs", "at", "balance:", "card"};
                    String banknames[] = {"Barclays", "SBI", "ICICI", "HDFC"};
                    for (String bank : banknames)
                    {
                        for (String token : tokens) {
                            if (token.equals(bank)) {
                                bankname = bank;
                                break;
                            }
                        }
                    }
                    for(int i = 0; i < tokens.length; i++) {
                        if(tokens[i].equals(keys[0])) {
                            amount = tokens[i + 1];
                        }
                        if(tokens[i].equals(keys[1])) {
                            vendor = tokens[i + 1];
                        }
                        if(tokens[i].equals(keys[2])) {
                            balance = tokens[i + 1];
                        }
                        if(tokens[i].equals(keys[3])) {
                            card = tokens[i + 1];
                        }
                    }

                /*Log.d("bankname", bankname);
                Log.d("card", card);
                Log.d("vendor", vendor);
                Log.d("amount", amount);
                Log.d("balance", balance);
                Log.d("date", date); */
                    System.out.println(("bankname" + bankname));
                    System.out.println("card" + card);
                    System.out.println("vendor" + vendor);
                    System.out.println("amount" + amount);
                    System.out.println("balance" + balance);
                    System.out.println("date" + date);

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setbankname(bankname);
                chatMessage.setcard(card);
                chatMessage.setvendor(vendor);
                chatMessage.setamount(amount);
                chatMessage.setbalance(balance);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setTag(1);
                Log.d("Insert: ", "Inserting ..");

                //DatabaseHandler db = new DatabaseHandler(context);
                //db.addtodatabase(chatMessage);
                //displayMessage(chatMessage);
                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
}
