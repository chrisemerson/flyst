package uk.co.cemerson.flyst;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getWindow().addFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button adbWifiButton = (Button) findViewById(R.id.adbwifi_button);
        adbWifiButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.setClassName("com.rockolabs.adbkonnect", "com.rockolabs.adbkonnect.AdbKonnectActivity");

                startActivity(i);
            }
        });

        Button settingsButton = (Button) findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Settings.ACTION_SETTINGS);

                startActivity(i);
            }
        });
    }
}
