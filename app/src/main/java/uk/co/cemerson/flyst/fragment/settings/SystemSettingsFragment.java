package uk.co.cemerson.flyst.fragment.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.dialog.ChangePasswordDialog;
import uk.co.cemerson.flyst.dialog.ClearPasswordDialog;

public class SystemSettingsFragment extends Fragment
{
    private static final String SHARED_PREFS_PASSWORD = "systemsettingspassword";
    private static final String ADB_WIFI_PACKAGE = "com.rockolabs.adbkonnect";
    private static final String ADB_WIFI_ACTIVITY = "AdbKonnectActivity";

    private static final String DIALOG_CHANGE_PASSWORD = "change_password_dialog";
    private static final String DIALOG_CLEAR_PASSWORD = "clear_password_dialog";

    private static final int REQUEST_CHANGE_PASSWORD = 0;
    private static final int REQUEST_CLEAR_PASSWORD = 1;

    private SharedPreferences mSharedPreferences;

    private View mView;

    private Button mChangePasswordButton;
    private Button mClearPasswordButton;
    private LinearLayout mPasswordBox;
    private LinearLayout mSystemSettingsBox;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_settings_system, container, false);

        initPasswordProtection();
        setInitialPasswordBoxVisibility();
        setupTemporarySettingsButtons();

        return mView;
    }

    private void setInitialPasswordBoxVisibility()
    {
        if (mSharedPreferences.contains(SHARED_PREFS_PASSWORD)) {
            hideSettings();
        } else {
            mChangePasswordButton.setText(getResources().getString(R.string.set_password_button));
            mClearPasswordButton.setVisibility(View.GONE);
            showSettings();
        }
    }

    @Override
    public void onResume()
    {
        setInitialPasswordBoxVisibility();
        super.onResume();
    }

    private void initPasswordProtection()
    {
        mPasswordBox = (LinearLayout) mView.findViewById(R.id.password);
        mSystemSettingsBox = (LinearLayout) mView.findViewById(R.id.system_settings);
        mChangePasswordButton = (Button) mView.findViewById(R.id.change_password_button);
        mClearPasswordButton = (Button) mView.findViewById(R.id.clear_password_button);

        mSharedPreferences = getActivity().getSharedPreferences("flyst", Context.MODE_PRIVATE);

        String password = null;

        if (mSharedPreferences.contains(SHARED_PREFS_PASSWORD)) {
            password = mSharedPreferences.getString(SHARED_PREFS_PASSWORD, null);
        }

        final String savedPassword = password;

        final EditText passwordText = (EditText) mView.findViewById(R.id.password_text);

        Button passwordSubmitButton = (Button) mView.findViewById(R.id.password_submit);
        passwordSubmitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (savedPassword == null || passwordText.getText().toString().equals(savedPassword)) {
                    showSettings();
                }

                passwordText.setText("");
                hideKeyboard();
            }

            private void hideKeyboard()
            {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(mView.getWindowToken(), 0);
            }
        });

        mChangePasswordButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ChangePasswordDialog dialog = ChangePasswordDialog.newInstance(mSharedPreferences.getString(SHARED_PREFS_PASSWORD, null));
                dialog.setTargetFragment(SystemSettingsFragment.this, REQUEST_CHANGE_PASSWORD);
                dialog.show(fm, DIALOG_CHANGE_PASSWORD);
            }
        });

        mClearPasswordButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ClearPasswordDialog dialog = ClearPasswordDialog.newInstance(mSharedPreferences.getString(SHARED_PREFS_PASSWORD, null));
                dialog.setTargetFragment(SystemSettingsFragment.this, REQUEST_CLEAR_PASSWORD);
                dialog.show(fm, DIALOG_CLEAR_PASSWORD);
            }
        });
    }

    private void showSettings()
    {
        mPasswordBox.setVisibility(View.GONE);
        mSystemSettingsBox.setVisibility(View.VISIBLE);
    }

    private void hideSettings()
    {
        mPasswordBox.setVisibility(View.VISIBLE);
        mSystemSettingsBox.setVisibility(View.GONE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CHANGE_PASSWORD) {
            String newPassword = data.getStringExtra(ChangePasswordDialog.EXTRA_NEW_PASSWORD);
            changePassword(newPassword);
        }

        if (requestCode == REQUEST_CLEAR_PASSWORD) {
            changePassword(null);
        }
    }

    private void changePassword(String newPassword)
    {
        if (newPassword == null) {
            mSharedPreferences.edit().remove(SHARED_PREFS_PASSWORD).commit();
        } else {
            mSharedPreferences.edit().putString(SHARED_PREFS_PASSWORD, newPassword).commit();
        }

        initPasswordProtection();
        setInitialPasswordBoxVisibility();
    }
}
