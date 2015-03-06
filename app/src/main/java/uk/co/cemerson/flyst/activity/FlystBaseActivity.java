package uk.co.cemerson.flyst.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
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

        addBackButtonToActionBarIfActivityHasParentSet();
    }

    private void addBackButtonToActionBarIfActivityHasParentSet()
    {
        if (NavUtils.getParentActivityName(this) != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
        switch (item.getItemId()) {
            case android.R.id.home:
                return handleBackButtonInActionBarClicked();

            case R.id.item_menu_settings:
                Intent settingsActivityIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsActivityIntent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean handleBackButtonInActionBarClicked()
    {
        if (NavUtils.getParentActivityName(this) != null) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return true;
    }
}
