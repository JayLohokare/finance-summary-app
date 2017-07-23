package in.skylinelabs.barclaysmanager;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    AlertDialog alertDialog ;

    private Card_Adapter m_adapter;
    private List<CardDetails> m_parts = new ArrayList<CardDetails>();

    RelativeLayout RelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(m_adapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder alertadd = new android.app.AlertDialog.Builder(
                        MainActivity.this);
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);

                final View view2 = factory.inflate(R.layout.add_card, null);


                final EditText edtCard = (EditText) view2.findViewById(R.id.editTextCard);
                final EditText CVV = (EditText) view2.findViewById(R.id.editTextExp);
                final EditText Number = (EditText) view2.findViewById(R.id.editTextCVV);


                alertadd.setView(view2);


                alertadd.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dlg, int sumthin) {

                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

                        ContentValues values = new ContentValues();
                        values.put("card_number", edtCard.getText().toString());
                        values.put("cvv", CVV.getText().toString());
                        values.put("expiry", Number.getText().toString());
                        db.insertCard("cards_table", values);

                        db.createCardsTable(edtCard.getText().toString());
                        loadList();

                    }
                });
                alertDialog = alertadd.create();
                alertDialog.show();


            }


        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView tv  = (TextView)v.findViewById(R.id.editTextCard);
                String card_num = tv.getText().toString();
                Toast.makeText(getApplicationContext(), card_num, Toast.LENGTH_LONG).show();
                Bundle b= new Bundle();
                b.putString("cardnum", card_num);
                Intent i = new Intent(getApplicationContext(),ChatActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

    }




    public void loadList() {

        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(m_adapter);

        RelativeLayout = (RelativeLayout) findViewById(R.id.rel);

        try {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());


            List<CardDetails> fetch = db.getCards();

            for (CardDetails cn1 : fetch) {
                String number = cn1.getcard_number();

                m_parts.add(cn1);
            }

        } catch (Exception e) {
            Log.d("Error dadadada", e.toString());
        }
        m_adapter = new Card_Adapter(getApplicationContext(), R.layout.card_list_element, m_parts);
        listview.setAdapter(m_adapter);


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.main) {
            Intent i = new Intent(this, Barclays_activity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.card) {

        } else if (id == R.id.tutorial) {
            Intent i = new Intent(this, App_intro.class);
            startActivity(i);
            finish();
        } else if (id == R.id.share) {

        } else if (id == R.id.send_money) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
