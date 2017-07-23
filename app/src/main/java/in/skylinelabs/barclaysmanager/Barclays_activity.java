package in.skylinelabs.barclaysmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.communication.IOnBarClickedListener;
import org.eazegraph.lib.models.BarModel;

import java.util.List;

public class Barclays_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barclays_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView tv = (TextView)findViewById(R.id.textView4);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BarChart mBarChart = (BarChart) findViewById(R.id.barchart);
        mBarChart.setOnBarClickedListener(new IOnBarClickedListener() {
            @Override
            public void onBarClicked(int _Position) {
                Toast.makeText(getApplicationContext(), _Position, Toast.LENGTH_LONG).show();
            }
        });

        DatabaseHandler db = new DatabaseHandler(this);
        List<CardDetails> l = db.getCards();
        int sum = 0;
        for(int i=0; i<l.size(); i++) {
            CardDetails cd = l.get(i);
            String cardnum = cd.getcard_number();
            mBarChart.addBar(new BarModel(total(cardnum), 0xFF123456));
            sum += total(cardnum);
        }
       tv.setText("" + sum);


        mBarChart.startAnimation();

        RelativeLayout rel5= (RelativeLayout) findViewById(R.id.rel34);
        rel5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent i = new Intent(Barclays_activity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
        );

    }

    int total(String cardnum){
        DatabaseHandler db = new DatabaseHandler(this);
        List<ChatMessage> cm = db.getAllMessages(cardnum);
        int sum = 0;
        for(int i=0; i<cm.size(); i++) {
            ChatMessage message = cm.get(i);

            String Balance = message.getamount();
            sum += Integer.parseInt(Balance);

        }
        return sum;
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
        getMenuInflater().inflate(R.menu.barclays_activity, menu);
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

        } else if (id == R.id.card) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
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
