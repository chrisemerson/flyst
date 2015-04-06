package uk.co.cemerson.flyst.activity;

import android.support.v4.app.Fragment;
import android.view.Menu;

import uk.co.cemerson.flyst.fragment.settings.SettingsFragment;

public class SettingsActivity extends FlystBaseActivity
{
    @Override
    protected Fragment getActivityFragment()
    {
        return new SettingsFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Don't put the settings button on the settings page!
        return true;
    }
}
