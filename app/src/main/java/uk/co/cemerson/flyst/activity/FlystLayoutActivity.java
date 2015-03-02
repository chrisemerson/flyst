package uk.co.cemerson.flyst.activity;

import android.os.Bundle;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.fragment.UpcomingTasksFragment;
import uk.co.cemerson.flyst.fragment.StatusFragment;

abstract public class FlystLayoutActivity extends FlystBaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addFragment(R.id.statusFragmentContainer, new StatusFragment());
        addFragment(R.id.upcomingTasksFragmentContainer, new UpcomingTasksFragment());
    }

    @Override
    protected int getViewID()
    {
        return R.layout.activity_flyst_layout;
    }
}