package in.skylinelabs.barclaysmanager;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Sign_up extends AppCompatActivity {
    Button btn;
    EditText edttxt1,edttxt2, edttxt3, edttxt4;
    TextInputLayout lNameLayout,lNameLayout1,lNameLayout2,lNameLayout4;
    String name;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        lNameLayout = (TextInputLayout) findViewById(R.id
                .fNameLayoutName);
        lNameLayout1 = (TextInputLayout) findViewById(R.id
                .fNameLayoutContact);
        lNameLayout4 = (TextInputLayout) findViewById(R.id
                .fNameLayoutEmail);


        btn = (Button) findViewById(R.id.textView5);

        edttxt1 = (EditText) findViewById(R.id.editTextName);//Name

        edttxt2 = (EditText) findViewById(R.id.editTextContact);//Phone

        edttxt4 = (EditText) findViewById(R.id.editTextEmail);//Email

        edttxt2.clearFocus();
        edttxt4.clearFocus();
        edttxt1.requestFocus();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String PREFS_NAME = "Barclays";
                final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                name = edttxt1.getText().toString();
                String email = edttxt4.getText().toString();
                String phone =  edttxt2.getText().toString();


                lNameLayout.setErrorEnabled(false);
                lNameLayout1.setErrorEnabled(false);
                lNameLayout4.setErrorEnabled(false);


                if (edttxt1.getText().toString().matches("") || edttxt4.getText().toString().matches("")  || edttxt2.getText().toString().matches("") ) {
                    if (edttxt1.getText().toString().matches("")) {
                        lNameLayout.setErrorEnabled(true);
                        lNameLayout.setError("*Required field");
                    }

                    if (edttxt4.getText().toString().matches("")) {
                        lNameLayout4.setErrorEnabled(true);
                        lNameLayout4.setError("*Required field");
                    }

                    if (edttxt2.getText().toString().matches("")) {
                        lNameLayout1.setErrorEnabled(true);
                        lNameLayout1.setError("*Required field");
                    }


                } else {
                    settings.edit().putString("name", name).commit();
                    settings.edit().putString("email", email).commit();
                    settings.edit().putString("phone", phone).commit();

                    /*DatabaseHandler db = new DatabaseHandler(context);
                    db.createTableTopics();

                    db.createFrom();
                    db.insertFrom();
                    db.createCat();
                    db.insertcat();
                    db.createIncome();
                    */

                    AlertDialog.Builder alertadd = new AlertDialog.Builder(
                            Sign_up.this);
                    LayoutInflater factory = LayoutInflater.from(Sign_up.this);

                    final View view2 = factory.inflate(R.layout.add_card, null);


                    final EditText edtCard = (EditText) view2.findViewById(R.id.editTextCard);
                    final EditText CVV = (EditText) view2.findViewById(R.id.editTextExp);
                    final EditText Number = (EditText) view2.findViewById(R.id.editTextCVV);


                    alertadd.setView(view2);


                    alertadd.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dlg, int sumthin) {
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            db.createCardsDB();

                            ContentValues values = new ContentValues();
                            values.put("card_number",edtCard.getText().toString());
                            values.put("cvv",CVV.getText().toString());
                            values.put("expiry", Number.getText().toString());

                            db.insertCard("cards_table", values);
                            db.createCardsTable(edtCard.getText().toString());

                            Intent i = new Intent(Sign_up.this, App_intro.class);
                            startActivity(i);

                            settings.edit().putBoolean("my_first_time", false).commit();
                            finish();
                        }
                    });
                    alertDialog =  alertadd.create();
                    alertDialog.show();



                }
            }
        });
    }

}
