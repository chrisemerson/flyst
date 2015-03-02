package uk.co.cemerson.flyst.activity;

import android.support.v4.app.Fragment;

import uk.co.cemerson.flyst.fragment.DashboardFragment;

public class DashboardActivity extends FlystLayoutActivity
{
    @Override
    protected Fragment getActivityFragment()
    {
        return new DashboardFragment();
    }
}
