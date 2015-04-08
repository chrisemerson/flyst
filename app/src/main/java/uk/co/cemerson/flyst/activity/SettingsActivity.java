package uk.co.cemerson.flyst.activity;

import android.support.v4.app.Fragment;

import uk.co.cemerson.flyst.fragment.settings.SettingsFragment;

public class SettingsActivity extends FlystBaseActivity
{
    @Override
    protected Fragment getActivityFragment()
    {
        return new SettingsFragment();
    }

    @Override
    protected boolean shouldDisplaySettingsButton()
    {
        return false;
    }
}
