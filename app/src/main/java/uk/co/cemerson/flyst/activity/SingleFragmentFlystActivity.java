package uk.co.cemerson.flyst.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import uk.co.cemerson.flyst.R;

abstract public class SingleFragmentFlystActivity extends FlystActivity
{
    protected abstract Fragment getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singlefragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.singleFragmentContainer);

        if (fragment == null) {
            fragment = getFragment();

            fm
                .beginTransaction()
                .add(R.id.singleFragmentContainer, fragment)
                .commit();
        }
    }
}
