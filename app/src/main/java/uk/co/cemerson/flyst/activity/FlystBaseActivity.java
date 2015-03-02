package uk.co.cemerson.flyst.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import uk.co.cemerson.flyst.R;

abstract public class FlystBaseActivity extends ActionBarActivity
{
    private FragmentManager mFragmentManager;

    protected abstract Fragment getActivityFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setWindowOptions();

        super.onCreate(savedInstanceState);

        setContentView(getViewID());

        mFragmentManager = getSupportFragmentManager();

        addFragment(R.id.activityFragmentContainer, getActivityFragment());
    }

    protected int getViewID()
    {
        return R.layout.activity_flyst_base;
    }

    protected void addFragment(int containerID, Fragment fragmentToAdd)
    {
        Fragment fragment = mFragmentManager.findFragmentById(containerID);

        if (fragment == null) {
            fragment = fragmentToAdd;

            mFragmentManager
                .beginTransaction()
                .add(containerID, fragment)
                .commit();
        }
    }

    protected void setWindowOptions()
    {
        getWindow().addFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
