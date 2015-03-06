package uk.co.cemerson.flyst.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.fragment.StatusFragment;
import uk.co.cemerson.flyst.fragment.UpcomingTasksFragment;

abstract public class FlystLayoutActivity extends FlystBaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addFragment(R.id.statusFragmentContainer, new StatusFragment());
        addFragment(R.id.upcomingTasksFragmentContainer, new UpcomingTasksFragment());

        addUpcomingEventsSliderClickListener();
    }

    private void addUpcomingEventsSliderClickListener()
    {
        ImageButton upcomingEventsSliderButton = (ImageButton) findViewById(R.id.upcoming_events_slider_button);

        upcomingEventsSliderButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Slide out UpcomingEvents Fragment here...
            }
        });
    }

    @Override
    protected int getViewID()
    {
        return R.layout.activity_flyst_layout;
    }
}
