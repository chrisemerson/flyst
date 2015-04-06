package uk.co.cemerson.flyst.fragment.settings;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uk.co.cemerson.flyst.R;

public class SystemSettingsFragment extends Fragment
{
    private View mView;

    private final static String ADB_WIFI_PACKAGE = "com.rockolabs.adbkonnect";
    private final static String ADB_WIFI_ACTIVITY = "AdbKonnectActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_settings_system, container, false);

        setupTemporarySettingsButtons();

        return mView;
    }

    private void setupTemporarySettingsButtons()
    {
        Button adbWifiButton = (Button) mView.findViewById(R.id.adbwifi_button);
        adbWifiButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.setClassName(ADB_WIFI_PACKAGE, ADB_WIFI_PACKAGE + "." + ADB_WIFI_ACTIVITY);

                startActivity(i);
            }
        });

        Button settingsButton = (Button) mView.findViewById(R.id.settings_button);
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
