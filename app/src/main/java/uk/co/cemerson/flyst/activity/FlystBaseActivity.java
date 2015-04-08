package uk.co.cemerson.flyst.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

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

        setActionBarLayout();
    }

    private void setActionBarLayout()
    {
        ActionBar ab = getSupportActionBar();

        ab.setCustomView(
            getLayoutInflater().inflate(R.layout.actionbar, null),
            new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.clickable_bar_width)
            )
        );

        Button settingsButton = (Button) ab.getCustomView().findViewById(R.id.menu_settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent settingsActivityIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsActivityIntent);
            }
        });

        if (!shouldDisplaySettingsButton()) {
            settingsButton.setVisibility(View.GONE);
        }

        ab.setDisplayOptions(ab.getDisplayOptions() | ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setDisplayHomeAsUpEnabled(false);

        addUpButtonToActionBarIfActivityHasParentSet();
    }

    protected abstract boolean shouldDisplaySettingsButton();

    private void addUpButtonToActionBarIfActivityHasParentSet()
    {
        if (NavUtils.getParentActivityName(this) != null) {
            ImageButton homeButton = (ImageButton) getSupportActionBar().getCustomView().findViewById(R.id.home);
            homeButton.setVisibility(View.VISIBLE);
            homeButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    handleBackButtonInActionBarClicked();
                }
            });
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

    private boolean handleBackButtonInActionBarClicked()
    {
        if (NavUtils.getParentActivityName(this) != null) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return true;
    }
}
