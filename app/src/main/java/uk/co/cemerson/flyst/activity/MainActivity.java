package uk.co.cemerson.flyst.activity;

import android.support.v4.app.Fragment;

import uk.co.cemerson.flyst.fragment.MainFragment;

public class MainActivity extends SingleFragmentFlystActivity
{
    @Override
    protected Fragment getFragment()
    {
        return new MainFragment();
    }
}
