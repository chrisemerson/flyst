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
    private boolean upcomingTasksPanelIsShown;
    private ImageButton mUpcomingTasksSliderButton;

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
        setUpcomingTasksSlidingPanelLayoutParams(getUpcomingTasksSlidingPanelRequiredOffset(), false);
        initUpcomingTasksSliderClickListener();

        upcomingTasksPanelIsShown = false;
    }

    private void hideUpcomingTasksPanel()
    {
        setUpcomingTasksSlidingPanelLayoutParams(getUpcomingTasksSlidingPanelRequiredOffset(), true);
        mUpcomingTasksSliderButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left));
    }

    private void showUpcomingTasksPanel()
    {
        setUpcomingTasksSlidingPanelLayoutParams(0, true);
        mUpcomingTasksSliderButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));
    }

    private void setUpcomingTasksSlidingPanelLayoutParams(int offset, boolean animate)
    {
        LinearLayout upcomingTasksSlidingPanel = (LinearLayout) findViewById(R.id.upcomingTasksSlidingPanel);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) upcomingTasksSlidingPanel.getLayoutParams();

        params.setMargins(0, 0, offset, 0);

        upcomingTasksSlidingPanel.setLayoutParams(params);
    }

    private int getUpcomingTasksSlidingPanelRequiredOffset()
    {
        Resources resources = getResources();

        float upcomingTasksOffsetInDIP = resources.getDimension(R.dimen.upcoming_tasks_panel_width) - resources.getDimension(R.dimen.clickable_bar_width);
        float scale = resources.getDisplayMetrics().density;

        return 0 - Math.round(upcomingTasksOffsetInDIP * scale);
    }

    private void initUpcomingTasksSliderClickListener()
    {
        mUpcomingTasksSliderButton = (ImageButton) findViewById(R.id.upcoming_events_slider_button);

        mUpcomingTasksSliderButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (upcomingTasksPanelIsShown) {
                    hideUpcomingTasksPanel();
                    upcomingTasksPanelIsShown = false;
                } else {
                    showUpcomingTasksPanel();
                    upcomingTasksPanelIsShown = true;
                }
            }
        });
    }

    @Override
    public void newTasks()
    {
        //Flash upcoming tasks button
    }

    @Override
    protected int getViewID()
    {
        return R.layout.activity_flyst_layout;
    }
}
