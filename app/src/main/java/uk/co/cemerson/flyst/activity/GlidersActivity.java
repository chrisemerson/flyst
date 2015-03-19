package uk.co.cemerson.flyst.activity;

import android.support.v4.app.Fragment;

import uk.co.cemerson.flyst.fragment.GlidersFragment;

public class GlidersActivity extends FlystLayoutActivity
{
    @Override
    protected Fragment getActivityFragment()
    {
        return new GlidersFragment();
    }
}
