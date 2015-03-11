package uk.co.cemerson.flyst.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.fragment.StatusFragment;
import uk.co.cemerson.flyst.fragment.UpcomingTasksFragment;

abstract public class FlystLayoutActivity extends FlystBaseActivity implements
    UpcomingTasksFragment.UpcomingTasksCallbackListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addFragment(R.id.statusFragmentContainer, new StatusFragment());
        addFragment(R.id.upcomingTasksFragmentContainer, new UpcomingTasksFragment());

        initialiseUpcomingTasksFragmentLayout();
    }

    private void initialiseUpcomingTasksFragmentLayout()
    {
        initUpcomingTasksSlidingPanelLayoutParams();
        initUpcomingEventsSliderClickListener();
    }

    private void initUpcomingTasksSlidingPanelLayoutParams()
    {
        LinearLayout upcomingTasksSlidingPanel = (LinearLayout) findViewById(R.id.upcomingTasksSlidingPanel);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) upcomingTasksSlidingPanel.getLayoutParams();

        params.setMargins(0, 0, getUpcomingTasksSlidingPanelRequiredOffset(), 0);

        upcomingTasksSlidingPanel.setLayoutParams(params);
    }

    private int getUpcomingTasksSlidingPanelRequiredOffset()
    {
        Resources resources = getResources();

        float upcomingTasksOffsetInDIP = resources.getDimension(R.dimen.upcoming_tasks_panel_width) - resources.getDimension(R.dimen.clickable_bar_width);
        float scale = resources.getDisplayMetrics().density;

        return 0 - Math.round(upcomingTasksOffsetInDIP * scale);
    }

    private void initUpcomingEventsSliderClickListener()
    {
        ImageButton upcomingEventsSliderButton = (ImageButton) findViewById(R.id.upcoming_events_slider_button);

        upcomingEventsSliderButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toggle UpcomingTasks sliding panel here...
            }
        });
    }

    @Override
    public void newEvents()
    {
        //Flash upcoming tasks button
    }

    @Override
    protected int getViewID()
    {
        return R.layout.activity_flyst_layout;
    }
}
