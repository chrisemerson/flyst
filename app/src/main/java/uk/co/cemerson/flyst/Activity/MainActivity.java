package uk.co.cemerson.flyst.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import uk.co.cemerson.flyst.R;

public class MainActivity extends FlystActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTemporarySettingsButtons();
    }

    private void setupTemporarySettingsButtons()
    {
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
