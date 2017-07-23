package in.skylinelabs.barclaysmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String PREFS_NAME = "Barclays";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {

            Intent i2 = new Intent(Launcher.this, Sign_up.class);
            startActivity(i2);
            finish();
        }
        else {

            Intent i2 = new Intent(Launcher.this, Barclays_activity.class);
            startActivity(i2);
            finish();


        }
    }
}
