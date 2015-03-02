package uk.co.cemerson.flyst.activity;

import android.support.v4.app.Fragment;

import uk.co.cemerson.flyst.fragment.SettingsFragment;

public class SettingsActivity extends FlystBaseActivity
{
    @Override
    protected Fragment getActivityFragment()
    {
        return new SettingsFragment();
    }
}
