package in.skylinelabs.barclaysmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Barclays";

    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_BANKNAME = "bankname";
    private static final String KEY_CARD = "card";
    private static final String KEY_VENDOR = "vendor";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_BALANCE = "balance";
    private static final String KEY_TAG = "tag";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }

    public void createCardsTable(String card_num) {
        String CREATE_TABLE = "CREATE TABLE " + "Table" + card_num + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT," + KEY_BANKNAME + " TEXT," + KEY_VENDOR + " TEXT," + KEY_BALANCE + " TEXT," + KEY_AMOUNT + " TEXT,"
                + KEY_TAG + " TEXT" + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TABLE);
    }



    void addtodatabase(ChatMessage cm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_DATE, cm.getDate());
        values.put(KEY_BANKNAME, cm.getbankname());
        values.put(KEY_VENDOR, cm.getvendor());

        System.out.println(cm.getvendor());
        System.out.println("Table" + cm.getcard());
        values.put(KEY_AMOUNT, cm.getamount());
        values.put(KEY_BALANCE, cm.getbalance());
        values.put(KEY_TAG, cm.getTag());
        // Inserting Row
        db.insert("Table" + cm.getcard(), null, values);
        db.close(); // Closing database connection
    }



    public List<ChatMessage> getAllMessages(String card_num) {
        List<ChatMessage> messageList = new ArrayList<ChatMessage>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + "Table" + card_num;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChatMessage cm = new ChatMessage();
                cm.setId(Integer.parseInt(cursor.getString(0)));
                cm.setDate(cursor.getString(1));
                cm.setbankname(cursor.getString(2));
                cm.setvendor(cursor.getString(3));
                cm.setbalance(cursor.getString(4));
                cm.setamount(cursor.getString(5));
                cm.setTag(Integer.parseInt(cursor.getString(6)));
                // Adding contact to list
                messageList.add(cm);
            } while (cursor.moveToNext());
        }


        return messageList;
    }


    /***************************************CARDS*******************************************/
    public void createCardsDB() {
       String catergory_table_create = "CREATE TABLE cards_table (card_number TEXT, cvv TEXT, expiry TEXT)";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(catergory_table_create);
        db.close();

    }

    public void insertCard(String name, ContentValues values) {
        SQLiteDatabase db2 = this.getReadableDatabase();
        db2.insert("cards_table", null, values);
        db2.close();
    }

    public List<CardDetails> getCards() {
        List<CardDetails> messageList = new ArrayList<CardDetails>();

        String selectQuery = "SELECT  * FROM cards_table ";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT  * FROM cards_table", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CardDetails cm = new CardDetails();
                cm.setcard_number(cursor.getString(0));
                messageList.add(cm);
            } while (cursor.moveToNext());
        }
        return messageList;
    }
    /***************************************CARDS*******************************************/

}













